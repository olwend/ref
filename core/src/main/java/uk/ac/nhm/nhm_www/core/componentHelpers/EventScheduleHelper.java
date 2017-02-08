package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

public class EventScheduleHelper {

	private String eventContentPath;
	private String eventType;
	private String eventDates;
	private String eventAllDay;
	private String eventTimes;
	private String durations;
	private String soldOut;

	private HashMap<String, String> datesMap;
	private HashMap<String[], String[]> soldOutMap;
	private ArrayList<String> sortedDates;

	public EventScheduleHelper(ResourceResolver resourceResolver, Page currentPage) throws ValueFormatException, PathNotFoundException, RepositoryException, ParseException {
		this.eventContentPath = currentPage.getPath() + "/jcr:content";

		Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

		this.eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
		this.eventDates = contentNode.hasProperty("jcr:datesRecurrence") ? contentNode.getProperty("jcr:datesRecurrence").getString() : "";
		this.eventAllDay = contentNode.hasProperty("jcr:allDayRecurrence") ? contentNode.getProperty("jcr:allDayRecurrence").getString() : "";
		this.eventTimes = contentNode.hasProperty("jcr:timesRecurrence") ? contentNode.getProperty("jcr:timesRecurrence").getString() : "";
		this.durations = contentNode.hasProperty("jcr:durationsRecurrence") ? contentNode.getProperty("jcr:durationsRecurrence").getString() : "";
		this.soldOut = contentNode.hasProperty("jcr:soldOut") ? contentNode.getProperty("jcr:soldOut").getString() : "";

		this.datesMap = new HashMap<String, String>();
		this.sortedDates = new ArrayList<String>();

		initialise();
	}

	public String getEventType() {
		return eventType;
	}

	public ArrayList<String> getSortedDates() {
		return sortedDates;
	}

	public HashMap<String, String> getDatesMap() {
		return datesMap;
	}

	private void initialise() throws ParseException {
		if (!eventDates.equals("")) {
			LinkedHashSet<String> dates = createDatesArray(eventDates);
			String[] times = createTimesArray(eventTimes, durations);
			String[] allDayArray = eventAllDay.replaceAll("[^\\w\\s\\,]", "").split(",");
			times = createSoldOutArray(soldOut, times);

			//Populates the Map
			for (String date : dates) {
				int index = Integer.parseInt(parseDateString(date, "(\\d+)$"));
				String datesMapValue = "All day";
				if (!allDayArray[index].equals("true")) {
					datesMapValue = times[index]; 
				} 
				datesMap.put(parseDateString(date, "^([a-zA-Z0-9 ])+"), datesMapValue);
			}

			//Populates an array list with the dates
			for(Map.Entry<String,String> map : datesMap.entrySet()){
				sortedDates.add(map.getKey());
			}

			//Sorts the dates array
			Collections.sort(sortedDates, new Comparator<String>() {
				SimpleDateFormat f = new SimpleDateFormat("d MMMM yyyy");

				@Override
				public int compare(String o1, String o2) {
					try {
						return f.parse(o1).compareTo(f.parse(o2));
					} catch (ParseException e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
		}
	}

	private String parseDateString(String date, String regex) {
        String pos = null;

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		if(matcher.find()) {
			pos = matcher.group(0);
		}

		return pos;
    }

	private LinkedHashSet<String> createDatesArray(String stringValue) throws ParseException {
		LinkedHashSet<String> datesSet = new LinkedHashSet<String>();
		String[] values = stringValue.split(",");

		for (int i = 0; i < values.length; i++) {
			if (values[i].length() > 0) {
				datesSet.add(getDateParsed (values[i]));
			}
		}
		return datesSet;
	}

	private String getDateParsed(String dateString) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM d yyyy");
		final SimpleDateFormat finalSdf = new SimpleDateFormat("d MMMM yyyy");

		String[] parts = dateString.split(" ");
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
		String index = parseDateString(dateString, "(\\d+)$");
		Date dateParsed = sdf.parse(stringDateParsed);

		return finalSdf.format(dateParsed).toString() + "-" + index;
	}

	private String[] createTimesArray(String times, String durations) throws ParseException {
		if (times.length() > 0) {
			ArrayList<String> stringArrayList = new ArrayList<String>();
			Integer[] intDurationArray = createDurationsArray(durations);

			times = times.replace("\"", "");
			times = times.substring(1, times.length() - 1);

			String[] values = times.split("],");

			for (int i = 0; i < values.length; i++) {
				String [] timeArray = values[i].replaceAll("[^\\w\\s\\,\\:]", "").split(",");
				stringArrayList.add(createTimeAndDuration(timeArray, intDurationArray[i]));
			}

			String[] stringArray = new String[stringArrayList.size()];
			stringArray = stringArrayList.toArray(stringArray);
			
			return stringArray;
		} else {
			return new String[0];    
		}
	}

	private Integer[] createDurationsArray(String durations) {
		if (durations.length() > 0) {
			ArrayList<Integer> intArrayList = new ArrayList<Integer>();

			String[] values = durations.replaceAll("[^\\w\\s\\,]", "").split(",");

			for (String duration : values) {
				if (!duration.equals("")) {
					intArrayList.add(Integer.parseInt(duration));
				} else {
					intArrayList.add(0);
				}
			}

			Integer[] intArray = new Integer[intArrayList.size()];
			intArray = intArrayList.toArray(intArray);

			return intArray;
		} else {
			return new Integer[0];    
		}
	}

	private String createTimeAndDuration(String[] times, Integer duration) throws ParseException {
		String timeAndDuration = "";
		if (times.length > 0) {
			for (int i = 0; i < times.length; i++) {
				String initialTime = times[i].replace(":",".");
				String finalTime = calculateEndTime(times[i], duration).replace(":",".");  
				timeAndDuration += initialTime.replace("00.00","midnight") + "-" + finalTime.replace("00.00","midnight");
				if (i < times.length - 1) {
					timeAndDuration += ", ";                       
				}
			}
		}
		return timeAndDuration;    
	}

	private String calculateEndTime(String time, Integer duration) throws ParseException {
		if (!time.equals("")) {
			final SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
			Calendar calendar = Calendar.getInstance();
			Date timeValue = timeSdf.parse(time);

			calendar.setTime(timeValue);
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + duration);

			return timeSdf.format(calendar.getTime()).toString();
		} else {
			return "";                               
		}
	}
	
	private static String[] createSoldOutArray(String soldOut, String[] times) {
        String[] soldOutArray = soldOut.substring(1, soldOut.length()-1).split("\\],\\[");
        String[] c = new String[soldOutArray.length];
        
        for(int i=0; i < soldOutArray.length; i++) {
            soldOutArray[i] = soldOutArray[i].replaceAll("[\\[\\]]","");
            
            String[] a = soldOutArray[i].split(",");
            String[] b = times[i].replaceAll(" ", "").split(",");
            
            for(int j=0; j < a.length; j++) {
                if(a[j].equals("true")) {
                    b[j] = b[j] + " Sold out";
                }
                
                if(c[i] == null) {
                    c[i] = b[j];
                } else {
                    c[i] = c[i] + " " + b[j];
                }
            } 
        }
        return c;
    }
}
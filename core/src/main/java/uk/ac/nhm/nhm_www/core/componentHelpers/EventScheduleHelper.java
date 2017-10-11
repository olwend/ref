package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.impl.services.CreateXMLFeedServiceImpl;

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
	private Boolean oneColumn;
	
	private ValueMap properties;

	private HashMap<String, String> datesMap;
	private HashMap<String[], String[]> soldOutMap;
	private ArrayList<String> sortedDates;

	private static final Logger LOG = LoggerFactory.getLogger(EventScheduleHelper.class);
	
	public EventScheduleHelper(ResourceResolver resourceResolver, Page currentPage, ValueMap properties) throws ValueFormatException, PathNotFoundException, RepositoryException, ParseException {
		this.eventContentPath = currentPage.getPath() + "/jcr:content/parentpar/eventdetail";

		Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

		this.eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
		this.eventDates = contentNode.hasProperty("datesRecurrence") ? contentNode.getProperty("datesRecurrence").getString() : "";
		this.eventAllDay = contentNode.hasProperty("allDayRecurrence") ? contentNode.getProperty("allDayRecurrence").getString() : "";
		this.eventTimes = contentNode.hasProperty("timesRecurrence") ? contentNode.getProperty("timesRecurrence").getString() : "";
		this.durations = contentNode.hasProperty("durationsRecurrence") ? contentNode.getProperty("durationsRecurrence").getString() : "";
		this.soldOut = contentNode.hasProperty("soldOut") ? contentNode.getProperty("soldOut").getString() : "";

		this.datesMap = new HashMap<String, String>();
		this.sortedDates = new ArrayList<String>();

		setProperties(properties);
		
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
			String[] soldOutArray = soldOut.substring(1, soldOut.length()-1).split("\\], \\[");
			
			times = addSoldOutText(soldOutArray, times, allDayArray);
			int index = 0;

			//Populates the Map
			for (String date : dates) {
				//String datesMapValue = "All day";
				//if (!allDayArray[index].equals("true")) {
					String datesMapValue = times[index]; 
				//}
				//TODO - need sub arrays in soldOutArray
				//else if(soldOutArray[index].equals("true")) {
				//	datesMapValue += " (Sold out)";
				//}
				datesMap.put(parseDateString(date, "^([a-zA-Z0-9 ])+"), datesMapValue);
				index++;
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
		
		if(getProperties().get("oneColumn") != null) {
			this.oneColumn = getProperties().get("oneColumn", false);
		} else {
			this.oneColumn = false;
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
	
	private String[] addSoldOutText(String[] soldOutArray, String[] times, String[] allDays) {
        
		int cLength = 0;
		for(int i=0; i<soldOutArray.length; i++) {
        	if(soldOutArray[i].contains("],[")) {
        		String[] temp = soldOutArray[i].split("\\],\\[");
        		for(int j=0; j<temp.length; j++) {
        			cLength++;
        		}
        	} else {
        		cLength++;
        	}
        }
		
		String[] c = new String[cLength];
        int index = 0;
        
        for(int i=0; i < soldOutArray.length; i++) {
        	if(soldOutArray[i].contains("],[")) {
        		String[] temp = soldOutArray[i].split("\\],\\[");
        		for(int j=0; j<temp.length; j++) {
        			temp[j] = temp[j].replaceAll("[\\[\\]]","");

        			String[] a = temp[j].split(",");
        			String[] b = times[i].replaceAll(" ", "").split(",");

    	            for(int k=0; k < a.length; k++) {
    	            	if(b[k].equals("-")) b[k] = "All Day";
    	            	
    	                if(a[k].equals("true")) {
    	                    b[k] = b[k] + " (Sold out)";
    	                }

    	                if(c[index] == null) {
		                    c[index] = b[k];
		                } else {
		                    c[index] = c[index] + ", " + b[k];
		                }
    	            }
    	            index++;
        		}
        	} else {
	        	soldOutArray[i] = soldOutArray[i].replaceAll("[\\[\\]]","");
	            
	            String[] a = soldOutArray[i].split(",");
	            String[] b = times[i].replaceAll(" ", "").split(",");

	            for(int j=0; j < a.length; j++) {
	            	if(b[j].equals("-")) b[j] = "All Day";
	            	
	                if(a[j].equals("true")) {
	                    b[j] = b[j] + " (Sold out)";
	                }
	                
	                if(c[index] == null) {
	                    c[index] = b[j];
	                } else {
	                    c[index] = c[index] + ", " + b[j];
	                }
	            }
	            index++;
	        }
        }
        return c;
    }
	
	public ValueMap getProperties() {
		return properties;
	}

	public void setProperties(ValueMap properties) {
		this.properties = properties;
	}

	public Boolean getOneColumn() {
		return oneColumn;
	}

	public void setOneColumn(Boolean oneColumn) {
		this.oneColumn = oneColumn;
	}

}
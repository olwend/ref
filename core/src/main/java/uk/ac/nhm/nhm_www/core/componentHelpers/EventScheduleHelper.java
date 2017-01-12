package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventScheduleHelper {

	public int getArrayValue(String date) {
	    int pos = -1;
	    
	    Pattern pattern = Pattern.compile("(\\d+)$");
	    Matcher matcher = pattern.matcher(date);
	    if(matcher.find()) {
	        pos = Integer.valueOf(matcher.group(0));
	    }
	    
	    return pos;
	}

	public LinkedHashSet<String> createDatesArray(String stringValue) throws ParseException {
		LinkedHashSet<String> datesSet = new LinkedHashSet<String>();
		String[] values = stringValue.split(",");

		for (int i = 0; i < values.length; i++) {
			if (values[i].length() > 0) {
				datesSet.add(getDateParsed (values[i]));
			}
		}
		return datesSet;
	}
	
	public String getDateParsed(String dateString) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
        final SimpleDateFormat finalSdf = new SimpleDateFormat("dd MMMM yyyy");
		
		String[] parts = dateString.split(" ");
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
		String index = Integer.toString(getArrayValue(dateString));
		Date dateParsed = sdf.parse(stringDateParsed);

		return finalSdf.format(dateParsed).toString() + index;
	}
    
    String[] createTimesArray(String times, String durations) throws ParseException {
       if (times.length() > 0) {
          ArrayList<String> stringArrayList = new ArrayList<String>();
          Integer[] intDurationArray =  createDurationsArray(durations);
    
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
    
    Integer[] createDurationsArray(String durations) {
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
                                            
    public String createTimeAndDuration(String[] times, Integer duration) throws ParseException {
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
    
    public String calculateEndTime(String time, Integer duration) throws ParseException {
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
	
}

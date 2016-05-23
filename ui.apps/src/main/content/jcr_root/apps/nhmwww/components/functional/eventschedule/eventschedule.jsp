<%@page session="false"%>
<%@ page import ="java.util.*" %>
<%@ page import ="java.text.*" %>
<%@include file="/libs/foundation/global.jsp" %>
<%!
    LinkedHashSet<String> createDatesArray(String stringValue) throws ParseException {
		LinkedHashSet<String> datesSet = new LinkedHashSet<String>();
		String[] values = stringValue.split(",");

		for (int i = 0; i < values.length; i++) {
			if (values[i].length() > 0) {
				datesSet.add(getDateParsed (values[i]));
			}
		}
		return datesSet;
	}
	
	String getDateParsed(String dateString) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
        final SimpleDateFormat finalSdf = new SimpleDateFormat("dd MMMM yyyy");
		
		String[] parts = dateString.split(" ");
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
		String index = dateString.substring(dateString.length() - 1);
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
                                            
    String createTimeAndDuration(String[] times, Integer duration) throws ParseException {
       String timeAndDuration = "";
       if (times.length > 0) {
          for (int i = 0; i < times.length; i++) {
             timeAndDuration += times[i] +" - "+calculateEndTime(times[i], duration);
             if (i < times.length - 1) {
                  timeAndDuration += ", ";                       
             }
          }
          return timeAndDuration;
       } else {
          return timeAndDuration;    
       }
	}
    
    String calculateEndTime(String time, Integer duration) throws ParseException {
        String endTime = "";
        if (!time.equals("")) {
           final SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
           Calendar calendar = Calendar.getInstance();
           Date timeValue = timeSdf.parse(time);
		
		   calendar.setTime(timeValue);
		   calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + duration);
		
		   return timeSdf.format(calendar.getTime()).toString();
        } else {
            return endTime;                               
        }
    }
%>
<%
   String eventContentPath = currentPage.getPath() + "/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
   String eventDates = contentNode.hasProperty("jcr:datesRecurrence") ? contentNode.getProperty("jcr:datesRecurrence").getString() : "";
   String eventAllDay = contentNode.hasProperty("jcr:allDayRecurrence") ? contentNode.getProperty("jcr:allDayRecurrence").getString() : "";
   String eventTimes = contentNode.hasProperty("jcr:timesRecurrence") ? contentNode.getProperty("jcr:timesRecurrence").getString() : "";
   String durations = contentNode.hasProperty("jcr:durationsRecurrence") ? contentNode.getProperty("jcr:durationsRecurrence").getString() : "";
   
   HashMap<String, String> datesMap = new HashMap<String, String>();
 
   ArrayList<String> sortedDates = new ArrayList<String>();

   if (!eventDates.equals("")) {
      LinkedHashSet<String> dates = createDatesArray(eventDates);
      String[] times = createTimesArray(eventTimes, durations);
      String[] allDayArray = eventAllDay.replaceAll("[^\\w\\s\\,]", "").split(",");
    
      //Populates the Map
      for (String date : dates) {
         int index = Integer.parseInt(date.substring(date.length() - 1));

         if (allDayArray[index].equals("true")){
            datesMap.put(date.substring(0,date.length()-1),"All day"); 
         } else {
            datesMap.put(date.substring(0,date.length()-1),times[index]); 
         }   
      }
   
      //Populates an array list with the dates
      for(Map.Entry<String,String> map : datesMap.entrySet()){
         sortedDates.add(map.getKey());
      }

      //Sorts the dates array
      Collections.sort(sortedDates, new Comparator<String>() {
         SimpleDateFormat f = new SimpleDateFormat("dd MMMM yyyy");
      
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
%>
<cq:includeClientLib categories="nhmwww.eventschedule"/>
<c:set var="eventType" value="<%= eventType %>" />
<c:set var="sortedDates" value="<%= sortedDates %>" />
<c:choose>
    <c:when test="${not empty eventType && not empty sortedDates}">
        <c:set var="datesMap" value="<%= datesMap %>" />
        <div class="event--schedule--wrapper">
            <table id="datesTable" class="small-12 medium-12 large-12">
                <thead>
                    <tr>
                        <td class="hti-box__feature-box title-bar">Event dates</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="date" items="${sortedDates}" varStatus="loop">
                        <tr>
                            <c:choose>
                                <c:when test="${loop.index < 7}">
                                    <td>${date} <span>${datesMap[date]}</span></td>
                                </c:when>
                                <c:otherwise>
                                    <td style="display:none;">${date} <span>${datesMap[date]}</span></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        <c:if test="${loop.index == 7}">
                            <tr>
                                <th id="showMore">Show more</th>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        The page has not been set up yet.
    </c:otherwise>
</c:choose>
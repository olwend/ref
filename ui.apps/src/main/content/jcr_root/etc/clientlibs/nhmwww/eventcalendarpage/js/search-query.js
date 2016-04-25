//Needed for using multiple onload
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

addLoadEvent(function() {
    //Stores the events JSON in sessionStorage and displays Today Events
    var eventsData  = CQ.HTTP.get(CQ.HTTP.externalize("/content/nhmwww/eventscontent" + "/events")),
        pagePath    = CQ.WCM.getPagePath();

    sessionStorage.events = eventsData.responseText;
    
    pagePath = pagePath.split("/");    
    sessionStorage.pagePath = pagePath[pagePath.length - 2];
        
    displayTodayEvents();
});

//Populates the single events result content
function displayTodayEvents() {
    var today           = new Date(),
        eventsJson      = JSON.parse(sessionStorage.events),
        carousel        = document.getElementsByClassName("nhm-carousel"),
        showMore        = document.getElementById("showMore"),
        noResultsToday  = document.getElementById("noResultsToday"),
        container       = document.getElementById("searchResult"),
        titleH2         = document.createElement("h2"),
        ul              = document.createElement("ul"),
        results         = [],
        flag            = true,
        counter         = 0;
    
    //Clears the container
    container.innerHTML = "";
    showMore.style.display = "none";
    noResultsToday.style.display = "none";
    noResults.style.display = "none";
    
    carousel[0].style.display = "block";
    titleH2.className = "events--header";
    titleH2.innerHTML = "Today's Events";
    ul.className = "large-block-grid-3 medium-block-grid-3 small-block-grid-1";

    for (var i = 0; i < eventsJson.Events.length;  i++) {
        for (var j = 0; j < eventsJson.Events[i].dates.length;  j++) {
            
            var isOurEvent  = false,
                date        = new Date(eventsJson.Events[i].dates[j]),
                eventType   = eventsJson.Events[i].eventType;
            
            //Checks the Event Type
            if (checkEventType(eventType)){
                isOurEvent = true;
            }
            else {
                //If not checks the tags
                var tags = eventsJson.Events[i].tags;
                for (var k = 0; k < tags.length; k++) {
                    var tag = tags[k].split("/");
                    if (checkEventType(tag[tag.length - 1])){
                        isOurEvent = true;
                    }
                }
            }
            if (today.setHours(0,0,0,0,0) == date.setHours(0,0,0,0,0) && isOurEvent) {
                if (flag) {
                    flag = false;
                    //Appends the title
                    container.appendChild(titleH2);
                    container.appendChild(ul);
                }
                results.push(eventsJson.Events[i]);
                break;
            }
        }
    }
    
    //Displays the results
    if (results.length > 0) {
        results = orderResults(results);
        doTheLayout(results, ul);
    }
    
    //Displays the no results for today message
    else { 
        noResultsToday.style.display = "block";
    }
    
    return;
};

//Function which search according to the criteria
function displaySearchEvents(keywordsInput, filterOne, filterTwo, dateFrom, dateTo) {
    
    var results         = [],
        eventsJson      = JSON.parse(sessionStorage.events),
        container       = document.getElementById("searchResult"),
        showMore        = document.getElementById("showMore"),
        noResultsToday  = document.getElementById("noResultsToday"),
        noResults       = document.getElementById("noResults"),
        carousel        = document.getElementsByClassName("nhm-carousel");
    
    //Clears the container
    container.innerHTML = "";
    showMore.style.display = "none";
    noResultsToday.style.display = "none";
    noResults.style.display = "none";
    carousel[0].style.display = "none";
    
    for (var i = 0; i < eventsJson.Events.length;  i++) {
        var isOurEvent  = false,
            eventType   = eventsJson.Events[i].eventType,
            pattern     = "",
            tags        = eventsJson.Events[i].tags;
                
        //Checks the Event Type
        if (checkEventType(eventType)) {
                isOurEvent = true;
        }
        //If not checks the tags
        else {
            for (var k = 0; k < tags.length; k++) {
                var tag = tags[k].split("/");
                if (checkEventType(tag[tag.length - 1])){
                    isOurEvent = true;
                }
            }
        }
        //If we have keyword and the event matched with our type
        if (keywordsInput && isOurEvent) {
            pattern = new RegExp(keywordsInput, 'i');
            
            //If the keyword matched with title, description or keywords
            if (!searchByKey(pattern, eventsJson.Events[i], "keyword")) {
                isOurEvent = false;
            }
        }
        //If the filter matches with the tags
        if (filterOne != "none"  && isOurEvent) {
            if (!searchByKey(filterOne, tags, "tag")) {
                isOurEvent = false;
            }
        }
        if (filterTwo != "none"  && isOurEvent) {
            if (!searchByKey(filterTwo, tags, "tag")) {
                isOurEvent = false;                
            }
        }
        //If the date matches
        if (dateFrom && isOurEvent) {
            var date = getFormattedDate(dateFrom);
            if (!searchByDate(date, eventsJson.Events[i].dates, true)){
                isOurEvent = false; 
            }
        }
        if (dateTo && isOurEvent) {
            var date = getFormattedDate(dateTo);
            if (!searchByDate(date, eventsJson.Events[i].dates, false)){
                isOurEvent = false; 
            }
        }
        //Add the event if matches the query
        if (isOurEvent) {
            results.push(eventsJson.Events[i]);
        }        
    }
    
    //Displays the results
    if (results.length > 0) {
        results = orderResults(results);
        createResultDiv(results, dateFrom, dateTo, container);
    }
    //Displays the error msg
    else {
       noResults.style.display = "block"; 
    }
    
    return;
};

//Function to display the results after a search query
function createResultDiv(results, dateFrom, dateTo, container) {
    var numOfDays;
        
    //If not dateFrom starts today
    if (!dateFrom) {
        dateFrom = new Date();
    } else if (dateFrom) {
        dateFrom = getFormattedDate(dateFrom);
    }
    
    //If not dateTo finish in one year
    if (!dateTo) {
        dateTo = new Date(dateFrom);
        dateTo.setMonth(dateTo.getMonth() + 12);
    } else if (dateTo){
        dateTo = getFormattedDate(dateTo);
    }
    
    dateFrom = dateFrom.setHours(0,0,0,0,0);
    dateTo = dateTo.setHours(0,0,0,0,0);
    
    //Gets the interval of days
    numOfDays = Math.floor(( dateTo - dateFrom) / 86400000);
    
    //If ther is only one date
    if (numOfDays == 0) {
        var titleH2         = document.createElement("h2"),
            ul              = document.createElement("ul");
            
        titleH2.className = "events--header";
        ul.className = "large-block-grid-3 medium-block-grid-3 small-block-grid-1";
        
        titleH2.innerHTML = parseToEventDate(new Date(dateFrom), true);
        
        container.appendChild(titleH2);
        container.appendChild(ul);
        
        doTheLayout(results, ul);
        
        return;
    }

    var date = new Date(dateFrom);
    
    //For each day gets the events
    for (var i = 0; i < numOfDays + 1; i ++) {
        var titleH2         = document.createElement("h2"),
            ul              = document.createElement("ul"),
            auxResults      = [],
            auxDate         = new Date();
        
        titleH2.className = "events--header";
        ul.className = "large-block-grid-3 medium-block-grid-3 small-block-grid-1";
        
        //Sets the date in the title
        titleH2.innerHTML = parseToEventDate(new Date(auxDate.setDate(date.getDate() + i)), true);
                
        container.appendChild(titleH2);
        container.appendChild(ul);
        
        auxDate = new Date(auxDate).setHours(0,0,0,0,0);
        
        for (var j = 0; j < results.length;  j++) {
            
            for (var k = 0; k < results[j].dates.length;  k++) {
                var eventDate = new Date(results[j].dates[k]).setHours(0,0,0,0,0);
                if (eventDate == auxDate) { 
                    auxResults.push(results[j]);
                    break;
                }
            }            
        }
        
        doTheLayout(auxResults, ul);
    }
    return;
};

//Helper function to create the results and add the seach button
function doTheLayout(results, ul){
    var showMore = document.getElementById("showMore"),
        counter  = 0;
    
    for (var i = 0; i < results.length; i ++) {
        counter ++;
        createSearchResult(results[i], ul, counter);
        if (counter > 6) {
            showMore.style.display = "block";
        }
    }
    
    //Adds the listener to the show more div
    showMore.addEventListener('click', function(e){
        showMoreEvents(e, counter, ul, showMore);
    }, false);
    
    return;
} 

//Helper function to get a formatted date from the datepicker inputs
function getFormattedDate(date) {
    var stringDate      = date.split(" "),
        dateNumbers     = stringDate[1].split("/");
    
    return new Date(dateNumbers[2],dateNumbers[1] - 1,dateNumbers[0]);
}

//Function to compare the dates
function searchByDate(date, dates, isFromDate) {
    for (var i = 0; i < dates.length; i++) {
        var eventDate = new Date(dates[i]).setHours(0,0,0,0,0);
        if ((isFromDate && eventDate >= date) || (!isFromDate && eventDate <= date)) {
            return true;
        }
    }
    return false;
};

//Helper function to check the keyword against title, description, keywords or filters
function searchByKey(pattern, element, type) {
    
    if (type === "keyword") {
        if (element.title.match(pattern) || element.description.match(pattern) || element.keywords.match(pattern)){                            
            return true;
        }
    }
    
    if (type === "tag") {
        for (var i = 0; i < element.length; i++) {
            if (element[i] == pattern){
                return true;
            }
        }
    }

    
    return false;
};

//Helper function to check the Event against the page
function checkEventType(eventType) {

    if (eventType == "Science" && sessionStorage.pagePath == "our-science") {
        return true;
    }
    if (eventType == "School" && sessionStorage.pagePath == "schools") {
        return true;
    }
    if (eventType == "Visitor" && sessionStorage.pagePath == "visit") {
        return true;
    }
    if (eventType == "Tring" && sessionStorage.pagePath == "tring-homepage") {
        return true;
    }
    
    return false;
};

//Function to order the search results
function orderResults(results) {
    var orderResults    = [],
        notAllDay       = [],
        finalResults    = [];
    
    //Order the events by name
    results = results.sort(function(a, b) {
        return a.title.localeCompare(b.title);
    });
    
    //Gets the All day first
    for (var i = 0; i < results.length; i++) {
        if (getEventTimes(results[i], false) == "All day") {
            orderResults.push(results[i]);
        }
        else {
            notAllDay.push(results[i]);
        }
    }
    //Order the rest by time
    notAllDay = notAllDay.sort(function(a, b) {
        if (getEventTimes(a, true) > getEventTimes(b, true)) {
            return 1;
        }
        if (getEventTimes(a, true) < getEventTimes(b, true)) {
            return -1;
        }    
        return 0;
    });
    
    orderResults = orderResults.concat(notAllDay);
    
    return orderResults;
};

//Populates the single event li and events ul
function createSearchResult(event, ul, counter) {
    var li              = document.createElement("li"),
        containerDiv    = document.createElement("div"),
        navigateDiv     = document.createElement("div"),
        a               = document.createElement("a"),
        imageDiv        = document.createElement("div"),
        img             = document.createElement("img"),
        h3              = document.createElement("h3"),
        textDiv         = document.createElement("div"),
        paragraph       = document.createElement("p");
    
    containerDiv.className = "small-12 columns";
    navigateDiv.className = "small-12 columns";
    
    //Sets the Event Link
    if (event.tileLink.localeCompare("") == 0) {
        a.href = event.eventPagePath + ".html";  
    }
    else {
        a.href = event.tileLink;    
    }
    
    imageDiv.className = "adaptiveimage parbase foundation5image image";
    h3.className = sessionStorage.pagePath + " event--title";
    textDiv.className = "feed--item--content";
    paragraph.className = "event--info";
    
    
    img.src = event.imageLink;
    h3.innerHTML = event.title;
    paragraph.innerHTML =   getEventDates(event.dates) + "<br/>" +
                            "Event type: <b>" + getEvents(event.tags, event.eventType) + "</b><br/>" +
                            "Time: <b>" + getEventTimes(event, false) + "</b><br/>" +
                            "Ticket price: <b>" + getEventPrice(event.adultPrice, event.concessionPrice, event.customPrice, event.familyPrice, event.memberPrice) + "</b><br/>" +
                            event.description;
    
    imageDiv.appendChild(img);
    a.appendChild(imageDiv);
    a.appendChild(h3);
    containerDiv.appendChild(a);
    textDiv.appendChild(paragraph);
    containerDiv.appendChild(textDiv);
    li.appendChild(containerDiv);
    
    //Hides the results above 6
    if (counter > 6) {
        li.style.display = "none";
    }
    
    ul.appendChild(li);
};

//Helper function to return the events
function getEvents(tags, eventType) {
    var events = "";
    
    for (var i = 0; i < tags.length; i++) {
        var tokens      = tags[i].split("/"),
            firstToken  = tokens[0],
            headers     = firstToken.split(":"),
            lastToken = "";
        
        if (headers[1].localeCompare("events") == 0) {
            lastToken = tokens[tokens.length-1];
            lastToken = lastToken.replace("-", " ");

            if (events.localeCompare("") == 0) {
                events = lastToken;                               
            } 
            else {
                events = events.concat(", " + lastToken);                            
            }
        }
    }
    
    if (events.localeCompare("") == 0) {
        return eventType;
    }
    
    return events;
};

//Helper function to parse the event date
function getEventDates(dates) {
    
    //If there is only one date
    if (dates.length == 1) {
        return parseToEventDate(new Date(dates[dates.length - 1]), false);
    }
    
    //If there are more than one day it gets the last date
    var aux = [];
    for (var i = 0; i < dates.length; i++) {
        var date = new Date(dates[i]);
        aux.push(date.setHours(0,0,0,0,0));
    }
    var lastDate = aux.reduce(function (a, b) { return a > b ? a : b; });
    
    return parseToEventDate(new Date(), false) + " - " + parseToEventDate(new Date(lastDate), false);
};

//Helper function to get the times
function getEventTimes(event, getTimes) {
    var dates       = event.dates,
        allDay      = event.allDay,
        times       = event.times,
        eventTimes  = [],
        today       = new Date(),
        index       = "";
    
    //Gets the index for today's event
    for (var i = 0; i < dates.length; i++) {
        var date = new Date(dates[i]);
        if (today.setHours(0,0,0,0,0) == date.setHours(0,0,0,0,0)) { 
            index = dates[i].substr(dates[i].length - 1);
            break;
        }
    }
    if (index != "" && !getTimes) {        
        if (allDay[index] == "true") {
            return "All day";
        }
        
        eventTimes = times[index].split(",");
        
        if (eventTimes.length == 1) {
            return eventTimes[0];
        }
        
        if (eventTimes.length > 1) {
            return "Times vary";
        }
    }
    
    eventTimes = times[index].split(",");
    var myTime = eventTimes[0].replace(':','');
    
    return parseInt(myTime);
};

//Helper function to convert the dates to string and to parse the date to the correct format
function parseToEventDate(str, isLongMonth) {
    var day             = str.getDate(),
        monthIndex      = str.getMonth(),
        year            = str.getFullYear(),
        shortMonthNames = [ "Jan",  "Feb", "Mar",
                            "Apr",  "May", "June", 
                            "July", "Aug", "Sept", 
                            "Oct",  "Nov", "Dec" 
        ],
        longMonthNames  = [ "January", "February", "March",
                            "April",   "May",      "June", 
                            "July",    "August",   "September", 
                            "October", "November", "December" ];
    if (!isLongMonth) {
        return day.toString() +  " " + shortMonthNames[monthIndex] + " " + year.toString();
    }
    
    return day.toString() +  " " + longMonthNames[monthIndex] + " " + year.toString();
};

//Helper function to get the event price
function getEventPrice(adultPrice, concessionPrice, customPrice, familyPrice, memberPrice) {
    //Remove the last space if exits
    if (customPrice.localeCompare("") != 0) {
        customPrice = customPrice.replace(/\s+$/, "");
    }
    if (customPrice == "Free" || customPrice == "free") {
        return "Free";
    }
    if (adultPrice != "" && concessionPrice == ""  && customPrice == "" && familyPrice == "" && memberPrice == "") {
        return "£" + adultPrice;
    }
    if (adultPrice == "" && concessionPrice != ""  && customPrice == "" && familyPrice == "" && memberPrice == "") {
        return "£" + concessionPrice;
    }
    if (adultPrice == "" && concessionPrice == ""  && customPrice != "" && familyPrice == "" && memberPrice == "") {
        if (/^\d+$/.test(customPrice)) {
            return "£" + customPrice;
        }
        return customPrice;
    }
    if (adultPrice == "" && concessionPrice == ""  && customPrice == "" && familyPrice != "" && memberPrice == "") {
        return "£" + familyPrice;
    }
    if (adultPrice == "" && concessionPrice == ""  && customPrice == "" && familyPrice == "" && memberPrice != "") {
        return "£" + memberPrice;
    }
    return "Vary";
};

//Function to display more events
function showMoreEvents(e, counter, ul, showMore) {
    e.preventDefault();
    var listItem        = ul.getElementsByTagName("li"),
        numOfElements   = 0;
    
    for (var i=0; i < listItem.length; i++) {
        if (numOfElements == 6) {
            return;
        }
        if (listItem[i].offsetParent === null) {
            listItem[i].style.display = "block";
            numOfElements++;
        }
    }
    showMore.style.display = "none";
};
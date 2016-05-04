//Set as global variables to improve the performance
var shortMonthNames = [ "Jan",  "Feb", "Mar",
                        "Apr",  "May", "June", 
                        "July", "Aug", "Sept", 
                        "Oct",  "Nov", "Dec" 
    ],
    longMonthNames  = [ "January", "February", "March",
                        "April",   "May",      "June", 
                        "July",    "August",   "September", 
                        "October", "November", "December" ];

$(document).ready(function() {
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
        results         = [];
    
    //Clears the container
    container.innerHTML = "";
    showMore.style.display = "none";
    noResultsToday.style.display = "none";
    noResults.style.display = "none";
    
    //Prevents if no carousel 
    if (carousel[0] != undefined || carousel[0] != null) {
        carousel[0].style.display = "block";
    }
    
    titleH2.className = "pl-12";
    titleH2.innerHTML = "Today's Events";
    ul.className = "large-block-grid-3 medium-block-grid-3 small-block-grid-1";

    for (var i = 0; i < eventsJson.Events.length;  i++) {
        for (var j = 0; j < eventsJson.Events[i].dates.length;  j++) {
            
            var isOurEvent  = false,
                date        = new Date(eventsJson.Events[i].dates[j]),
                eventType   = eventsJson.Events[i].eventType;
            
            //If date == today
            if (today.setHours(0,0,0,0,0) == date.setHours(0,0,0,0,0)) {
                //Checks the Event Type
                if (isValidEvent(eventType)){
                    results.push(eventsJson.Events[i]);
                }
                else {
                    //If not checks the tags
                    var tags = eventsJson.Events[i].tags;
                    for (var k = 0; k < tags.length; k++) {
                        var tag = tags[k].split("/");
                        if (isValidEvent(tag[tag.length - 1])){
                            results.push(eventsJson.Events[i]);
                            break;
                        }
                    }
                }            
                break;
            }
        }
    }
    
    //Displays the results
    if (results.length > 0) {
        //Appends the title
        container.appendChild(titleH2);
        container.appendChild(ul);
        
        results = orderResults(results);
        renderLayout(results, ul);
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
    
    //Prevents if no carousel 
    if (carousel[0] != undefined || carousel[0] != null) {
        carousel[0].style.display = "none";
    }
    
    
    for (var i = 0; i < eventsJson.Events.length;  i++) {
        var isOurEvent  = false,
            eventType   = eventsJson.Events[i].eventType,
            pattern     = "",
            tags        = eventsJson.Events[i].tags;
                
        //Checks the Event Type
        if (isValidEvent(eventType)) {
                isOurEvent = true;
        }
        //If not checks the tags
        else {
            for (var k = 0; k < tags.length; k++) {
                var tag = tags[k].split("/");
                if (isValidEvent(tag[tag.length - 1])){
                    isOurEvent = true;
                    break;
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
    
    var date = new Date(dateFrom);
    //For each day gets the events
    for (var i = 0; i < numOfDays + 1; i ++) {
        var titleH2         = document.createElement("h2"),
            ul              = document.createElement("ul"),
            auxResults      = [],
            auxDate         = new Date();
        
        titleH2.className = "pl-12";
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
        
        renderLayout(auxResults, ul);
    }
    return;
};

//Helper function to create the results and add the seach button
function renderLayout(results, ul){
    var showMore = document.getElementById("showMore");
    
    for (var i = 0; i < results.length; i ++) {
        createSearchResult(results[i], ul, i);
        if (i == 5) {
            showMore.style.display = "block";
        }
    }
    
    //Adds the listener to the show more div
    if (results.length > 5) {
        showMore.addEventListener('click', function(e){
            showMoreEvents(e, ul, showMore);
        }, false);    
    }
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
function isValidEvent(eventType) {

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
        var timeOfEventA = getEventTimes(a, true),
            timeOfEventB = getEventTimes(b, true);
        
        if (timeOfEventA > timeOfEventB) {
            return 1;
        }
        if (timeOfEventA < timeOfEventB) {
            return -1;
        }    
        return 0;
    });
    
    orderResults = orderResults.concat(notAllDay);
    
    return orderResults;
};

//Populates the single event li and events ul
function createSearchResult(event, ul, resultsDisplayed) {
    var li              = document.createElement("li"),
        containerDiv    = document.createElement("div"),
        navigateDiv     = document.createElement("div"),
        a               = document.createElement("a"),
        aH3             = document.createElement("a"),
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
    h3.className = "pr-11 pl-11 mt-24";
    textDiv.className = "feed--item--content";
    paragraph.className = "mb-6";
    
    
    img.src = event.imageLink;
    aH3.innerHTML = event.title;
    h3.appendChild(aH3);
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
    if (resultsDisplayed > 5) {
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
    else {
        return events;
    }
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
function getEventTimes(event, shouldGetTimes) {
    var dates       = event.dates,
        allDay      = event.allDay,
        times       = event.times,
        eventTimes  = [],
        today       = new Date(),
        key         = "";
    
    //Gets the key for today's event
    for (var i = 0; i < dates.length; i++) {
        var date = new Date(dates[i]);
        if (today.setHours(0,0,0,0,0) == date.setHours(0,0,0,0,0)) { 
            key = dates[i].substr(dates[i].length - 1);
            break;
        }
    }
    if (key != "" && !shouldGetTimes) {        
        if (allDay[key] == "true") {
            return "All day";
        }
        
        eventTimes = times[key].split(",");
        
        if (eventTimes.length == 1) {
            return eventTimes[0];
        }
        
        if (eventTimes.length > 1) {
            return "Times vary";
        }
    }
    
    eventTimes = times[key].split(",");
    
    return parseInt(eventTimes[0].replace(':',''));
};

//Helper function to convert the dates to string and to parse the date to the correct format
function parseToEventDate(date, isLongMonth) {
    var day         = date.getDate(),
        monthIndex  = date.getMonth(),
        year        = date.getFullYear(),
        monthName   = isLongMonth? longMonthNames[monthIndex] : shortMonthNames[monthIndex];
    
    return day.toString() +  " " + monthName + " " + year.toString();
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
function showMoreEvents(e, ul, showMore) {
    e.preventDefault();
    var listItem = ul.getElementsByTagName("li");
    
    for (var i = 0; i < listItem.length; i++) {
        if (i == 6) {
            return;
        }
        if (listItem[i].offsetParent === null) {
            listItem[i].style.display = "block";
        }
    }
    showMore.style.display = "none";
    //Removes the vent listener
    this.removeEventListener('click',arguments.callee,false);
};
//Stores the events JSON in sessionStorage and displays Today Events
window.onload = function (e) {
    var eventsData = CQ.HTTP.get(CQ.HTTP.externalize("/content/nhmwww/eventscontent" + "/events"));
    sessionStorage.events = eventsData.responseText;
    displayTodayEvents();
};

//Populates the single events result content
function displayTodayEvents() {
    var today       = new Date(),
        eventsJson  = JSON.parse(sessionStorage.events),
        container   = document.getElementById("searchResult"),
        showMore    = document.getElementById("showMore"),
        titleH2     = document.createElement("h2"),
        ul          = document.createElement("ul"),
        flag        = true,
        counter     = 0;
    
    showMore.style.display = "none"; 
    titleH2.innerHTML = "Today's Events";
    ul.className = "small-block-grid-3";
    
    //TODO: REMOVE THE LOG
    console.log(eventsJson);
    for (var i = 0; i < eventsJson.Events.length;  i++) {
        var eventFlag = false;
        for (var j = 0; j < eventsJson.Events[i].dates.length;  j++) {
            var date = new Date(eventsJson.Events[i].dates[j]);
            if (parseDate(today, false) == parseDate(date, false)) {
                counter ++;
                if (flag) {
                    flag = false;
                    //Appends the title
                    container.appendChild(titleH2);
                }
                eventFlag = true;
                break;
            }
        }
        if (eventFlag) {
            createSearchResult(eventsJson.Events[i], ul, counter);
            if (counter > 6) {
                showMore.style.display = "table";
            }
        }
    }
    //Adds the listener to the show more div
    showMore.addEventListener('click', function(e){
        showMoreEvents(e, counter, ul, showMore);
    }, false);
    
    container.appendChild(ul);
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
    a.href = event.eventPagePath + ".html";
    imageDiv.className = "adaptiveimage parbase foundation5image image";
    h3.className = "event--title";
    textDiv.className = "feed--item--content";
    paragraph.className = "event--info";
    
    
    img.src = event.imageLink;
    
    h3.innerHTML = event.title;
    paragraph.innerHTML =   getEventDates(event.dates) + "<br/>" +
                            "Event type: <b>" + event.eventType + "</b><br/>" +
                            "Time: <b>" + getEventTimes(event.dates, event.allDay, event.times) + "</b><br/>" +
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

//Helper function to parse the event date
function getEventDates(dates) {
    var lastDate = new Date(dates[dates.length - 1]);
    
    if (dates.length == 1) {
        return parseDate(lastDate, true);
    } 
    return parseDate(new Date(),true) + " - " + parseDate(lastDate,true);
};

//Helper function to get the times
function getEventTimes(dates, allDay, times) {
    var eventTimes  = [],
        index       = "";
    
    for (var i = 0; i < dates.length; i++) {
        var date = new Date(dates[i]);
        if (parseDate(new Date(), false) == parseDate(date, false)) { 
            index = dates[i].substr(dates[i].length - 1);
            break;
        }
    }
    if (index != "") {        
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
};

//Helper function to convert the dates to string and to parse the date to the correct format
function parseDate(str, needsFormat) {
    var day         = str.getDate(),
        monthIndex  = str.getMonth(),
        year        = str.getFullYear();
    
    if (needsFormat) {
        var monthNames = [  
            "Jan",  "Feb", "Mar",
            "Apr",  "May", "June", 
            "July", "Aug", "Sept", 
            "Oct",  "Nov", "Dec"
        ];
        return day.toString() +  " " + monthNames[monthIndex] + " " + year.toString();
    }
    return day.toString() + monthIndex.toString() + year.toString();
};

//Helper function to get the event price
function getEventPrice(adultPrice, concessionPrice, customPrice, familyPrice, memberPrice) {
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
        return "£" + customPrice;
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
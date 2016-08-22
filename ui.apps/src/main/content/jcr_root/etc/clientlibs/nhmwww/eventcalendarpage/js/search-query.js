var NHMSearchQuery = new function () {
    var showMoreValue = Number($('div[data-showmore]').data('showmore'));
    
    function checkIsNaN(o) {
        return typeof(o) === 'number' && isNaN(o);
    }
    
    var CONST = {
        CONTAINER_DIV_CLASS:    "small-12 columns",
        DISPLAY_SHOW_MORE:      "row event--calendar--more--results",
        EVENT_TITLE_CLASS:      "event--calendar--search--result--main--title",
        EVENT_TITLE_TODAY:      "Today's Events",
        EVENT_TITLE_SEARCH:     "Search results",
        EVENT_MONTH_DATES:      ["Jan",  "Feb", "Mar",
                                 "Apr",  "May", "Jun",
                                 "Jul",  "Aug", "Sep",
                                 "Oct",  "Nov", "Dec"],
        SHORT_MONTH_DATES:      ["Jan",  "Feb", "Mar",
                                 "Apr",  "May", "June",
                                 "July", "Aug", "Sept",
                                 "Oct",  "Nov", "Dec"],
        LONG_MONTH_DATES:       ["January", "February", "March",
                                "April",    "May",      "June",
                                "July",     "August",   "September",
                                "October",  "November", "December"],
        NO_RESULTS_CLASS:       "more-results-text__event--calendar--search--results",
        EVENTS_UL_CLASS:        "large-block-grid-3 medium-block-grid-3 small-block-grid-1",
        PARAGRAPH_CLASS:        "event--calendar--search--result--content--text",
        EVENTS_DATA_URL:        "/content/nhmwww/eventscontent" + "/events",
        HIDE_DIV_CLASS:         "event--calendar--more--results--hide",
        TEXT_DIV_CLASS:         "event--calendar--search--result--item--content",
        IMAGE_CLASS:            "adaptiveimage parbase foundation5image image event--calendar--search--result--image",
        LI_CLASS:               "event--calendar--search--result",
        H3_CLASS:               "event--calendar--search--result--title",
        SHOW_MORE:              (checkIsNaN(showMoreValue)?6:showMoreValue),
        MAX_TITLE_CHARS:        45
    };

    var inputs = {};
    var eventsCounter = 0;
    
    //Helper function to create the title
    var createTitleH2 = function () {
        var titleH2 = document.createElement("h2");
        titleH2.className = CONST.EVENT_TITLE_CLASS;
        if (eventsCounter >= CONST.SHOW_MORE) {
            titleH2.style.display = "none";
        }
        return titleH2;
    };
    
    //Helper function to create the ul
    var createUL = function () {
        var ul = document.createElement("ul");
        ul.className = CONST.EVENTS_UL_CLASS;
        return ul;
    };
    
    //Helper function to clear the results container
    var clearContainer = function () {
        inputs.results = [];
        if (inputs.container && 
            inputs.showMore &&
            inputs.noResultsToday &&
            inputs.noResults) {
			inputs.container.innerHTML = "";
            inputs.showMore.className = CONST.HIDE_DIV_CLASS;
            inputs.noResultsToday.className = CONST.HIDE_DIV_CLASS;
            inputs.noResults.className = CONST.HIDE_DIV_CLASS;
        }
    };
    
    // Given a month name, it return month in numeric format
    function getMonthFromString(mon){
       return "JanFebMarAprMayJunJulAugSepOctNovDec".indexOf(mon) / 3 + 1;
    }
    
    // Converts the date obtained in the JSON file in the repository to a Date object
    var jsonDateToDateObject = function(date) {
        date = date.substring(0, date.length - 1); 
        date = date.split(' '); 
        return new Date(getMonthFromString(date[1]) + '/' + date[2] + '/' + date[5]);
    }
    
    // Given a Date it returns all the events that are scheduled for that date.
    var findEventsForDay = function(fromDate) {
        var arrayEvents = [];
        for (var i = 0; i < inputs.results.length; i++) {
            var event = inputs.results[i];
            for (var j = 0; j < event.dates.length; j++) {
                var dateString = event.dates[j];
                var eventDate = jsonDateToDateObject(dateString);
                if (fromDate.getFullYear() == eventDate.getFullYear() &&
                    fromDate.getMonth() == eventDate.getMonth() &&
                    fromDate.getDate() == eventDate.getDate()) {
                    arrayEvents.push(event);
                    break; // No need to check more dates for this event
                }
            }
        }
        return arrayEvents;
    };

    // Function to display the results after a search query
    var createResultDiv = function (dateFrom, dateTo) {
        eventsCounter = 0;
        var numOfDays;
        dateFrom = dateFrom ? getFormattedDate(dateFrom) : new Date();

        //If not dateTo finish in one year
        if (!dateTo) {
            dateTo = new Date(dateFrom);
            dateTo.setMonth(dateTo.getMonth() + 12);
        } else {
            dateTo = getFormattedDate(dateTo);
        }

        dateFrom = dateFrom.setHours(0, 0, 0, 0);
        dateTo = dateTo.setHours(23, 59, 59, 999);

        // Calculates the interval of days
        numOfDays = Math.round((dateTo - dateFrom)/24/60/60/1000);
        var date = new Date(dateFrom);
        
        for (var i = 0; i < numOfDays; i++) {
            var dayEvents = findEventsForDay(date);
            if (dayEvents.length > 0) {
                var titleH2 = createTitleH2();
                titleH2.innerHTML = parseToEventDate(date, true);  
                var ul = createUL();
                inputs.container.appendChild(titleH2);
                inputs.container.appendChild(ul);
                renderLayout(dayEvents, ul, true);
            }
            date.setDate(date.getDate() + 1); // Set the date from the next day
        }
    };

    //Helper function to create the results and add the seach button
    var renderLayout = function (results, ul, isFromSearch) {
        if (results.length > CONST.SHOW_MORE) {
            inputs.showMore.className = CONST.DISPLAY_SHOW_MORE;
        }
        for (var i = 0; i < results.length; i++) {
            eventsCounter++;
            createSearchResult(results[i], ul, i, isFromSearch);
        }
        
        //Adds the listener to the show more div
        if (eventsCounter > CONST.SHOW_MORE) {
            inputs.showMore.className = CONST.DISPLAY_SHOW_MORE; 
            inputs.showMore.addEventListener('click', function (e) {
                showMoreEvents(e, ul, inputs.showMore);
            }, false);
        }  
    };

    //Helper function to get a formatted date from the datepicker inputs
    var getFormattedDate = function (date) {
        var stringDate  = date.split(" "),
            dateNumbers = stringDate[1].split("/");

        return new Date(dateNumbers[2], dateNumbers[1] - 1, dateNumbers[0]);
    };
    
    //Helper function to get a formatted date from the eventsJson
    var getEventsFormattedDate = function (date) {
        var stringDate  = date.split(" ");
        return new Date(stringDate[5], CONST.EVENT_MONTH_DATES.indexOf(stringDate[1]), stringDate[2]);
    };

    //Helper function to check the keyword against title, description
    var searchByKeyword = function (pattern, element) {
        return element.title.match(pattern) || element.description.match(pattern) || element.keywords.match(pattern); 
    };
    
    //Helper function to check the tag against title, description, keywords or filters
    var searchByTag = function (pattern, event) {
        var element = event.tags.concat(event.scienceSubject).concat(event.subject);
        var isTarget = false;
        
        for (var i = 0; i < element.length; i++) {
            if (element[i] == pattern) {
                isTarget = true;
                break;
            }
        }
        
        return isTarget;
    };

    //Helper function to check the Event against the page
    var isValidEvent = function (eventType) {
        if (eventType == "Science" && sessionStorage.pagePath == "our-science") {
            return true;
        }
        if (eventType == "School" && sessionStorage.pagePath == "schools") {
            return true;
        }
        if (eventType == "Visitor" && sessionStorage.pagePath == "visit") {
            return true;
        }
        if (eventType == "Tring" && sessionStorage.pagePath == "tring") {
            return true;
        }
        return false;
    };

    //Function to order the search results
    var orderResults = function () {
        var tempResults     = [],
            notAllDay       = [];

        //Order the events by name
        inputs.results = inputs.results.sort(function (a, b) {
            return a.title.localeCompare(b.title);
        });
        //Gets the All day first
        for (var i = 0; i < inputs.results.length; i++) {
            if (getEventTimes(inputs.results[i], false) == "All day") {
                tempResults.push(inputs.results[i]);
            } else {
                notAllDay.push(inputs.results[i]);
            }
        }
        //Order the rest by time
        notAllDay = notAllDay.sort(function (a, b) {
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

        tempResults = tempResults.concat(notAllDay);

        return tempResults;
    };
    
    var isExhibitionEvent = function(path) {
        var parsedPath = path.split('/');
        return (parsedPath[parsedPath.length - 2] == "exhibitions")
    }

    //Populates the single event li and events ul
    var createSearchResult = function (event, ul, resultsDisplayed, isFromSearch) {
        var li              = document.createElement("li"),
            containerDiv    = document.createElement("div"),
            navigateDiv     = document.createElement("div"),
            a               = document.createElement("a"),
            imageDiv        = document.createElement("div"),
            img             = document.createElement("img"),
            h3              = document.createElement("h3"),
            textDiv         = document.createElement("div"),
            paragraph       = document.createElement("p");

        containerDiv.className = CONST.CONTAINER_DIV_CLASS;
        navigateDiv.className = CONST.CONTAINER_DIV_CLASS;

        //Sets the Event Link
        if (event.tileLink.localeCompare("") === 0) {
            a.href = event.eventPagePath + ".html";
            aH3.href = event.eventPagePath + ".html";
        } else {
            a.href = event.tileLink;
            aH3.href = event.tileLink;
        }

        li.className = CONST.LI_CLASS;
        imageDiv.className = CONST.IMAGE_CLASS;
        h3.className = CONST.H3_CLASS;
        textDiv.className = CONST.TEXT_DIV_CLASS;
        paragraph.className = CONST.PARAGRAPH_CLASS;
        
        // Avoids showing event titles when they are too large
        if (event.title.length > CONST.MAX_TITLE_CHARS) {
            event.title = event.title.substring(0, CONST.MAX_TITLE_CHARS) + "...";
        }

        img.src = event.imageLink;
        
        h3.innerHTML = event.title;
        paragraph.innerHTML = getEventDates(event.dates, isExhibitionEvent(event.eventPagePath)) + "<br/>" +
            "Event type: <b>" + getEvents(event.tags, event.eventType) + "</b><br/>" +
            "Time: <b>" + getEventTimes(event, false) + "</b><br/>" +
            "Ticket price: <b>" + event.eventListingPrice + "</b><br/>" +
            "<div class='event--calendar--search--result--description'>" + event.description + "</div>";

        imageDiv.appendChild(img);
        a.appendChild(imageDiv);
        a.appendChild(h3);
        containerDiv.appendChild(a);
        textDiv.appendChild(paragraph);
        containerDiv.appendChild(textDiv);
        li.appendChild(containerDiv);

        //Hides the results above the maximum number of events set by an author.
        if (eventsCounter > CONST.SHOW_MORE) {
            li.style.display = "none";
        }

        ul.appendChild(li);
    };
    
    var getTagTitle = function(tagId) {
        var tagTitle = "";
        for (var i = 0; i < tagsJson.length; i++) {
            if (tagId == tagsJson[i].name) {
                tagTitle = tagsJson[i].title;
                break;
            }
        }
        return (tagTitle.length>0 ? tagTitle : tagId);
    }

    //Helper function to return the events
    var getEvents = function (tags, eventType) {
        var events = "";
        var firstEvent = true;
        for (var i = 0; i < tags.length; i++) {
            var tagId = tags[i];
            var tokens      = tags[i].split("/"),
                firstToken  = tokens[0],
                headers     = firstToken.split(":");

            if (headers[1] == "events") {
                 var tagTitle = getTagTitle(tagId);
                 
                 if (firstEvent) { // First tag, it must use capital letter in the first character.
                     var firstLetter = tagTitle.charAt(0).toUpperCase(); 
                     var restOfString = tagTitle.substring(1,tagTitle.length); 
                     tagTitle = firstLetter + restOfString; 
                     firstEvent = false;
                 }
                 else { // All events but first must be in lower cases.
                     tagTitle = tagTitle.toLowerCase(); 
                 }
                 events += tagTitle + ", ";
            }
        }
        if (events.length == 0) {
            return eventType;
        } else {
            return events.substring(0, events.length - 2); // Remove the ", " from the last event.
        }
    };

    //Helper function to parse the event date
    var getEventDates = function (dates, isExhibitionEvent) {
        //If there is only one date
        if (dates.length == 1) {
            return parseToEventDate(getEventsFormattedDate(dates[0].substring(0, dates[0].length - 1)), false);
        }

        //If there are more than one day it gets the last date
        var aux = [];
        for (var i = 0; i < dates.length; i++) {
            var date = getEventsFormattedDate(dates[i].substring(0, dates[i].length - 1));
            aux.push(date.setHours(0, 0, 0, 0, 0));
        }
        var lastDate = aux.reduce(function (a, b) {
            return a > b ? a : b;
        });
        var firstDate = aux.reduce(function (a, b) {
            return a < b ? a : b;
        });
        var startDate = new Date(firstDate);
        var endDate = new Date(lastDate);
        var startDateParsed = parseToEventDate(startDate, true);
        var endDateParsed = parseToEventDate(endDate, true);
        var today = new Date();
        
        var finalDate = "";
        
        //c3. For exhibitions only, if the start date has passed, the format should change to: 'Until [end date]'."
        if (isExhibitionEvent && startDate.getTime() < today.getTime()) {
            finalDate = "Until " + endDateParsed;
        }
        //c2. For exhibitions only, if the start date hasn't passed, the format should be: 
        // 1 December 2015 - 15 January 2016 if spanning 2 years, or 1 January - 20 March 2016 if the exhibition starts and ends in the the same year.
        else if (isExhibitionEvent && startDate.getFullYear() == endDate.getFullYear()) {
            finalDate = startDateParsed.replace(" " + startDate.getFullYear(), "") + " - " + endDateParsed;
        }
        else {
            finalDate = startDateParsed + " - " + endDateParsed;
        }
        return finalDate;
    };

    //Helper function to get the times
    var getEventTimes = function (event, shouldGetTimes) {
        var dates       = event.dates,
            allDay      = event.allDay,
            times       = event.times,
            today       = new Date(),
            eventTimes  = [],
            key         = '0',
            result      = '';

        //Gets the key for today's event
        for (var i = 0; i < dates.length; i++) {
            var date = getEventsFormattedDate(dates[i].substring(0, dates[i].length - 1));
            key = dates[i].substr(dates[i].length - 1);
            break;
        }
        
        eventTimes = times[key].split(',');
        
        if(!shouldGetTimes) {
            if (allDay[key] == 'true') {
                result = 'All day';
            } else if (eventTimes.length == 1) {
                result = eventTimes[0].replace(":",".").replace("00.00","midnight");
            } else if (eventTimes.length > 1) {
                result = 'Times vary';
            }
        } else {
            result = parseInt(eventTimes[0].replace(':', ''));
        }

        return result;
    };

    //Helper function to convert the dates to string and to parse the date to the correct format
    var parseToEventDate = function (date, isLongMonth) {
        var day         = date.getDate(),
            monthIndex  = date.getMonth(),
            year        = date.getFullYear(),
            monthName   = isLongMonth ? CONST.LONG_MONTH_DATES[monthIndex] : CONST.SHORT_MONTH_DATES[monthIndex];

        return day.toString() + " " + monthName + " " + year.toString();
    };

    //Function to display more events
    var showMoreEvents = function (e, ul, showMore) {
        e.preventDefault();
        var listItem = ul.getElementsByTagName("li");
        for (var i = 0; i < listItem.length; i++) {
            if (listItem[i].offsetParent === null) {
                listItem[i].style.display = "block";
            }
        }   
        var headers = $("#searchResult h2:hidden");
        for (var j = 0; j < headers.length; j++) {
            var header = headers[j];
            if (header) {
                headers[j].style.display = "block";
            }
        }
        showMore.className = CONST.HIDE_DIV_CLASS;
        //Removes the vent listener
        this.removeEventListener('click', arguments.callee, false);
    };
    
    //Helper function to check the today's events
    var checkTodayEvents = function (event) {
        var eventType = event.eventType;
        
        if (isValidEvent(eventType)) {
           return true;
        } else {
            //If not checks the tags
            var tags = event.tags;
            for (var k = 0; k < tags.length; k++) {
                var tag = tags[k].split("/");
                if (isValidEvent(tag[tag.length - 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    this.init = function () {
        //Stores the events JSON in sessionStorage and displays Today Events
        var eventsData  = CQ.HTTP.get(CQ.HTTP.externalize(CONST.EVENTS_DATA_URL)),
            pagePath    = CQ.WCM.getPagePath();

        sessionStorage.events = eventsData.responseText;

        pagePath = pagePath.split("/");
        sessionStorage.pagePath = pagePath[pagePath.length - 2];

        inputs = {
            eventsJson:     JSON.parse(sessionStorage.events).Events,
            carousel:       document.getElementsByClassName("nhm-carousel"),
            showMore:       document.getElementById("showMore"),
            noResultsToday: document.getElementById("noResultsToday"),
            noResults:      document.getElementById("noResults"),
            container:      document.getElementById("searchResult"),
            results:        []
        };
    };
    
    //Populates the single events result content
    this.displayTodayEvents = function () {
        clearContainer();
        eventsCounter = 0;
        //Prevents if no carousel
        if (inputs.carousel) {
            var carousel = inputs.carousel[0];
            if (carousel != undefined || carousel != null) {
                carousel.style.display = "block";
            }

            var titleH2 = createTitleH2(),
                ul      = createUL(),
                today   = new Date();

            titleH2.innerHTML = CONST.EVENT_TITLE_TODAY;

            for (var i = 0; i < inputs.eventsJson.length; i++) {
                var eventsJson  = inputs.eventsJson[i];
                for (var j = 0; j < eventsJson.dates.length; j++) {
                    var date = getEventsFormattedDate(eventsJson.dates[j].substring(0, eventsJson.dates[j].length - 1));
                    //If date == today
                    if (today.setHours(0, 0, 0, 0, 0) == date.setHours(0, 0, 0, 0, 0)) {
                        //Checks the Event Type
                        if (checkTodayEvents(eventsJson)) {
                            inputs.results.push(eventsJson);
                        }
                        break;
                    }
                }
            }

            //Displays the results
            if (inputs.results.length > 0) {
                //Appends the title
                inputs.container.appendChild(titleH2);
                inputs.container.appendChild(ul);

                inputs.results = orderResults();
                renderLayout(inputs.results, ul, false);
            }

            //Displays the no results for today message
            else {
                inputs.noResultsToday.className = CONST.NO_RESULTS_CLASS;
            }
        }

    };
    
    var formattedTags = function(tagArray) {
        var result = [];
        for (var i = 0; i < tagArray.length; i++) {
            var tag = tagArray[i];
            result.push(tag.split('/')[1]);
        }
        return result;
    }
    
    var hasAllTags = function(paramsTag, eventTags) {
        var containsAllTags = true;
        var paramsTagList = paramsTag.split(',');
        for (var i = 0; i < paramsTagList.length; i++) {
            var param = paramsTagList[i];
            var found = $.inArray(param, eventTags) > -1;
            if (!found) {
                containsAllTags = false;
                break;
            }
        } 
        return containsAllTags;
    };
    
    var hasAllKeywords = function(paramsKeywords, eventKeywords) {
        eventKeywords = eventKeywords.split(" ");
        var containsAllKeywords = true;
        var paramsKeywordsList = paramsKeywords.split(',');
        for (var i = 0; i < paramsKeywordsList.length; i++) {
            var keyword = paramsKeywordsList[i];
            var found = $.inArray(keyword, eventKeywords) > -1;
            if (!found) {
                containsAllKeywords = false;
                break;
            }
        } 
        return containsAllKeywords;
    };
    
    var monthNumber = {
        'Jan':'01','Feb':'02','Mar':'03','Apr':'04','May':'05','Jun':'06',
        'Jul':'07','Aug':'08','Sep':'09','Oct':'10','Nov':'11','Dec':'12'    
    }
    
    var dateToSearchFormat = function(date) {
        var parsedDate = date.split(" ");
        var month = monthNumber[parsedDate[1]]; // Gets the month number in the format MM
        var day = parsedDate[2];
        var parsedYear = parsedDate[parsedDate.length-1];
        var year = parsedYear.substring(0, parsedYear.length - 1);
        return month + '/' + day + '/' + year;    
    }
    
    
    var isAfterDate = function(paramFromDate, arrayDates, event) {
        if (typeof arrayDates == 'undefined' || arrayDates.length == 0) return false;
        paramFromDate = Date.parse(paramFromDate);
        var lastDate = arrayDates.reduce(function (a, b) {
            return Date.parse(dateToSearchFormat(a)) >= Date.parse(dateToSearchFormat(b)) ? a : b;
        });
        return (paramFromDate <= Date.parse(dateToSearchFormat(lastDate)));
    }
    
    var isBeforeDate = function(paramToDate, arrayDates, event) {
        if (typeof arrayDates == 'undefined' || arrayDates.length == 0) return false;
        paramToDate = Date.parse(paramToDate);
        var firstDate = arrayDates.reduce(function (a, b) {
            return Date.parse(dateToSearchFormat(a)) < Date.parse(dateToSearchFormat(b)) ? a : b;
        });
        return (paramToDate >= Date.parse(dateToSearchFormat(firstDate)));
    }
    
    var containsText = function(paramText, title, description) {
        var pattern = new RegExp(paramText.replace('+',' '),"g");
        return title.match(pattern) || description.match(pattern);
    }
    
    this.displayFilteredEvents = function (params) {
        clearContainer();
        //Prevents if no carousel
        if (inputs.carousel) {
            var carousel = inputs.carousel[0];
            if (carousel != undefined || carousel != null) {
                carousel.style.display = "block";
            }

            var titleH2 = createTitleH2(),
                ul      = createUL(),
                today   = new Date();

            titleH2.innerHTML = CONST.EVENT_TITLE_SEARCH;

            for (var i = 0; i < inputs.eventsJson.length; i++) {
                var eventsJson  = inputs.eventsJson[i];
                var allFiltersPassed = true;
                if (params.group) allFiltersPassed = params.group == eventsJson.group;
                if (allFiltersPassed && params.title) allFiltersPassed = params.title.replace(/\+/g,' ') == eventsJson.title; 
                if (allFiltersPassed && params.description) allFiltersPassed = params.description.replace(/\+/g,' ') == eventsJson.description; 
                if (allFiltersPassed && params.venue) allFiltersPassed = params.venue.replace(/\+/g,' ') == eventsJson.venue;
                if (allFiltersPassed && params.link) allFiltersPassed = params.link == eventsJson.ctaLink;
                if (allFiltersPassed && params.tags) allFiltersPassed = hasAllTags(params.tags,formattedTags(eventsJson.tags));
                if (allFiltersPassed && params.keywords) allFiltersPassed = hasAllKeywords(params.keywords,eventsJson.keywords);    
                if (allFiltersPassed && params.from) allFiltersPassed = isAfterDate(params.from,eventsJson.dates, eventsJson);
                if (allFiltersPassed && params.to) allFiltersPassed = isBeforeDate(params.to,eventsJson.dates);
                if (allFiltersPassed && params.text) allFiltersPassed = containsText(params.text, eventsJson.title, eventsJson.description);    
                if (allFiltersPassed) {
                    for (var j = 0; j < eventsJson.dates.length; j++) {
                        var date = getEventsFormattedDate(eventsJson.dates[j].substring(0, eventsJson.dates[j].length - 1));
                        inputs.results.push(eventsJson);
                        break;
                    }
                }
            }

            //Displays the results
            if (inputs.results.length > 0) {
                //Appends the title
                inputs.container.appendChild(titleH2);
                inputs.container.appendChild(ul);

                inputs.results = orderResults();
                renderLayout(inputs.results, ul, false);
            }

            //Displays the no results for today message
            else {
                inputs.noResults.className = CONST.NO_RESULTS_CLASS;
            }
        }

    };
    
    /*
     * Gets a date in the format mm/dd/yyyy
     * @param date, a Date object
     */
    var getOnlyDate = function(date) {
        var dd = date.getDate();
        var mm = date.getMonth()+1;
        var yyyy = date.getFullYear();
            if(mm<10){
                mm='0'+mm
            } 
        return mm + '/' + dd + '/' + yyyy;
    }
    
    var isBetweenDates = function(initialDate, finalDate, arrayDates) {
        var start = Date.parse(initialDate);
        var final = Date.parse(finalDate);
        isInBetween = false;
        if (arrayDates.length > 0 ) {

            var firstDate = arrayDates.reduce(function (a, b) {
                return Date.parse(dateToSearchFormat(a)) < Date.parse(dateToSearchFormat(b)) ? a : b;
            });
            var lastDate = arrayDates.reduce(function (a, b) {
                return Date.parse(dateToSearchFormat(a)) >= Date.parse(dateToSearchFormat(b)) ? a : b;
            });
            
            firstDate = Date.parse(dateToSearchFormat(firstDate));
            lastDate = Date.parse(dateToSearchFormat(lastDate));
            
            // Four possible cases of overlapping between a given event
            // and a range of dates
            if ((firstDate <= start && lastDate >= start && lastDate <= final) ||
               (firstDate >= start && lastDate <= final) || 
               (firstDate >= start && firstDate <= final && lastDate >= final) || 
               (firstDate <= start && lastDate >= final)) {
                    isInBetween = true;   
               }    
        }
        return isInBetween;
    }

    //Function which search according to the criteria
    this.displaySearchEvents = function (keywordsInput, filterOne, filterTwo, dateFrom, dateTo) {
        clearContainer();

        //Prevents if no carousel
        if (inputs.carousel) {
            var carousel = inputs.carousel[0];
            if (carousel != undefined || carousel != null) {
                carousel.style.display = "none";
            }

            for (var i = 0; i < inputs.eventsJson.length; i++) {
                var pattern     = "",
                    eventsJson  = inputs.eventsJson[i];

                if (!checkTodayEvents(eventsJson)) { 
                    continue;
                }

                //If we have keyword and the event matched with our type
                if (keywordsInput) {
                    pattern = new RegExp(keywordsInput, 'i');

                    //If the keyword matched with title, description or keywords
                    if (!searchByKeyword(pattern, eventsJson)) {
                        continue;
                    }
                }
                //If the filter matches with the tags
                if (filterOne != "none") {
                    if (!searchByTag(filterOne, eventsJson)) {
                        continue;
                    }
                }
                if (filterTwo != "none") {
                    if (!searchByTag(filterTwo, eventsJson)) {
                        continue;
                    }
                }
                
                //If the date matches
                var initialDate;
                if (!dateFrom) {
                     initialDate = getOnlyDate(new Date()); // Do not filter events from the past.
                }
                else {
                    var dateSplitted = dateFrom.split(' ')[1].split('/');
                    initialDate = dateSplitted[1] + '/' + dateSplitted[0] + '/' + dateSplitted[2];
                }
                
                var finalDate;
                if (!dateTo) {
                     var date = new Date(initialDate);
                     date.setFullYear(date.getFullYear() + 1); // One year time if user does not set the ToDate field
                     finalDate = getOnlyDate(date);
                }
                else {
                    var dateSplitted = dateTo.split(' ')[1].split('/');
                    finalDate = dateSplitted[1] + '/' + dateSplitted[0] + '/' + dateSplitted[2];
                }
                if (!isBetweenDates(initialDate, finalDate, eventsJson.dates)) {
                    continue;
                }
                
                // The event matches all the query parameters.
                inputs.results.push(eventsJson);
            }
            //Displays the results
            if (inputs.results.length > 0) {
                inputs.results = orderResults();
                createResultDiv(dateFrom, dateTo);
            }
            //Displays the error msg
            else {
                inputs.noResults.className = CONST.NO_RESULTS_CLASS;
            }
        }

    };
};

$(document).ready(function () {
    $("#events-calendar-loading").hide();
    eventsCounter = 0;
});
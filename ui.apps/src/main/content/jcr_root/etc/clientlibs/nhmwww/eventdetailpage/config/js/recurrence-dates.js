//Function to validate the datepickers
function createDates(dlg) {
    var datesPanel          = dlg.getField('./dateAndTime'),
        datesSubpanels      = datesPanel.findByType('nhm-multi-field-panel'),
        datesRecurrence     = dlg.findByType('textfield')[0],
        allDayRecurrence    = dlg.findByType('textfield')[1],
        timesRecurrence     = dlg.findByType('textfield')[2],
        durationsRecurrence = dlg.findByType('textfield')[3],
        eventPagePath       = dlg.findByType('textfield')[4],
        soldOut 			= dlg.findByType('textfield')[5],
        jsonString          = '',
        mainDates           = [],
        allDays             = [],
        timesArray          = [],
        durationsArray      = [],
        daysCounter         = 0,
        EventDates          = '',
        isRecurring         = true;

    //Initialise arrays for sold out
    //Get current times array and dates array
    var currentTimesArray = [],
        currentDatesList = [],
        currentDatesArray = [],
    	countDatesArray = [];

    //Dates
    if(datesRecurrence.value != undefined) {
        if(datesRecurrence.value.includes(',')) {
            currentDatesList = datesRecurrence.getValue().split(",");
        } else {
            currentDatesList.push(datesRecurrence.value);
        }
        currentDatesArray = getMixedDatesArray(currentDatesList);
    }

    //Times
	if(timesRecurrence.getValue() != "") {
        currentTimesArray = timesRecurrence.getValue().split("],[");
    }

    for(var i=0; i<currentTimesArray.length; i++) {
        currentTimesArray[i] = currentTimesArray[i].replace(new RegExp('\\[|\\]|"', 'g'), '').split(",");
    }

    //Reset dialog values - to be populated later
    datesRecurrence.setValue('');
    timesRecurrence.setValue('');

    //Sets the page path
    eventPagePath.setValue(CQ.WCM.getPagePath());

    for (var m=0;m<datesSubpanels.length;m++) {
        var subpanel = datesSubpanels[m];
        if(subpanel.name === './dateAndTime') {

            var multi               = subpanel.findByType('multifield'),
                dates               = subpanel.findByType('datetime'),
                selectionFields     = subpanel.findByType('selection'),
                durations           = subpanel.findByType('numberfield');

            isRecurring = true;
            
            //Gets the main days and if it's an All day event
            //Populates All Day array
            for (var k = 0; k < selectionFields.length; k++) {
                if (selectionFields[k].fieldLabel === 'All Day Event') {
                    if (selectionFields[k].getValue()[0]) {
                        allDays.push(true);
                    } else {
                        allDays.push(false);
                    }
                } else if (selectionFields[k].fieldLabel === 'Recurring') {
                    var fieldValue = selectionFields[k].getValue();
                    if(!fieldValue.length || !fieldValue[0]) {
                        //Populates the main date Array
                        for (var j = 0; j < dates.length; j++) {
                            if (dates[j].fieldLabel === 'Date') {
                                var mainDateValue = dates[j].getValue();
                                mainDates.push(mainDateValue);
                                if (daysCounter > 0) {
                                    EventDates += ',';
                                }
                                EventDates += mainDateValue + daysCounter;
                                daysCounter++;
                                isRecurring = false;
                                break;
                            }
                        }
                    }
                }
            }

            //Populates the durations array
            for (var l = 0; l < durations.length; l++) {
                if (durations[l].fieldLabel === 'Duration') {
                    var value = durations[l].getValue();
                    value = value === '' ? 0 : value;
                    durationsArray.push(value);
                }
            }

            for (var i = 0; i < multi.length; i++) {

                strDaysCounter = daysCounter.toString();

                //Gets the times
                if (multi[i].title === 'Add Times') {

                    var time    = multi[i].findByType('timefield'),
                        times   = [];

                    for (var j = 0; j < time.length; j++) {
                        times.push(time[j].getValue());
                    }
                    timesArray.push(times);
                }

                //Gets the recurrences only if it's not a single date event
                if (isRecurring && multi[i].title === 'Recurrence') {

                    var panel           = multi[i].findParentByType('panel'),
                        startDate       = multi[i].findByType('datetime')[0];

                    //Sets the first date
                    if (daysCounter > 0) {
                        EventDates += ',';
                    }

                    if (startDate){

                        var startDateValue  = parseDate(startDate.getValue()),
                            endDate         = multi[i].findByType('datetime')[1],
                            endDateValue    = parseDate(endDate.getValue()),
                            recurSelectList = multi[i].findByType('selection')[0],
                            option          = recurSelectList.getValue(),
                            weekDayList     = multi[i].findByType('selection')[3],
                            weekdays        = weekDayList.getValue();                    

                        switch(option[0]) {

                            case 'daily':

                                EventDates += createDayDates(weekdays, null, startDateValue, endDateValue, strDaysCounter);

                                break;

                            case 'weekly':

                                var weekRepeat  = multi[i].findByType('numberfield')[0],
                                    numberfield = weekRepeat.getValue();

                                EventDates += createWeekDates(weekdays, numberfield, startDateValue, endDateValue, strDaysCounter);

                                break;

                            case 'monthly':

                                var customMonthCheck    = multi[i].findByType('selection')[1],
                                    repeatList          = multi[i].findByType('selection')[2],
                                    daysList            = multi[i].findByType('selection')[4],
                                    dayRepeat           = multi[i].findByType('numberfield')[1],
                                    monthRepeat         = multi[i].findByType('numberfield')[2],
                                    isCustom            = customMonthCheck.getValue(),
                                    dayNumber           = dayRepeat.getValue(),
                                    monthNumber         = monthRepeat.getValue(),
                                    repeatListValue     = repeatList.getValue(),
                                    daysListValue       = daysList.getValue();

                                EventDates += createMonthDates (weekdays, isCustom, dayNumber, monthNumber, repeatListValue, daysListValue, startDateValue, endDateValue, strDaysCounter);

                                break;        
                        }   
                    }
                    daysCounter++;
                }     
            }
        }
    }

	//Replace needed to remove empty ,,
    var aggregatedDates = removeConflictDates(EventDates.replace(/,,/g,',').split(',')),
        newDatesArray = getMixedDatesArray(aggregatedDates);

	//All day
    var tempAllDayArray = [];

    for(var i=0; i<newDatesArray.length; i++) {
        if(Array.isArray(newDatesArray[i])) {
            for(var j=0; j<newDatesArray[i].length; j++) {
				tempAllDayArray.push(allDays[i]);
            }
        } else {
			tempAllDayArray.push(allDays[i]);
        }	
    }

    //Populate times arrays to be used for adding/removing dates/times
    var reccurrenceTimesArray = [];
    for(var i=0; i<timesArray.length; i++) {
		reccurrenceTimesArray.push(timesArray[i]);
    }

    for(var i=0; i<currentDatesArray.length; i++) {
        if(Array.isArray(currentDatesArray[i])) {
            var tempCurrentTimesArray = [];
            for(var j=0; j<currentDatesArray[i].length; j++) {
				tempCurrentTimesArray.push(currentTimesArray[i]);
            }
            currentTimesArray[i] = tempCurrentTimesArray;
        }
    }

    for(var i=0; i<newDatesArray.length; i++) {
        if(Array.isArray(newDatesArray[i])) {
            var tempCurrentTimesArray = [];
            for(var j=0; j<newDatesArray[i].length; j++) {
				tempCurrentTimesArray.push(reccurrenceTimesArray[i]);
            }
            reccurrenceTimesArray[i] = tempCurrentTimesArray;
        }
    }

	//Populate sold out array
	var soldOutArray = [];

    if(soldOut.value !== undefined) {
    	soldOutArray = soldOut.getValue().split("], [");
    }

    for(var i=0;i<soldOutArray.length;i++) {
        if(soldOutArray[i].match('\\],\\[')) {
			soldOutArray[i] = soldOutArray[i].split("],[");
            for(var j=0; j<soldOutArray[i].length; j++) {
				soldOutArray[i][j] = soldOutArray[i][j].replace(new RegExp('\\[|\\]|"', 'g'), '').split(",");
            }
        } else {
        	soldOutArray[i] = soldOutArray[i].replace(new RegExp('\\[|\\]|"', 'g'), '').split(",");
        }
    }

	//Initialise existing pages
    if(soldOutArray.length == 0 && currentTimesArray.length != 0) {
        for(var i=0; i<currentTimesArray.length; i++) {
            var subArray = [];
            for(var j=0; j<currentTimesArray[i].length; j++) {
                if(Array.isArray(currentTimesArray[i][j])) {
					var subSubArray = [];
                    for(var k=0; k<currentTimesArray[i][j].length; k++) {
						subSubArray[k] = "false";
                    }
                    subArray[j] = subSubArray;
                } else {
					subArray[j] = "false";
                }
            }

            soldOutArray[i] = subArray;
        }
    }

    //A date is added
    var diff = 0;

    if(currentTimesArray.length == 1 && currentTimesArray[0] == "") {
        diff = timesArray.length;
    } else if(timesArray.length > currentTimesArray.length) {
        diff = timesArray.length - currentTimesArray.length;
    }

    if(diff > 0) {
        for(var i=currentTimesArray.length; i<timesArray.length; i++) {
			var emptySubArray = [];
            var soldOutSubArray = [];

            //If recurrence date
            if(Array.isArray(newDatesArray[i])) {
                if(allDays[i]) {
					for(var j=0; j<newDatesArray[i].length; j++) {
						var emptySubSubArray = [];
                        var soldOutSubSubArray = [];

                        for(var k=0; k<reccurrenceTimesArray[i].length; k++) {
                            emptySubSubArray[0] = "";
                            soldOutSubSubArray[0] = "false";
                        }
						emptySubArray[j] = emptySubSubArray;
                        soldOutSubArray[j] = soldOutSubSubArray;
                    }
                } else {
					for(var j=0; j<newDatesArray[i].length; j++) {
                        var emptySubSubArray = [];
                        var soldOutSubSubArray = [];

                        for(var k=0; k<reccurrenceTimesArray[i][j].length; k++) {
                            emptySubSubArray[k] = "";
                            soldOutSubSubArray[k] = "false";
                        }
                        emptySubArray[j] = emptySubSubArray;
                        soldOutSubArray[j] = soldOutSubSubArray;
                    }
                }
            } else {
                if(allDays[i]) {
                    emptySubArray[0] = "";
                    soldOutSubArray[0] = "false";
                } else {
                    for(var j=0; j < timesArray[i].length; j++) {
                        emptySubArray[j] = "";
                        soldOutSubArray[j] = "false";
                    }
                }
        	}
            currentTimesArray[i] = emptySubArray;
            soldOutArray[i] = soldOutSubArray;
        }
    }

    //A date is removed - TODO
    if(currentDatesList.length > aggregatedDates.length) {
        for(var i=0; i<currentDatesArray.length; i++) {
            if(Array.isArray(currentDatesArray[i])) {
                for(var j=0; j<currentDatesArray[i].length; j++) {
                    var dateExists = false;
                    for(var k=0; k<aggregatedDates.length; k++) {
						if(currentDatesArray[i][j].match('^([a-zA-Z0-9:( +]+)\\)')[0] == aggregatedDates[k].match('^([a-zA-Z0-9:( +]+)\\)')[0]) {
                            dateExists = true;
                            break;
                        }
                    }
                    if(dateExists == false) {
						soldOutArray[i][j] = "todelete";
                    }
                }
            } else {
				var dateExists = false;
                for(var j=0; j<aggregatedDates.length; j++) {
                    if(currentDatesArray[i].match('^([a-zA-Z0-9:( +]+)\\)')[0] == aggregatedDates[j].match('^([a-zA-Z0-9:( +]+)\\)')[0]) {
                        dateExists = true;
                        break;
                    }
                }
                if(dateExists == false) {
                    soldOutArray[i] = "todelete";
                }
            }
        }
        for(var i=(soldOutArray.length - 1); i>-1; i--) {
            if(Array.isArray(soldOutArray[i][0])) {
                for(var j=(soldOutArray[i].length -1); j>-1; j--) {
                    if(soldOutArray[i][j] == "todelete") {
						soldOutArray[i].splice(j, 1);
                        currentDatesArray[i].splice(j, 1);
                    }
                }
            } else {
                if(soldOutArray[i] == "todelete") {
                    soldOutArray.splice(i, 1);
                    currentDatesArray.splice(i, 1);
                }
            }
        }
    }

    //A time is added/removed
    if(reccurrenceTimesArray.length == currentTimesArray.length) {
        for(var i=0; i<currentTimesArray.length; i++) {
            if(!allDays[i]) {
                //A time has been added to an existing date
                if(Array.isArray(reccurrenceTimesArray[i][0])) {
                    if(reccurrenceTimesArray[i][0].length > currentTimesArray[i][0].length) {
                        for(var j=0; j<reccurrenceTimesArray[i].length; j++) {
                            for(var k=0; k<reccurrenceTimesArray[i][j].length; k++) {
                                var cur = reccurrenceTimesArray[i][j][k];
                                var exists = false;
                                for(var l=0; l<currentTimesArray[i][j].length; l++) {
                                    if(currentTimesArray[i][j][l] == cur) {
										exists = true;
                                    }
                                }
                                if(exists == false) {
									soldOutArray[i][j].splice(k, 0, "false");
                                }
                            }
                        }
                    }
                } else {
                    if(reccurrenceTimesArray[i].length > currentTimesArray[i].length) {
                        for(var j=0; j<reccurrenceTimesArray[i].length; j++) {
                            var cur = reccurrenceTimesArray[i][j];
                            var exists = false;
                            for(var k=0; k<currentTimesArray[i].length; k++) {
                                if(currentTimesArray[i][k] == cur) {
                                    exists = true;
                                }
                            }
                            if(exists == false) {
                                soldOutArray[i].splice(j, 0, "false");
                            }
                        }
                    }
                }

                //A time has been removed from an existing date
                if(Array.isArray(currentTimesArray[i][0])) {
                    //This could be made more efficient by only going through the first array - the same value
                    //will be spliced from all instances within soldOutArray
                    if(reccurrenceTimesArray[i][0].length < currentTimesArray[i][0].length) {
                        for(var j=0; j<currentTimesArray[i].length; j++) {
                            for(var k=0; k<currentTimesArray[i][j].length; k++) {
                                var timeExists = false;
                                for(var l=0; l<reccurrenceTimesArray[i][j].length; l++) {
                                    if(currentTimesArray[i][j][k] === reccurrenceTimesArray[i][j][l]) {
                                        timeExists = true;
                                    }
                                }
                                if(timeExists == false) {
                                    soldOutArray[i][j].splice(k, 1);
                                }
                            }
                        }
                    }
                } else {
                    if(reccurrenceTimesArray[i].length < currentTimesArray[i].length) {
                        for(var j=0; j<currentTimesArray[i].length; j++) {
                            var timeExists = false;
                            for(var k=0; k<reccurrenceTimesArray[i].length; k++) {
                                if(currentTimesArray[i][j] == reccurrenceTimesArray[i][k]) {
                                    timeExists = true;
                                }
                            }
                            if(timeExists == false) {
                                soldOutArray[i].splice(j, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    //Create sold out string to store in repo
    var soldOutString = stringifySoldOutArray(soldOutArray);

    //Save values back to repo
	allDayRecurrence.setValue(JSON.stringify(tempAllDayArray));
    durationsRecurrence.setValue(JSON.stringify(durationsArray));
    timesRecurrence.setValue(JSON.stringify(timesArray));
	datesRecurrence.setValue(aggregatedDates);
    soldOut.setValue(soldOutString);
}

//Function to turn soldOutArray into 'array' string
function stringifySoldOutArray(soldOutArray) {
    var soldOutString = "[";

    for(var i=0; i<soldOutArray.length; i++) {
        soldOutString += "[";
        if(Array.isArray(soldOutArray[i])) {
            for(var j=0; j<soldOutArray[i].length; j++) {
                if(Array.isArray(soldOutArray[i][j])) {
                    soldOutString += "[";
                    for(var k=0; k<soldOutArray[i][j].length; k++) {
						soldOutString += soldOutArray[i][j][k] + ",";
                    }
                    soldOutString = soldOutString.slice(0,-1) + "],";
                } else {
					soldOutString += soldOutArray[i][j] + ",";
                }
            }
        }
        soldOutString = soldOutString.slice(0,-1) + "], ";
    }
    soldOutString = soldOutString.slice(0,-2) + "]";

    return soldOutString;
}

//Function to get mixed array of recurring dates (in array) and single dates (in string)
function getMixedDatesArray(currentArray) {
	var currentIndex = 0;
    var subArray = [];
    var newDatesArray = [];
    for(var i=0; i<currentArray.length; i++) {
        var dateIndex = currentArray[i].match(/(\d)+$/)[0];
        if(dateIndex == currentIndex) {
            subArray.push(currentArray[i]);
        } else {
            if(subArray.length == 1) {
                newDatesArray.push(subArray[0]);
            } else {
                newDatesArray.push(subArray);
            }
            subArray = [];
            subArray.push(currentArray[i]);
            currentIndex++;
        }
    }
    //Push last date set
    if(subArray.length == 1) {
        newDatesArray.push(subArray[0]);
    } else {
        newDatesArray.push(subArray);
    }

	return newDatesArray;
}

//Function to remove conflict dates
function removeConflictDates(eventDates) {
    var toRemoveIndex = [];
    
    //Removes the empty values
    eventDates = eventDates.filter(date => date !== '');

    toRemoveIndex = getIndexToRemove(getSingleItemsIndex(eventDates), eventDates);

    //Removes the recurrence dates which are in conflict with single dates
    for (var i = toRemoveIndex.length - 1; i >= 0; i--) {
        eventDates.splice(toRemoveIndex[i], 1);
    }
    
    //Removes the duplicated dates
    var finalDates = resetTimeStamp(eventDates).filter(function (elem, index, self) {
        return index === self.indexOf(elem);
    });

    return finalDates;
}

//Function to get the index of the single dates
function getSingleItemsIndex(dates) {
    var indexArray = [];

    for (var i = 0; i < dates.length; i++) {
        //Gets the index placed in the last position of the date string
        var index   = dates[i].slice(-1),
            counter = 0;
        
        for (var j = 0; j < dates.length; j++) {
            var innerIndex = dates[j].slice(-1);
            if (index === innerIndex) {
                counter++;
                if (counter > 1){
                    break;
                }
            }
        }
        if (counter === 1) {
            indexArray.push(i);
        }
    }
    return indexArray;
}

//Function to get the index on the dates to be removed    
function getIndexToRemove(singleItemArray, dates) {
    var indexToRemove = [];
    
    for (var i = 0; i < singleItemArray.length; i++) {
        var dateString = createDateString(dates[singleItemArray[i]]);
        for (var j = dates.length - 1; j >= 0; j--) {
            if (j !== singleItemArray[i]) {
                var dateStringToCompare = createDateString(dates[j]);
                if (dateString === dateStringToCompare) {
                    indexToRemove.push(j);
                }
            }
        }
    }
    return indexToRemove.sort();
}

//Helper function to create a String from a date entered
function createDateString(date) {
    var dateArray = date.split(' ');
    return dateArray[1] + dateArray[2] + dateArray[3];
}

//Function to remove the timestamp and set all the times to 00:00:00
function resetTimeStamp(dates) {
    var datesString = [];
    for (var i = 0; i < dates.length; i++) {
        var dateArrayCompared = dates[i].split(' ');
        datesString.push(dateArrayCompared[0] + ' ' + dateArrayCompared[1] + ' ' + dateArrayCompared[2] + ' ' + dateArrayCompared[3] + ' 00:00:00 ' + dateArrayCompared[5] + ' ' + dates[i].match(/\((.*)\)/)[0] + getArrayValue(dates[i]));
    }
    return datesString;
}

//Function to get position of date group in array
function getArrayValue(dates) {
	var pos = dates.match(/(\d+)$/gm);
	return pos;
}

//Function to generate Daily and Weekly dates
function createDayDates (weekdays, numberfield, startDate, endDate, strDaysCounter) {
    var parserText = 'on ';
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ', ' + weekdays[i];
    }
    
    if (numberfield !== null) {
        parserText += ' every ' + numberfield + ' weeks';
    }
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000, startDate);

    parserText = occurrences[0] + strDaysCounter;
    
    for (var x = 1; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - endDate > 0) {
            break;
        }
        parserText += ',' + occurrences[x] + strDaysCounter;
    }
    return parserText;
}

//Function to generate Weekly dates
function createWeekDates (weekdays, numberfield, startDate, endDate, strDaysCounter) {
    var parserText = 'on ';
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ', ' + weekdays[i];
    }
    
    if (numberfield !== null) {
        parserText += ' every ' + numberfield + ' weeks';
    }
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000, startDate);
    
    //WR-971, alisp2: Apologies this is a bit of a hack job
    //Use function to get diff between desired week numbers and week numbers
    //provided by Later.js then re-run to find new occurrences
    
    var weekNumber = getWeekNumber(startDate);
    var diff = weekNumber - sched.schedules[0].wy[0];
    
    for(var i=0; i<sched.schedules[0].wy.length; i++) {
        sched.schedules[0].wy[i] = sched.schedules[0].wy[i] + diff;
        console.log(sched.schedules[0].wy[i]);
      }
      
      var test = sched;
      
      var sched = later.parse.text(parserText),
      occurrences = later.schedule(test).next(1000, startDate);

    parserText = occurrences[0] + strDaysCounter;
    
    for (var x = 1; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - endDate > 0) {
            break;
        }
        parserText += ',' + occurrences[x] + strDaysCounter;
    }
    return parserText;
}

//Function to get week number of date
function getWeekNumber(dateString) {
	var date = new Date(dateString);
    date.setHours(0, 0, 0, 0);
    // Thursday in current week decides the year.
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    // January 4 is always in week 1.
    var week1 = new Date(date.getFullYear(), 0, 4);
    // Adjust to Thursday in week 1 and count number of weeks from date to week1.
    var weekNumber = 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getDay() + 6) % 7) / 7);
	return weekNumber;
}

//Function to generate the montly recurrence dates
function createMonthDates (weekdays, isCustom, dayNumber, monthNumber, repeatListValue, daysListValue, startDate, endDate, strDaysCounter) {
    
    var parserText,
        dates;
    
    //If it's not a custom monthly recurrence
    if (!isCustom[0]) {
        
        parserText = 'on the ' + dayNumber + ' day every '+ monthNumber + ' months';
    
    } 
    
    else {
        
        
        if (weekdays.length === 0 && daysListValue.length === 1){
            
            switch(daysListValue[0].toString()) {
                    
                case 'Day':
                    //Query for the 1st, 2nd, 3r, 4th and last day of the month
                    parserText = 'on the '+ repeatListValue[0] + ' day every '+ monthNumber + ' months';
                    break;

                case 'WeekendDay':
                    //Query for the Weekend
                    parserText = 'on Saturday, Sunday every ' + monthNumber + ' months';
                    
                    return filterMonthDates(parserText, startDate, endDate, repeatListValue, 2, strDaysCounter);
                                    
                case 'Weekday':
                    //Query for the Weekdays
                    parserText = 'on Monday, Tuesday, Wednesday, Thursday, Friday every ' + monthNumber + ' months';
                    
                    return filterMonthDates(parserText, startDate, endDate, repeatListValue, 5, strDaysCounter);                    
            }
        }

        if (daysListValue.length === 0){
            
            parserText = 'on ';
            parserText += weekdays[0];
            
            for (var i = 1; i < weekdays.length; i++) {
                
                parserText += ', ' + weekdays[i];           
            }
            
            parserText += ' every ' + monthNumber + ' months';

            return filterMonthDates(parserText, startDate, endDate, repeatListValue, weekdays.length, strDaysCounter);
        }
    }
    
    //If it's not a custom monthly recurrence generates the query directly
    var sched = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000, startDate);
    
    parserText = occurrences[0] + strDaysCounter;
    
    for (var x = 1; x < occurrences.length; x ++) {
        
        if (parseDate(occurrences[x]) - endDate >= 0){
            return parserText;
        }
        
        parserText += ',' + occurrences[x] + strDaysCounter;
    }
    
    return '';
}

function filterMonthDates(parserText, startDate, endDate, repeatListValue, weekdays, strDaysCounter) {
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000, startDate),
        datesArray  = [],
        counter     = 0,
        monthYear   = concatDates(occurrences[0]),
        finalArray  = '';
        
    datesArray[counter] = [];    
    //Creates an array of arrays with all the dates
    for (var i = 0; i < occurrences.length; i ++) {
        
        if (parseDate(occurrences[i]) - endDate >= 0){break;}
        
        if (monthYear === concatDates(occurrences[i])) {
            datesArray[counter].push(occurrences[i]);
        }

        else {
            counter++;
            datesArray[counter] = []; 
            monthYear = concatDates(occurrences[i]);
            datesArray[counter].push(occurrences[i]);
        }
    }
    
    //Gets the value needed
    for (var x = 0; x < datesArray.length; x ++) {
        
        if (datesArray[x].length <= weekdays) {
            
            finalArray += ',' + datesArray[x][0] + strDaysCounter;
            
            for (var z = 1; z < datesArray[x].length; z++) {
                
                finalArray += ',' + datesArray[x][z] + strDaysCounter;
            }
        }
        
        else {
            var repeatNumber = 0;
            //Case Frist is === to 0 case Last special value
            switch(repeatListValue.toString()) {

                case '2':
                    repeatNumber = 1;
                    break;

                case '3':
                    repeatNumber = 2;    
                    break;  
                
                case '4':
                    repeatNumber = 3;    
                    break; 
            }
            if (repeatListValue.toString() === 'Last') {
                finalArray += ',' + datesArray[x][datesArray[x].length - weekdays] + strDaysCounter;  
            } 
            else {
                finalArray += ',' + datesArray[x][weekdays * repeatNumber] + strDaysCounter;   
            }

            for (var z = 1; z < weekdays; z++) {
                
                if (repeatListValue.toString() === 'Last') {
                    
                    finalArray += ',' + datesArray[x][z + datesArray[x].length - weekdays] + strDaysCounter;  
                }
                else {
                    
                    finalArray += ',' + datesArray[x][z + (weekdays * repeatNumber)] + strDaysCounter;  
                }
            }
        }
    }

    return finalArray;
}

//Helper function to parse the dates
function parseDate(str) {

    var day         = str.getDate(),
        monthIndex  = str.getMonth(),
        year        = str.getFullYear();
    
    return new Date(year, monthIndex, day);
}

//Helper function to concat month and year
function concatDates(date){
    return date.getMonth().toString().concat(date.getFullYear().toString());
}
//Function to validate the datepickers
function createDates(dlg) {
    var datesPanel          = dlg.getField('./dateAndTime'),
        datesSubpanels      = datesPanel.findByType('nhm-multi-field-panel'),
        datesRecurrence     = dlg.findByType('textfield')[0],
        allDayRecurrence    = dlg.findByType('textfield')[1],
        timesRecurrence     = dlg.findByType('textfield')[2],
        durationsRecurrence = dlg.findByType('textfield')[3],
        eventPagePath       = dlg.findByType('textfield')[4],
        jsonString          = '',
        mainDates           = [],
        allDays             = [],
        timesArray          = [],
        durationsArray      = [],
        daysCounter         = 0,
        EventDates          = '',
        isRecurring         = true;
    
    datesRecurrence.setValue('');
    timesRecurrence.setValue('');
    //Sets the page path
    eventPagePath.setValue(CQ.WCM.getPagePath());
    
    for (var m=0;m<datesSubpanels.length;m++){
        var subpanel = datesSubpanels[m];
        if(subpanel.name === './dateAndTime'){

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
                                mainDates.push(dates[j].getValue());
                                if (daysCounter > 0) {
                                    EventDates += ',';
                                }
                                EventDates += mainDates[daysCounter] + daysCounter;
                                daysCounter++;
                                isRecurring = false;
                            }
                        }
                    }
                }
            }

            //Populates the durations array
            for (var l = 0; l < durations.length; l++) {
                if (durations[l].fieldLabel === 'Duration') {
                    durationsArray.push(durations[l].getValue());
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

                                EventDates += createDayAndWeekDates(weekdays, null, startDateValue, endDateValue, strDaysCounter);

                                break;

                            case 'weekly':

                                var weekRepeat  = multi[i].findByType('numberfield')[0],
                                    numberfield = weekRepeat.getValue();

                                EventDates += createDayAndWeekDates(weekdays, numberfield, startDateValue, endDateValue, strDaysCounter);

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
    
    allDayRecurrence.setValue(JSON.stringify(allDays));
    durationsRecurrence.setValue(JSON.stringify(durationsArray));
    timesRecurrence.setValue(JSON.stringify(timesArray));
    
    var aggregatedDates;
    if(isRecurring) {
        //Replace needed to remove empty ,,
        aggregatedDates = removeConflictDates(EventDates.replace(/,,/g,',').split(','));
    } else {
        aggregatedDates = [EventDates];
    }
    datesRecurrence.setValue(aggregatedDates);
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

//Function to remove the timestamp and set all the tomes to 00:00:00
function resetTimeStamp(dates) {
    var datesString = [];
    for (var i = 0; i < dates.length; i++) {
        var dateArrayCompared = dates[i].split(' ');
        datesString.push(dateArrayCompared[0] + ' ' + dateArrayCompared[1] + ' ' + dateArrayCompared[2] + ' ' + dateArrayCompared[3] + ' 00:00:00 ' + dateArrayCompared[5] + ' ' + dates[i].match(/\((.*)\)/)[0] + dates[i].slice(-1));
    }
    return datesString;
}

//Function to generate Daily and Weekly dates
function createDayAndWeekDates (weekdays, numberfield, startDate, endDate, strDaysCounter) {
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
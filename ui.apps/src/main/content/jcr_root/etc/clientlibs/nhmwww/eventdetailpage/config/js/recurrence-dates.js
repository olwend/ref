//Function to validate the datepickers
function createDates(dlg) {
    var multi               = dlg.findByType('multifield'),
        datesRecurrence     = dlg.findByType('textfield')[0],
        allDayRecurrence    = dlg.findByType('textfield')[1],
        timesRecurrence     = dlg.findByType('textfield')[2],
        eventPagePath       = dlg.findByType('textfield')[3],
        jsonString          = "",
        mainDates           = [],
        allDays             = [],
        timesArray          = [],
        daysCounter         = 0,
        EventDates          = "";
    
    datesRecurrence.setValue("");
    timesRecurrence.setValue("");
    //Sets the page path
    eventPagePath.setValue(CQ.WCM.getPagePath());
    
    for (var i = 0; i < multi.length; i++) {

        //Gets the main days and if it's an All day event
        if (multi[i].title == "Dates") {
            
            var dates        = multi[i].findByType('datetime'),
                allDayFields = multi[i].findByType('selection');
            
            //Populates the main date Array
            for (var j = 0; j < dates.length; j++) {
                if (dates[j].fieldLabel == "Date") {
                    mainDates.push(dates[j].getValue());
                }
            }
            //Populates All Day array
            for (var k = 0; k < allDayFields.length; k++) {
                if (allDayFields[k].fieldLabel == "All Day Event") {
                    if (allDayFields[k].getValue()[0] == true) {
                        allDays.push(true);
                    } else {
                        allDays.push(false);
                    }
                }
            }
            
            jsonString = JSON.stringify(allDays);
            allDayRecurrence.setValue(jsonString);
        }
        
        //Gets the times
        if (multi[i].title == "Add Times") {
                
            var time    = multi[i].findByType('timefield'),
                times   = [];
                
            for (var j = 0; j < time.length; j++) {
                    
                times.push(time[j].getValue());
            }
            timesArray.push(times);
        }
        
        jsonString = JSON.stringify(timesArray);
        timesRecurrence.setValue(jsonString);
        
        //Gets the recurrences
        if (multi[i].title == "Recurrence") {

            var panel       = multi[i].findParentByType("panel"),
                startDate   = multi[i].findByType('datetime')[0];
            
            //Sets the first date
            if (daysCounter == 0){
                EventDates += mainDates[daysCounter] + daysCounter.toString() + ",";
            } else {
                EventDates += "," + mainDates[daysCounter] + daysCounter.toString() + ",";
            }
            
            if (startDate){
                
                var startDateValue  = startDate.getValue(),
                    endDate         = multi[i].findByType('datetime')[1],
                    endDateValue    = endDate.getValue(),
                    recurSelectList = multi[i].findByType('selection')[0],
                    option          = recurSelectList.getValue(),
                    weekDayList     = multi[i].findByType('selection')[3],
                    weekdays        = weekDayList.getValue();                    

                switch(option[0]) {
                        
                    case "daily":
                        
                        EventDates += createDailyDates(weekdays, startDateValue, endDateValue, daysCounter);
                        
                        break;

                    case "weekly":
                        
                        var weekRepeat  = multi[i].findByType('numberfield')[0],
                            numberfield = weekRepeat.getValue();
                        
                        EventDates += createWeekDates(weekdays, numberfield, startDateValue, endDateValue, daysCounter);
                        
                        break;

                    case "monthly":
                        
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
                        
                        EventDates += createMonthDates (weekdays, isCustom, dayNumber, monthNumber, repeatListValue, daysListValue, startDateValue, endDateValue, daysCounter);
                        
                        break;        
                }   
            }
            daysCounter++;
        }     
    }
    datesRecurrence.setValue(EventDates);
};

//Function to generate the daily recurrence dates
function createDailyDates (weekdays, startDate, endDate, daysCounter) {

    var parserText = "on ";
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ", " + weekdays[i];           
    } 
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));

    parserText = occurrences[0] + daysCounter.toString();
    
    for (var x = 1; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0) {
            return parserText;
        }
        parserText += "," + occurrences[x] + daysCounter.toString();
    }
    return parserText;
};

//Function to generate the weekly recurrence dates
function createWeekDates (weekdays, numberfield, startDate, endDate, daysCounter) {
    
    var parserText = "on ";
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ", " + weekdays[i];           
    }
    
    parserText += " every " + numberfield + " weeks";
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));

    parserText = occurrences[0] + daysCounter.toString();
    
    for (var x = 1; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0) {
            return parserText;
        }
        parserText += "," + occurrences[x] + daysCounter.toString();
    }
    return parserText;
};

//Function to generate the montly recurrence dates
function createMonthDates (weekdays, isCustom, dayNumber, monthNumber, repeatListValue, daysListValue, startDate, endDate, daysCounter) {
    
    var parserText,
        dates;
    
    //If it's not a custom monthly recurrence
    if (!isCustom[0]) {
        
        parserText = 'on the ' + dayNumber + ' day every '+ monthNumber + ' months';
    
    } 
    
    else {
        
        
        if (weekdays.length == 0 && daysListValue.length == 1){
            
            switch(daysListValue[0].toString()) {
                    
                case "Day":
                    //Query for the 1st, 2nd, 3r, 4th and last day of the month
                    parserText = 'on the '+ repeatListValue[0] + ' day every '+ monthNumber + ' months';
                    break;

                case "WeekendDay":
                    //Query for the Weekend
                    parserText = "on Saturday, Sunday every " + monthNumber + " months";
                    
                    return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, 2, daysCounter);
                                    
                case "Weekday":
                    //Query for the Weekdays
                    parserText = "on Monday, Tuesday, Wednesday, Thursday, Friday every " + monthNumber + " months";
                    
                    return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, 5, daysCounter);                    
            }
        }
        
        if (daysListValue.length == 0){
            
            parserText = "on ";
            parserText += weekdays[0];
            
            for (var i = 1; i < weekdays.length; i++) {
                
                parserText += ", " + weekdays[i];           
            }
            
            parserText += " every " + monthNumber + " months";

            return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, weekdays.length, daysCounter);
        }
    }
    
    //If it's not a custom monthly recurrence generates the query directly
    var sched = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));
    
    parserText = occurrences[0] + daysCounter.toString();
    
    for (var x = 1; x < occurrences.length; x ++) {
        
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0){
            return parserText;
        }
        
        parserText += "," + occurrences[x] + daysCounter.toString();
    }
    
    return "";
};

function filterMonthDates(parserText, startDate, endDate, repeatListValue, weekdays, daysCounter) {
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,startDate),
        datesArray  = [],
        counter     = 0,
        monthYear   = concatDates(occurrences[0]),
        finalArray  = "";
        
    datesArray[counter] = [];    
    //Creates an array of arrays with all the dates
    for (var i = 0; i < occurrences.length; i ++) {
        
        if (parseDate(occurrences[i]) - endDate >= 0){break;}
        
        if (monthYear == concatDates(occurrences[i])) {
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
            
            finalArray += "," + datesArray[x][0] + daysCounter.toString();
            
            for (var z = 1; z < datesArray[x].length; z++) {
                
                finalArray += "," + datesArray[x][z] + daysCounter.toString();
            }
        }
        
        else {
            var repeatNumber = 0;
            //Case Frist is == to 0 case Last special value
            switch(repeatListValue.toString()) {

                case "2":
                    repeatNumber = 1;
                    break;

                case "3":
                    repeatNumber = 2;    
                    break;  
                
                case "4":
                    repeatNumber = 3;    
                    break; 
                                
            
            }
            if (repeatListValue.toString() == "Last") {
                finalArray += "," + datesArray[x][datesArray[x].length - weekdays] + daysCounter.toString();  
            } 
            else {
                finalArray += "," + datesArray[x][weekdays * repeatNumber] + daysCounter.toString();   
            }
            
            for (var z = 1; z < weekdays; z++) {
                
                if (repeatListValue.toString() == "Last") {
                    
                    finalArray += "," + datesArray[x][z + datesArray[x].length - weekdays] + daysCounter.toString();  
                }
                else {
                    
                    finalArray += "," + datesArray[x][z + (weekdays * repeatNumber)] + daysCounter.toString();  
                }
            }
        }
    }
    
    return finalArray;
};

//Helper function to parse the dates
function parseDate(str) {
    
    var day         = str.getDate(),
        monthIndex  = str.getMonth(),
        year        = str.getFullYear();
    
    return new Date(year, monthIndex, day);
};

//Helper function to concat month and year
function concatDates(date){
    return date.getMonth().toString().concat(date.getFullYear().toString());
}
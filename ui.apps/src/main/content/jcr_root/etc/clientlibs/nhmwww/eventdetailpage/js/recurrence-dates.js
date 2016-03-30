//Function to validate the datepickers
function createDates(dlg) {
    var multi           = dlg.findByType('multifield'),
        datesRecurrence = dlg.findByType('textfield')[0],
        eventPagePath   = dlg.findByType('textfield')[1],
        EventDates      = "";
    
    datesRecurrence.setValue("");
    //Sets the page path
    eventPagePath.setValue(CQ.WCM.getPagePath());
    
    for (var i = 0; i < multi.length; i++) {
        if (multi[i].title == "Dates") {
                var date = multi[i].findByType('datetime')[0];
                EventDates += date.getValue();
        }
        
        if (multi[i].title == "Recurrence") {
            
            var panel       = multi[i].findParentByType("panel"),
                startDate   = multi[i].findByType('datetime')[0];
            
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
                        
                        EventDates += createDailyDates(weekdays, startDateValue, endDateValue);
                        
                        break;

                    case "weekly":
                        
                        var weekRepeat  = multi[i].findByType('numberfield')[0],
                            numberfield = weekRepeat.getValue();
                        
                        EventDates += createWeekDates(weekdays, numberfield, startDateValue, endDateValue);
                        
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
                        
                        EventDates += createMonthDates (weekdays, isCustom, dayNumber, monthNumber, repeatListValue, daysListValue, startDateValue, endDateValue);
                        
                        break;        
                }   
            }
        }     
    }
    datesRecurrence.setValue(EventDates);
};

//Function to generate the daily recurrence dates
function createDailyDates (weekdays,startDate,endDate) {
    
    var parserText = "on ";
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ", " + weekdays[i];           
    } 
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));

    parserText = "";
    
    for (var x = 0; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0){
            return parserText;
        }
        parserText += "," + occurrences[x];
    }
    return parserText;
};

//Function to generate the weekly recurrence dates
function createWeekDates (weekdays,numberfield,startDate,endDate) {
    
    var parserText = "on ";
    
    parserText += weekdays[0];
    
    for (var i = 1; i < weekdays.length; i++) {
        parserText += ", " + weekdays[i];           
    }
    
    parserText += " every " + numberfield + " weeks";
    
    var sched       = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));

    parserText = "";
    
    for (var x = 0; x < occurrences.length; x ++) {
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0){
            return parserText;
        }
        parserText += "," + occurrences[x];
    }
    return parserText;
};

//Function to generate the montly recurrence dates
function createMonthDates (weekdays,isCustom,dayNumber,monthNumber,repeatListValue,daysListValue,startDate,endDate) {
    
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
                    
                    return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, 2);
                                    
                case "Weekday":
                    //Query for the Weekdays
                    parserText = "on Monday, Tuesday, Wednesday, Thursday, Friday every " + monthNumber + " months";
                    
                    return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, 5);                    
            }
        }
        
        if (daysListValue.length == 0){
            
            parserText = "on ";
            parserText += weekdays[0];
            
            for (var i = 1; i < weekdays.length; i++) {
                
                parserText += ", " + weekdays[i];           
            }
            
            parserText += " every " + monthNumber + " months";

            return filterMonthDates(parserText, parseDate(startDate), parseDate(endDate), repeatListValue, weekdays.length);
        }
    }
    
    //If it's not a custom monthly recurrence generates the query directly
    var sched = later.parse.text(parserText),
        occurrences = later.schedule(sched).next(1000,parseDate(startDate));
    
    parserText = "";
    
    for (var x = 0; x < occurrences.length; x ++) {
        
        if (parseDate(occurrences[x]) - parseDate(endDate) >= 0){
            
            return parserText;
        }
        
        parserText += "," + occurrences[x];
    }
    
    return "";
};

function filterMonthDates(parserText, startDate, endDate, repeatListValue, weekdays) {
    
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
            
            finalArray += "," + datesArray[x][0];
            
            for (var z = 1; z < datesArray[x].length; z++) {
                
                finalArray += "," + datesArray[x][z];
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
                finalArray += "," + datesArray[x][datesArray[x].length - weekdays];  
            } 
            else {
                finalArray += "," + datesArray[x][weekdays * repeatNumber];   
            }
            
            for (var z = 1; z < weekdays; z++) {
                
                if (repeatListValue.toString() == "Last") {
                    
                    finalArray += "," + datesArray[x][z + datesArray[x].length - weekdays];  
                }
                else {
                    
                    finalArray += "," + datesArray[x][z + (weekdays * repeatNumber)];  
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
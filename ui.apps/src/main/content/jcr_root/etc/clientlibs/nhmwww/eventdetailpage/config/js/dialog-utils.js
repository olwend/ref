//Displays or Hides the selected Event Config
function displayEventConfig(field,value) {
    //Find the parent tabpanel container
    var tabs = field.findParentByType('tabpanel');
    var tabNames = ['School','Science'];
    //Compares the value get and the Array to display/hide the Tabs
    for (var i = 0; i < tabNames.length; i++){
        var tabName = tabNames[i];
        tabs.hideTabStripItem(tabName); 
        if (tabName === value) {
            tabs.unhideTabStripItem(tabName);
        }
    }
}

function checkDates(dialog) {
    var submit = true;
    var datesPanel = dialog.getField('./dateAndTime');
    var datesPanelValue = datesPanel.getValue();
    if (datesPanelValue.length === 0) {
        submit = false;
        datesPanel.markInvalid('Dates can not be empty');
    }else {
        var subpanels = datesPanel.findByType('nhm-multi-field-panel');
        for(var j=0;j<subpanels.length && submit;j++){
            var subpanel = subpanels[j];
            if(subpanel.name === './dateAndTime'){
                var selectionFields = subpanel.findByType('selection');
                //check is recurring
                var isRecurringField = selectionFields[0].getValue();
                if(isRecurringField.length && isRecurringField[0]) {
                    var recurrencesPanel = subpanel.findByType('multifield')[1];
                    if(recurrencesPanel.getValue().length){
                        var recurSelections = recurrencesPanel.findByType('selection');
                        var recurNumFields = recurrencesPanel.findByType('numberfield');
                        var frequencyType = recurSelections[0].getValue();
                        if(frequencyType.length){
                            switch (frequencyType[0]){
                                case 'daily':
                                    submit = checkDailyFrequency(recurSelections);
                                    break;
                                case 'weekly':
                                    submit = checkWeeklyFrequency(recurSelections, recurNumFields);
                                    break;
                                case 'monthly':
                                    submit = checkMonthlyFrequency(recurNumFields);
                                    break;
                            }
                            if(!submit){
                                break;
                            }
                        }
                    }else{
                        submit = false;
                        recurrencesPanel.markInvalid('Recurrences can not be empty');
                        break;
                    }
                }
                
                for(var i=1;i<selectionFields.length;i++){
                    var selectionField = selectionFields[i];
                    if(selectionField.dName === 'allDay'){
                        var allDay = selectionField.getValue();
                        if(!allDay.length || !allDay[0]){
                            var datePanel = selectionField.findParentByType('nhm-multi-field-panel');
                            var timesFields = subpanel.findByType('timefield');
                            if(timesFields.length) {
                                var durationPanel = subpanel.findByType('numberfield')[0];
                                if(!durationPanel.getValue()){
                                    submit = false;
                                    durationPanel.markInvalid('Duration can not be empty');
                                    break;
                                }
                            } else {
                                submit = false;
                                var timesPanel = subpanel.findByType('multifield')[0];
                                timesPanel.markInvalid('Times can not be empty');
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    return submit;
}

function isDailyFrequencyValid(recurSelections){
    var isValid = true;
    for(var i=1;i<recurSelections.length;i++){
        var recurSelection = recurSelections[i];
        if(recurSelection.dName === 'weekdaysList'){
            var weekdaysListValue = recurSelection.getValue();
            if(!weekdaysListValue.length){
                isValid = false;
                recurSelection.markInvalid('Day(s) can not be empty');
            }
            break;
        }
    }
    return isValid;
}

function checkDailyFrequency(recurSelections) {
    return isDailyFrequencyValid(recurSelections);
}

function checkWeeklyFrequency(recurSelections, recurNumFields) {
    var isValid = isDailyFrequencyValid(recurSelections);
    if(isValid){
        for(var i=0;i<recurNumFields.length;i++){
            var recurNumField = recurNumFields[i];
            if(recurNumField.dName === 'weekRepeat'){
                if(!recurNumField.getValue()){
                    isValid = false;
                    recurNumField.markInvalid('Weekly recurrence frequency can not be empty');
                }
                break;
            }
        }
    }
    return isValid;
}

function checkMonthlyFrequency(recurNumFields) {
    var isValid = true;
    if(isValid){
        for(var i=0;i<recurNumFields.length;i++){
            var recurNumField = recurNumFields[i];
            if(recurNumField.dName === 'dayRepeat'){
                if(!recurNumField.getValue()){
                    isValid = false;
                    recurNumField.markInvalid('Day of month can not be empty');
                    break;
                }
            }
            if(recurNumField.dName === 'monthRepeat'){
                if(!recurNumField.getValue()){
                    isValid = false;
                    recurNumField.markInvalid('Monthly recurrence frequency can not be empty');
                    break;
                }
            }
        }
    }
    return isValid;
}

function getToday() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yy = today.getFullYear().toString().substr(2,2);

    if(dd<10) {
        dd='0'+dd;
    } 

    if(mm<10) {
        mm='0'+mm;
    }

    return mm+'-'+dd+'-'+yy;
}

function changeEventRecurType(isRecurring, dateForm, recurForm) {
    var dateFormValue = dateForm.getValue();
    if(isRecurring && !dateFormValue){
        dateForm.setValue(getToday());
    }
    dateForm.getEl().up('.x-form-item').child('.x-form-field-wrap').removeClass('x-hide-display');
    if (isRecurring) {
        recurForm.show();
    } else {
        recurForm.hide();
    }
    dateForm.getEl().up('.x-form-item').setDisplayed(!isRecurring);
}

//Displays or Hides the ui elements depending on whether the event is single or recursive
function eventRecurTypeSelected(field,value,isChecked) {
    //Find the parent panel container
    var panel = field.findParentByType('panel');
    //Gets the Times Multifield
    var date = panel.findByType('datetime')[0];
    var times = panel.findByType('multifield')[0];
    var recurrence = panel.findByType('multifield')[1];
    var duration = panel.findByType('numberfield')[0];
    var allDay = panel.findByType('selection')[1];
    allDay.show();
    toggleTimesVisibility(allDay, times);
    toggleDurationVisibility(panel, duration);
    changeEventRecurType(isChecked, date, recurrence);
}

function toggleDurationVisibility(container, durationForm) {
    var times = container.findByType('multifield')[0];
    var timesItems = container.findByType('timefield');
    if (!times.hidden && timesItems.length) {
        if(durationForm.hidden){
            durationForm.show();
        }
    } else {
        if(!durationForm.hidden){
            durationForm.hide();
        }
    }
}

function toggleTimesVisibility(allDayForm, timesForm) {
    var allDayValue = allDayForm.getValue();
    if(allDayForm.hidden || allDayValue.length > 0 && allDayValue[0]){
        timesForm.hide();
    }else {
        timesForm.show();
        timesForm.doLayout();
        timesForm.getEl().child('.times-container-time').setWidth('auto');
    }
}

function initEventDateLayout(component) {
    var value = component.getValue();
    var panel = component.findParentByType('panel');
    var date = panel.findByType('datetime')[0];
    var times = panel.findByType('multifield')[0];
    var recurrence = panel.findByType('multifield')[1];
    var duration = panel.findByType('numberfield')[0];
    var allDay = panel.findByType('selection')[1];
    toggleTimesVisibility(allDay, times);
    toggleDurationVisibility(panel, duration);
    changeEventRecurType(value[0], date, recurrence);
}

//Displays or Hides the Time selector for an Event Date
function allDaySelected(field,value,isChecked) {
    //Find the parent panel container
    var panel = field.findParentByType('panel');
    //Gets the Times Multifield
    var times = panel.findByType('multifield')[0];
    var duration = panel.findByType('numberfield')[0];
    //Hide or show component based on checked value
    if(isChecked) {
        times.hide();
        duration.hide();
    } else {
        times.show();
        times.doLayout();
        times.getEl().child('.times-container-time').setWidth('auto');
        toggleDurationVisibility(panel, duration);
    }
}

function updateDurationVisibility(container,component) {
    var panel = container.findParentByType('nhm-multi-field-panel');
    var duration = panel.findByType('numberfield')[0];
    toggleDurationVisibility(panel, duration);
}

//Inits the recur checkbox
function initRecurValues (component, value, isChecked) {
    var panel = component.findParentByType('panel');
    var extraMonthParam = panel.findByType('selection')[1];
    var repeatList = panel.findByType('selection')[2];
    var weekdaysList = panel.findByType('selection')[3];
    var daysList = panel.findByType('selection')[4];
    var repeatWeekly = panel.findByType('numberfield')[0];
    var repeatDay = panel.findByType('numberfield')[1];
    var repeatMonth = panel.findByType('numberfield')[2];
    var componentValues = component.getValue();
    
    if (component && componentValues) {
        if (value && isChecked) {
            var newValue = value;
            for (var i = 0; i < componentValues.length; i++) {
                if (componentValues[i] === newValue) {
                    extraMonthParam.hide();
                    repeatList.hide();
                    weekdaysList.hide();
                    daysList.hide();
                    repeatWeekly.hide();
                    repeatDay.hide();
                    repeatMonth.hide();
                    
                    component.setValue(newValue);                    
                    switch(newValue) {
                        case 'daily':
                            weekdaysList.show();
                            break;
                            
                        case 'weekly':
                            weekdaysList.show();
                            repeatWeekly.show();
                            break;
                            
                        case 'monthly':
                            extraMonthParam.show();
                            repeatMonth.show();
                            repeatDay.show();
                            break;        
                    }
                    return;
                }
            }
        } 
        if (typeof isChecked !== 'undefined' && !isChecked) {
            extraMonthParam.hide();
            repeatList.hide();
            weekdaysList.hide();
            daysList.hide();
            repeatWeekly.hide();
            repeatDay.hide();
            repeatMonth.hide();
        }
    }
}

//Displays/Hides the extra monthly parameters
function initExtraValues (component, value, isChecked) {
    var panel = component.findParentByType('panel');
    var repeatList = panel.findByType('selection')[2];
    var weekdaysList = panel.findByType('selection')[3];
    var daysList = panel.findByType('selection')[4];
    var repeatDay = panel.findByType('numberfield')[1];
    var repeatMonth = panel.findByType('numberfield')[2];
    
    if (typeof isChecked !== 'undefined') {
        isChecked ? repeatList.show() && weekdaysList.show() && daysList.show() && repeatDay.hide() : repeatList.hide() && weekdaysList.hide() && daysList.hide() && repeatDay.show();
    } else {
        component.reset();
    }
}

function initRepeatValues (component, value, isChecked) {
    var panel = component.findParentByType('panel');
    var repeatList = panel.findByType('selection')[2];
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value && isChecked) {
            var newValue = value;
            for (var i = 0; i < componentValues.length; i++) {
                if (componentValues[i] === newValue) {
                    component.setValue(newValue);
                    break;        
                }
            }
        } 
    }
}

function resetDaysValues (component, value, isChecked) {
    var panel = component.findParentByType('panel');
    var weekdaysList = panel.findByType('selection')[3];
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value && isChecked) {
            weekdaysList.reset();
        } 
    }
}

function resetWeekDaysValues (component, value, isChecked) {
    var panel = component.findParentByType('panel');
    var daysList = panel.findByType('selection')[4];
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value && isChecked) {
            daysList.reset();
        } 
    }
}

//Function to validate the datepickers
function checkTime (field, newValue, oldValue) {
    var d = new Date();
    var today = new Date(d.getFullYear(), d.getMonth(), d.getDate());
    var newDate = new Date(newValue.getFullYear(), newValue.getMonth(), newValue.getDate());

    if (newDate < today) {
        field.setValue(oldValue);
        alert('Please select a valid date');
    }
}

//Function to set the prices as required or not
function setValidation (field, newValue, oldValue) {
    var panel = field.findParentByType('panel');
    var prices = panel.findByType('textfield');
    var isEmpty = true;
    if (newValue) {
        for (var i = 0; i < prices.length - 1; i++) {
            prices[i].allowBlank = true;
            prices[i].clearInvalid();
        }
    } else {
        field.reset();
        
        isEmpty = checkEmptyPrice(prices);

        if (isEmpty) {
            for (var i = 0; i < prices.length - 1; i++) {
                prices[i].allowBlank = false;
                prices[i].clearInvalid();
            }
        }
    }
}

function checkInitPrices (field, dataRecord, path) {
    var panel = field.findParentByType('panel');
    var prices = panel.findByType('textfield');
    var isEmpty = true;
      
    isEmpty = checkEmptyPrice(prices);
    
    if (!isEmpty) {
        for (var i = 0; i < prices.length - 1; i++) {
            prices[i].allowBlank = true;
            prices[i].clearInvalid();
        }
    }
}

function checkEmptyPrice (prices) {
    var isEmpty = true;
    
    for (var i = 0; i < prices.length - 1; i++) {
        if (prices[i].getValue() !== '') {
            isEmpty = false;
            break;
        }
    }
    
    return isEmpty;
}
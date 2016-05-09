//Displays or Hides the selected Event Config
function displayEventConfig(field,value) {
    //Find the parent tabpanel container
    var tabs = field.findParentByType('tabpanel');
    var tabNames = ["School","Science"];
    //Compares the value get and the Array to display/hide the Tabs
    for (var i = 0; i < tabNames.length; i++){
        var tabName = tabNames[i];
        tabs.hideTabStripItem(tabName); 
        if (tabName == value) {
            tabs.unhideTabStripItem(tabName);
        }
    }
}

//Displays or Hides the Time selector for an Event Date
function allDaySelected(field,value,isChecked) {
    //Find the parent panel container
    var panel = field.findParentByType("panel");
    //Gets the Times Multifield
    var times = panel.findByType('multifield')[0];
    //Hide or show component based on checked value
    isChecked ? times.hide() : times.show();
}

//Inits the recur checkbox
function initRecurValues (component, value, isChecked) {
    var panel = component.findParentByType("panel");
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
                if (componentValues[i] == newValue) {
                    extraMonthParam.hide();
                    repeatList.hide();
                    weekdaysList.hide();
                    daysList.hide();
                    repeatWeekly.hide();
                    repeatDay.hide();
                    repeatMonth.hide();
                    
                    component.setValue(newValue);                    
                    switch(newValue) {
                        case "daily":
                            weekdaysList.show();
                            break;
                            
                        case "weekly":
                            weekdaysList.show();
                            repeatWeekly.show();
                            break;
                            
                        case "monthly":
                            extraMonthParam.show();
                            repeatMonth.show();
                            repeatDay.show();
                            break;        
                    }
                    return;
                }
            }
        } 
        if (typeof isChecked != 'undefined' && !isChecked) {
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
    var panel = component.findParentByType("panel");
    var repeatList = panel.findByType('selection')[2];
    var weekdaysList = panel.findByType('selection')[3];
    var daysList = panel.findByType('selection')[4];
    var repeatDay = panel.findByType('numberfield')[1];
    var repeatMonth = panel.findByType('numberfield')[2];
    
    if (typeof isChecked != 'undefined') {
        isChecked ? repeatList.show() && weekdaysList.show() && daysList.show() && repeatDay.hide() : repeatList.hide() && weekdaysList.hide() && daysList.hide() && repeatDay.show();
    } else {
        component.reset();
    }
}

function initRepeatValues (component, value, isChecked) {
    var panel = component.findParentByType("panel");
    var repeatList = panel.findByType('selection')[2];
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value && isChecked) {
            var newValue = value;
            for (var i = 0; i < componentValues.length; i++) {
                if (componentValues[i] == newValue) {
                    component.setValue(newValue);
                    break;        
                }
            }
        } 
    }
}

function resetDaysValues (component, value, isChecked) {
    var panel = component.findParentByType("panel");
    var weekdaysList = panel.findByType('selection')[3];
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value && isChecked) {
            weekdaysList.reset();
        } 
    }
}

function resetWeekDaysValues (component, value, isChecked) {
    var panel = component.findParentByType("panel");
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
        alert("Please select a valid date");
    }
}

//Function to set the prices as required or not
function setValidation (field, newValue, oldValue) {
    var panel = field.findParentByType("panel");
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
    var panel = field.findParentByType("panel");
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
        if (prices[i].getValue() != "") {
            isEmpty = false;
            break;
        }
    }
    
    return isEmpty;
}
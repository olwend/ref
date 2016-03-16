//Displays or Hides the selected Event Config
function displayEventConfig(field,value) {
    //Find the parent tabpanel container
    var tabs = field.findParentByType('tabpanel');
    var tabNames = ["exhibitionTab","schoolTab","scienceTab","tringTab","visitorTab"];
    //Compares the value get and the Array to display/hide the Tabs
    for (var i = 0; i < tabNames.length; i++){
        tabs.hideTabStripItem(tabNames[i]); 
        if (tabNames[i] == value) {
            tabs.unhideTabStripItem(tabNames[i]);
        }
    }
};

//Displays or Hides the Time selector for an Event Date
function allDaySelected(field,value,isChecked) {
    //Find the parent panel container
    var panel = field.findParentByType("panel");
    //Gets the Times Multifield
    var times = panel.findByType('multifield')[0];
    //Hide or show component based on checked value
    isChecked ? times.hide() : times.show();
};

function displayRecur(field,value,isChecked) {
    var panel = field.findParentByType("panel");
    var recurSelectList = panel.findByType('selection')[2];
    var weekdaysList = panel.findByType('selection')[3];

    isChecked ? recurSelectList.show() : recurSelectList.hide() && weekdaysList.hide();
};

//Inits the recur checkbox
function initRecurValues (component, value) {
    var componentValues = component.getValue();
    if (component && componentValues) {
        if (value) {
            var newValue = value;
            for (var i = 0; i < componentValues.length; i++) {
                if (componentValues[i] == newValue) {
                    component.setValue(newValue);
                    setRecurOften(component, newValue);
                    break;
                }
            }
        }
    }
};

//Displays the options according to the recurrence selected
function setRecurOften(component, newValue) {
    var panel = component.findParentByType("panel");
    var weekdaysList = panel.findByType('selection')[3];
    if (newValue) {
        switch(newValue) {
            case "custom":
                weekdaysList.show();
                break;
            case "daily":
                weekdaysList.hide();
                break;
            case "weekly":
                weekdaysList.show();
                break;
            case "monthly":
                weekdaysList.hide();
                break;
        }
    }
};
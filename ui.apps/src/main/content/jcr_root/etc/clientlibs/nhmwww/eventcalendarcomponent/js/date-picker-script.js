var NHMDatePicker = NHMDatePicker || {};
if (typeof NHMDatePicker.Profile === 'undefined') {
    NHMDatePicker.Profile = function () {
        this.CONST = {
            DATE_FORMAT:    'D d/mm/yy',
            ORIENTATION:    'top',
            DAY_NAMES:      ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            MIN_DATE:       0,
            MAX_DATE:       '+1y'
        };
    };   
}
$(document).ready(function () {
    //Sets the profiles
    var datePickerProfile = new NHMDatePicker.Profile();
    var calendarProfile = new NHMCalendar.Profile();
    //First datepicker
    $('#dateFrom').datepicker({
        minDate: datePickerProfile.CONST.MIN_DATE,
        maxDate: datePickerProfile.CONST.MAX_DATE,
        orientation: datePickerProfile.CONST.ORIENTATION,
        dateFormat: datePickerProfile.CONST.DATE_FORMAT,
        dayNamesMin: datePickerProfile.CONST.DAY_NAMES,
        beforeShow: function (input, inst) {
            inst.inline = false;
            setTimeout(function () {
                //Needed to set the datepicker width
                addRule(document.styleSheets[0], "#ui-datepicker-div", "width: " + $(".helper").width() + "px");
                //Needed to place the arrow properly
                addRule(document.styleSheets[0], ".ui-datepicker:before", "left: 25%");
                addRule(document.styleSheets[0], ".ui-datepicker:after", "left: 25%;");
            }, 10);
        },
        //Needed to set the "To" date 
        onSelect: function (dateText, inst) {
            $("#dateTo").datepicker("option", "minDate", $("#dateFrom").datepicker("getDate"));
            //Call to the search function
            calendarProfile.doSearch();
        }
    });
    //Second datepicker
    $('#dateTo').datepicker({
        minDate: datePickerProfile.CONST.MIN_DATE,
        maxDate: datePickerProfile.CONST.MAX_DATE,
        orientation: datePickerProfile.CONST.ORIENTATION,
        dateFormat: datePickerProfile.CONST.DATE_FORMAT,
        dayNamesMin: datePickerProfile.CONST.DAY_NAMES,
        //Needed to display both datepickers in the same place
        beforeShow: function (input, inst) {
            var cal = inst.dpDiv;
            //Gets the left
            var left = $('#dateFrom').offset().left;
            setTimeout(function () {
                cal.css({
                    'left': left
                });
                addRule(document.styleSheets[0], "#ui-datepicker-div", "width: " + $(".helper").width() + "px");
                addRule(document.styleSheets[0], ".ui-datepicker:before", "left: 75%");
                addRule(document.styleSheets[0], ".ui-datepicker:after", "left: 75%;");
            }, 10);
        },
        onSelect: function (dateText, inst) {
           calendarProfile.doSearch();
        }
    });
    //Needed to hide the datepickers
    $('.dp').on('change', function () {
        $('.datepicker').datepicker('hide');
    });
    
    //Helper function to add the rules
    var addRule = function(sheet, selector, styles) {
        if (sheet.insertRule) return sheet.insertRule(selector + " {" + styles + "}", sheet.cssRules.length);
        if (sheet.addRule) return sheet.addRule(selector, styles);
    };
});
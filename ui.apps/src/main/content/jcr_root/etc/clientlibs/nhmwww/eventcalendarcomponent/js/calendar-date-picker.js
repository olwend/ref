var NHMDatePicker = {
    DATE_FORMAT: 'D d/mm/yy',
    ORIENTATION: 'top',
    DAY_NAMES: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    MIN_DATE: 0,
    MAX_DATE: '+1y',
    MONDAY: 1
};

$(document).ready(function () {
    //First datepicker
    $('#dateFrom').datepicker({
        minDate: NHMDatePicker.MIN_DATE,
        maxDate: NHMDatePicker.MAX_DATE,
        orientation: NHMDatePicker.ORIENTATION,
        dateFormat: NHMDatePicker.DATE_FORMAT,
        dayNamesMin: NHMDatePicker.DAY_NAMES,
        firstDay: NHMDatePicker.MONDAY,
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
            NHMCalendar.doSearch(true);
        }
    });
    //Second datepicker
    $('#dateTo').datepicker({
        minDate: NHMDatePicker.MIN_DATE,
        maxDate: NHMDatePicker.MAX_DATE,
        orientation: NHMDatePicker.ORIENTATION,
        dateFormat: NHMDatePicker.DATE_FORMAT,
        dayNamesMin: NHMDatePicker.DAY_NAMES,
        firstDay: NHMDatePicker.MONDAY,
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
            NHMCalendar.doSearch(true);
        }
    });
    //Needed to hide the datepickers
    $('.dp').on('change', function () {
        $('.datepicker').datepicker('hide');
    });

    //Helper function to add the rules
    var addRule = function (sheet, selector, styles) {
        if (sheet.insertRule) return sheet.insertRule(selector + " {" + styles + "}", sheet.cssRules.length);
        if (sheet.addRule) return sheet.addRule(selector, styles);
    };
});
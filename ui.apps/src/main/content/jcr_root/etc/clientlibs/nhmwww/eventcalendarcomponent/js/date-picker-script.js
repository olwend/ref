$(document).ready(function () {
    //First datepicker
    $('#dateFrom').datepicker({
        minDate: 0,
        maxDate: "+1y",
        orientation: "top",
        dateFormat: "D d/mm/yy",
        dayNamesMin: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
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
        },
    });
    //Second datepicker
    $('#dateTo').datepicker({
        minDate: 0,
        maxDate: "+1y",
        orientation: "top",
        dateFormat: "D d/mm/yy",
        dayNamesMin: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
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
    });
    //Needed to hide the datepickers
    $('.dp').on('change', function () {
        $('.datepicker').hide();
    });
    
    //Helper function to add the rules
    var addRule = function(sheet, selector, styles) {
        if (sheet.insertRule) return sheet.insertRule(selector + " {" + styles + "}", sheet.cssRules.length);
        if (sheet.addRule) return sheet.addRule(selector, styles);
    };
});
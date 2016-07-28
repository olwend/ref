var NHMCalendar = new function () {
    var CONST = {
        DATE_BUTTON_SELECTED_CLASS: 'date--button--selected',
        DATE_BUTTON_CLASS: 'small-12 medium-12 large-12 columns date--button',
        THIRTY_DAYS: 'thirtyDays',
        SEVEN_DAYS: 'sevenDays',
        TODAY: 'today',
        DATE_FROM: '#dateFrom',
        SET_DATE: 'setDate',
        MIN_DATE: 'minDate',
        DATE_TO: '#dateTo',
        OPTION: 'option',
        CHANGE: 'change',
        KEYUP: 'keyup',
        CLICK: 'click',
        NONE: 'none'
    };
    
    var inputs = {};

    //Do Search if key pressed is enter
    var getKey = function (event) {
        if (event.which == 13 || event.keyCode == 13) {
            doSearch();
        }
    };

    //Function to reset the calendar
    var resetFilters = function () {
        //Reset the keyword
        inputs.keywordsInput.value = "";

        //Reset the filters
        inputs.filterOne.value = CONST.NONE;
        inputs.filterTwo.value = CONST.NONE;

        //Reset the dates
        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].className = CONST.DATE_BUTTON_CLASS;
        }
        $(CONST.DATE_FROM).datepicker(CONST.SET_DATE, null);
        $(CONST.DATE_TO).datepicker(CONST.SET_DATE, null);
        $(CONST.DATE_TO).datepicker(CONST.OPTION, CONST.MIN_DATE, 0);

        //Call the search function
        doSearch();
    };

    //Function to set the button selected
    var setDateButton = function (event) {
        var buttonEl = event.currentTarget;
        for (var i = 0; i < inputs.dateButtons.length; i++) {
            if (inputs.dateButtons[i].id != buttonEl.id) {
                inputs.dateButtons[i].className = CONST.DATE_BUTTON_CLASS;
            }
        }

        if (!buttonEl.classList.contains(CONST.DATE_BUTTON_SELECTED_CLASS)) {
            buttonEl.className += (" " + CONST.DATE_BUTTON_SELECTED_CLASS);
            //Set the selected dates
            setDatesFromButton(buttonEl.id);
        } else {
            buttonEl.className = CONST.DATE_BUTTON_CLASS;
            $(CONST.DATE_FROM).datepicker(CONST.SET_DATE, null);
            $(CONST.DATE_TO).datepicker(CONST.SET_DATE, null);
        }

        doSearch();
    };

    //Function to set the datepicker values from the buttons
    var setDatesFromButton = function (dates) {
        var today = new Date(),
            toDate = '',
            fromEl = $(CONST.DATE_FROM),
            toEl = $(CONST.DATE_TO);

        fromEl.datepicker(CONST.SET_DATE, today);
        switch (dates) {
            case CONST.TODAY:
                toDate = today;
                toEl.datepicker('option', 'minDate', fromEl.datepicker('getDate'));
                break;
                
            case CONST.SEVEN_DAYS:
                toDate = new Date();
                toDate.setDate(today.getDate() + 6);
                break;

            case CONST.THIRTY_DAYS:
                toDate = new Date();
                toDate.setDate(today.getDate() + 29);
                break;
        }
        toEl.datepicker(CONST.SET_DATE, toDate);
    };
    
    this.init = function () {
        inputs = {
            keywordsInput: document.getElementById("keywordsInput"),
            searchButton: document.getElementsByClassName("search-icon"),
            resetButton: document.getElementsByClassName("reset--filter"),
            dateButtons: document.getElementsByClassName("date--button"),
            filterOne: document.getElementById("filterOne"),
            filterTwo: document.getElementById("filterTwo"),
            dateFrom: document.getElementById("dateFrom"),
            dateTo: document.getElementById("dateTo")
        };
        bindEvents();
    };

    //Sets the listenerts needed
    var bindEvents = function () {
        inputs.keywordsInput.addEventListener(CONST.KEYUP, function (e) {
            getKey(e);
        }, false);
        inputs.searchButton[0].addEventListener(CONST.CLICK, function () {
            doSearch();
        }, false);
        inputs.resetButton[0].addEventListener(CONST.CLICK, function () {
            resetFilters();
        }, false);
        inputs.filterOne.addEventListener(CONST.CHANGE, function () {
            doSearch();
        }, false);
        inputs.filterTwo.addEventListener(CONST.CHANGE, function () {
            doSearch();
        }, false);

        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].addEventListener(CONST.CLICK, function (e) {
                setDateButton(e);
            }, false);
        }
    };
    
    //Function to trigger the search
    var doSearch = function () {
        if (inputs.keywordsInput.value || inputs.filterOne.value != CONST.NONE || inputs.filterTwo.value != CONST.NONE || inputs.dateFrom.value || inputs.dateTo.value) {
            NHMSearchQuery.displaySearchEvents(inputs.keywordsInput.value, inputs.filterOne.value, inputs.filterTwo.value, inputs.dateFrom.value, inputs.dateTo.value);
        } else {
            NHMSearchQuery.displayTodayEvents();
        }
    };
    
    this.resetSelectedButtons = function () {
        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].className = CONST.DATE_BUTTON_CLASS;
        }
    };
    
    this.doSearch = doSearch;
};

$(document).ready(function () {
    NHMCalendar.init();
});
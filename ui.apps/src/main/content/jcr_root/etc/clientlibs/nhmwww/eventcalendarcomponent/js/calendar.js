var searchHistoryArray = [];
var ignoreHistoryChange = false;

var NHMCalendar = new function () {
    var searchNumber = 0;
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
            doSearch(true);
        }
    };

    //Function to reset the calendar
    var resetFilters = function (saveSearch) {
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
//        var save = (typeof )
        doSearch(saveSearch);
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

        doSearch(true);
    };

    //Function to set the datepicker values from the buttons
    var setDatesFromButton = function (dates) {
        var today = new Date(),
            toDate = '',
            fromEl = $(CONST.DATE_FROM),
            toEl = $(CONST.DATE_TO);

        fromEl.datepicker(CONST.SET_DATE, today);
        toEl.datepicker('option', 'minDate', fromEl.datepicker('getDate'));
        
        switch (dates) {
            case CONST.TODAY:
                toDate = today;
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
            doSearch(true);
        }, false);
        inputs.resetButton[0].addEventListener(CONST.CLICK, function () {
            resetFilters(true);
        }, false);
        inputs.filterOne.addEventListener(CONST.CHANGE, function () {
            doSearch(true);
        }, false);
        inputs.filterTwo.addEventListener(CONST.CHANGE, function () {
            doSearch(true);
        }, false);

        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].addEventListener(CONST.CLICK, function (e) {
                setDateButton(e);
            }, false);
        }
    };
    
    var setSearchParameters = function(parameters) {
        // Clean selection of the date shortcut buttons
        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].className = CONST.DATE_BUTTON_CLASS;
        }
        inputs.keywordsInput.value = parameters.keywordsInput;
        inputs.filterOne.value = parameters.filterOne;
        inputs.filterTwo.value = parameters.filterTwo;
        inputs.dateFrom.value = parameters.dateFrom;
        inputs.dateTo.value = parameters.dateTo;
        
        if (parameters.dateButtons.length > 0) {
            $('#' + parameters.dateButtons)[0].className += " " + CONST.DATE_BUTTON_SELECTED_CLASS;
        }
    };
    
    var getSelectedDateButton = function() {
        var selected = "";
        var todayButton = $('#' + CONST.TODAY)[0];
        var sevenDaysButton = $('#' + CONST.SEVEN_DAYS)[0];
        var thirtyDaysButton = $('#' + CONST.THIRTY_DAYS)[0];
        
        if ((typeof todayButton != "undefined") && 
             todayButton.classList.contains(CONST.DATE_BUTTON_SELECTED_CLASS)) {
            selected = CONST.TODAY;
        }
        else if ((typeof sevenDaysButton != "undefined") && 
                  sevenDaysButton.classList.contains(CONST.DATE_BUTTON_SELECTED_CLASS)) {
            selected = CONST.SEVEN_DAYS;
        }
        else if ((typeof thirtyDaysButton != "undefined") && 
                  thirtyDaysButton.classList.contains(CONST.DATE_BUTTON_SELECTED_CLASS)) {
            selected = CONST.THIRTY_DAYS;
        }
        return selected;
    }
    
    //Function to trigger the search
    var doSearch = function (saveSearchHistory) {
        $("#events-calendar-loading").show();
        if (saveSearchHistory) {
            ignoreHistoryChange = true;
            window.location = "#search_" + searchNumber;
            searchHistoryArray.push({
                keywordsInput: inputs.keywordsInput.value,
                dateButtons: getSelectedDateButton(),
                filterOne: inputs.filterOne.value,
                filterTwo: inputs.filterTwo.value,
                dateFrom: inputs.dateFrom.value,
                dateTo: inputs.dateTo.value  
            });
            localStorage.setItem("searchHistoryArray", JSON.stringify(searchHistoryArray));
            searchNumber++;
        }
        
        if (inputs.keywordsInput.value || inputs.filterOne.value != CONST.NONE || inputs.filterTwo.value != CONST.NONE || inputs.dateFrom.value || inputs.dateTo.value) {
            NHMSearchQuery.displaySearchEvents(inputs.keywordsInput.value, inputs.filterOne.value, inputs.filterTwo.value, inputs.dateFrom.value, inputs.dateTo.value);
        } else {
            NHMSearchQuery.displayTodayEvents();
        }
        $(".event--calendar--search--result--description").dotdotdot();
        $("#events-calendar-loading").hide();
    };
    
    this.resetSelectedButtons = function () {
        for (var i = 0; i < inputs.dateButtons.length; i++) {
            inputs.dateButtons[i].className = CONST.DATE_BUTTON_CLASS;
        }
    };
    
    this.doSearch = doSearch;
    
    $(document).ready(function () {
        NHMSearchQuery.init();
        NHMCalendar.init();
        
        // Browser tries to access a stored search when clicking 'Back' or 'Forward' buttons
        if (window.location.href.indexOf("#search") > 0) {
            var searchIndex = parseInt(window.location.href.split('#')[1].replace("search_",""));
            searchHistoryArray = JSON.parse(localStorage.getItem("searchHistoryArray"));
            // Prevents accessing to a seach history position that does not exists
            if (searchHistoryArray.length != 0 &&  searchIndex < searchHistoryArray.length) {
                searchNumber = searchHistoryArray.length;
                setSearchParameters(searchHistoryArray[searchIndex]);
                doSearch(false);    
            }
            else { // Tried to access an invalid position in history, just load Today's events.
                NHMSearchQuery.displayTodayEvents();
            }
        }
        else {
            var getUrlParameter = function getUrlParameter(sParam) {
                var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                    sURLVariables = sPageURL.split('&'),
                    sParameterName,
                    i;

                for (i = 0; i < sURLVariables.length; i++) {
                    sParameterName = sURLVariables[i].split('=');
                    if (sParameterName[0] === sParam) {
                        return sParameterName[1] === undefined ? true : sParameterName[1];
                    }
                }
            };

            var params = {
                group : getUrlParameter('group'),
                title : getUrlParameter('title'),
                description : getUrlParameter('description'),
                venue : getUrlParameter('venue'),
                link : getUrlParameter('link'),
                tags : getUrlParameter('tags'),
                keywords : getUrlParameter('keywords'),
                from : getUrlParameter('from'),
                to : getUrlParameter('to'),
                text : getUrlParameter('text')
            };

            var hasParams = function() {
                return window.location.search.substring(1).replace("wcmmode=disabled","").length > 0;
            };

            if (hasParams()) { // Perform search with URL parameters indicated by the user.
                NHMSearchQuery.displayFilteredEvents(params);
            }
            else {
                NHMSearchQuery.displayTodayEvents();
            }
        }
    });

    window.addEventListener('popstate', function(event) {
        if (ignoreHistoryChange) { // User performed a search 
            ignoreHistoryChange = false;
        }
        else { // User clicked 'Back' or 'Forward' button
            if(window.location.href.indexOf("#search") > 0) {
                var searchIndex = parseInt(window.location.href.split('#')[1].replace("search_",""));
                if (searchIndex < searchHistoryArray.length) {
                    setSearchParameters(searchHistoryArray[searchIndex]);
                    doSearch(false);
                }
                else { // User tried to go to an url like http://<calendar-url>#searchX in which the searchX parameters don't exist.
                    window.location = "";
                }
            }
            else { // Show today's events. 
                resetFilters(false);
            }
        }
    }, false);
};

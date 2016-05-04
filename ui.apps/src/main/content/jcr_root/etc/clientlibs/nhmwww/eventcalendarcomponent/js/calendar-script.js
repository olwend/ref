var NHMCalendar = NHMCalendar || {};
if (typeof NHMCalendar.Profile === 'undefined') {
    NHMCalendar.Profile = function () {
        this.CONST = {
            DATE_BUTTON_SELECTED_CLASS: 'date--button--selected',
            DATE_BUTTON_CLASS:          'small-12 medium-12 large-12 columns date--button',
            THIRTY_DAYS:                'thirtyDays',
            SEVEN_DAYS:                 'sevenDays',
            DATE_FROM:                  '#dateFrom',
            SET_DATE:                   'setDate',
            MIN_DATE:                   'minDate',
            DATE_TO:                    '#dateTo',
            OPTION:                     'option',
            CHANGE:                     'change',
            KEYUP:                      'keyup',
            CLICK:                      'click',
            NONE:                       'none'
        };
        
        this.inputs = {
            keywordsInput:  document.getElementById("keywordsInput"),
            searchButton:   document.getElementsByClassName("search-icon"),
            resetButton:    document.getElementsByClassName("reset--filter"),
            dateButtons:    document.getElementsByClassName("date--button"),
            filterOne:      document.getElementById("filterOne"),
            filterTwo:      document.getElementById("filterTwo"),
            dateFrom:       document.getElementById("dateFrom"),
            dateTo:         document.getElementById("dateTo")
        };
        
        //Sets the listenerts needed
        this.bindEvents = function () {
            var that = this;
            this.inputs.keywordsInput.addEventListener(this.CONST.KEYUP, function(e) {
                that.getKey(e);
            }, false);
            this.inputs.searchButton[0].addEventListener(this.CONST.CLICK, function() {
                that.doSearch();
            }, false);
            this.inputs.resetButton[0].addEventListener(this.CONST.CLICK, function() {
                that.resetFilters();
            }, false);
            this.inputs.filterOne.addEventListener(this.CONST.CHANGE, function() {
                that.doSearch();
            }, false);
            this.inputs.filterTwo.addEventListener(this.CONST.CHANGE, function() {
                that.doSearch();
            }, false);

            for (var i = 0; i < this.inputs.dateButtons.length; i++) {
                this.inputs.dateButtons[i].addEventListener(this.CONST.CLICK, function(e) {
                    that.setDateButton(e);
                }, false);
            }  
        };
        
        //Do Search if key pressed is enter
        this.getKey = function (event) {
            if (event.which == 13 || event.keyCode == 13) {
                this.doSearch();
            }
        };
        
        //Function to reset the calendar
        this.resetFilters = function () {
            //Reset the keyword
            this.inputs.keywordsInput.value = "";

            //Reset the filters
            this.inputs.filterOne.value = this.CONST.NONE;
            this.inputs.filterTwo.value = this.CONST.NONE;

            //Reset the dates
            for (var i = 0; i < this.inputs.dateButtons.length; i++) {
                this.inputs.dateButtons[i].className = this.CONST.DATE_BUTTON_CLASS;   
            }
            $(this.CONST.DATE_FROM).datepicker(this.CONST.SET_DATE, null);
            $(this.CONST.DATE_TO).datepicker(this.CONST.SET_DATE, null);
            $(this.CONST.DATE_TO).datepicker(this.CONST.OPTION, this.CONST.MIN_DATE, 0);

            //Call the search function
            this.doSearch();
        };
        
        //Function to set the button selected
        this.setDateButton = function (event) {
            var buttonEl = event.currentTarget;
            for (var i = 0; i < this.inputs.dateButtons.length; i++) {
                if (this.inputs.dateButtons[i].id != buttonEl.id) {
                    this.inputs.dateButtons[i].className = this.CONST.DATE_BUTTON_CLASS;   
                }
            }

            if (!buttonEl.classList.contains(this.CONST.DATE_BUTTON_SELECTED_CLASS)) {
                buttonEl.className += (" " + this.CONST.DATE_BUTTON_SELECTED_CLASS);
                //Set the selected dates
                this.setDatesFromButton(buttonEl.id);
            } 
            else {
                buttonEl.className = this.CONST.DATE_BUTTON_CLASS;
                $(this.CONST.DATE_FROM).datepicker(this.CONST.SET_DATE, null);
                $(this.CONST.DATE_TO).datepicker(this.CONST.SET_DATE, null);
            }
    
            this.doSearch();
        };
        
        //Function to set the datepicker values from the buttons
        this.setDatesFromButton = function (dates) {
                var today = new Date(),
                    fromDate = today;

                $(this.CONST.DATE_FROM).datepicker(this.CONST.SET_DATE, today);
                $(this.CONST.DATE_TO).datepicker(this.CONST.OPTION, this.CONST.MIN_DATE, 0);

                switch (dates) {

                    case this.CONST.SEVEN_DAYS:

                        fromDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7);            

                        break;

                    case this.CONST.THIRTY_DAYS:

                        fromDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 30);

                        break;
                }

                $(this.CONST.DATE_TO).datepicker(this.CONST.SET_DATE, fromDate);
        };
        
        //Function to trigger the search
        this.doSearch = function () {
            if (this.inputs.keywordsInput.value || this.inputs.filterOne.value != this.CONST.NONE || this.inputs.filterTwo.value != this.CONST.NONE || this.inputs.dateFrom.value || this.inputs.dateTo.value) {
                displaySearchEvents(this.inputs.keywordsInput.value, this.inputs.filterOne.value , this.inputs.filterTwo.value , this.inputs.dateFrom.value , this.inputs.dateTo.value);
            }
            else {
                displayTodayEvents(); 
            }
        };
    };
}

$(document).ready(function(){
    var profile = new NHMCalendar.Profile();
    profile.bindEvents();
}); 
//Needed for using multiple onload
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}

addLoadEvent(function() {
    //Sets the listenerts needed
    var keywordsInput = document.getElementById("keywordsInput");
    keywordsInput.addEventListener("keyup", function(){ getKey(event); }, false);
    
    var searchButton = document.getElementsByClassName("search-icon");
    searchButton[0].addEventListener("click", doSearch, false);
    
    var resetButton = document.getElementsByClassName("reset--filter");
    resetButton[0].addEventListener("click", resetFilters, false);
    
    var dateButtons = document.getElementsByClassName("date--button");
    for (var i = 0; i < dateButtons.length; i++) {
        dateButtons[i].addEventListener('click', setDateButton, false);
    }
});    

//Do Search if key pressed is enter
function getKey(event) {
    if (event.which == 13 || event.keyCode == 13) {
        doSearch();
    }
    return;
};

//Function to trigger the search
function doSearch(){
    var keywordsInput   = document.getElementById("keywordsInput"),
        filterOne       = document.getElementById("filterOne"),
        filterTwo       = document.getElementById("filterTwo"),
        dateFrom        = document.getElementById("dateFrom"),
        dateTo          = document.getElementById("dateTo");
    
    displaySearchEvents(keywordsInput.value, filterOne.value, filterTwo.value, dateFrom.value, dateTo.value);
    
    return;
};

//Function to reset the filters
function resetFilters () {
    var filterOne = document.getElementById("filterOne"),
        filterTwo = document.getElementById("filterTwo");
    
    filterOne.value = "none";
    filterTwo.value = "none";
    
    return;
};

//Functio to set the button selected
function setDateButton () {
    var dateButtons = document.getElementsByClassName("date--button");
    
    for (var i = 0; i < dateButtons.length; i++) {
        if (dateButtons[i].id != this.id) {
            dateButtons[i].className = "small-12 medium-12 large-12 columns date--button";   
        }
    }
    
    if (!this.classList.contains("date--button--selected")) {
        this.className += " date--button--selected";
        //Set the selected dates
        setDatesFromButton(this.id);
    } 
    else {
        this.className = "small-12 medium-12 large-12 columns date--button"
         $('#dateFrom').datepicker("setDate", null);
         $('#dateTo').datepicker("setDate", null);
    }
    
    return;
};

function setDatesFromButton(dates) {
    var today = new Date();
    $('#dateFrom').datepicker("setDate", today);  
    
    switch (dates) {
        
        case "today":
            
            $('#dateTo').datepicker("setDate", today);
            
            break;
        
        case "sevenDays":
            
            var sevenDays = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7);            
            $('#dateTo').datepicker("setDate", sevenDays);
            
            break;
        
        case "thirtyDays":
            
            var thirtyDays = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 30);
            $('#dateTo').datepicker("setDate", thirtyDays);
            
            break;
    }
};
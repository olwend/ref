var name = "";
var surname = "";
var keywords = "";
var autoKeywords = "";
	
var $elementSelected;
var departmentDivision = "";

var $collectionGroupSelected;;
var collectionsGroup = "";

var loadDepartmentFromURL = false;
var loadCollectionsFromURL = false;

var ignoreURL = false;

$.extend({
	getUrlVars : function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(
				window.location.href.indexOf('?') + 1).split('&');
		for (var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	},
	getUrlVar : function(name) {
		return $.getUrlVars()[name];
	}
});


$(document).ready(function() {
	
	var globalMaxResult = 8;
	
	var nameSorted 			= false,
		jobSorted			= false,
		departmentSorted	= false,
		specialismsSorted	= false;
	
	var names = [],
		firstnames = [],
		surnames = [],
		keywords = [],
		autoKeywords = [];
	
	tableColors();
	
	names = populateOptions();
	firstnames = populateFirstNames();
	surnames = populateSurNames();
	keywords = populateKeywords();
	autoKeywords = populateAutoKeywords();
	
	
	// ######################
	// #### Autocomplete #### 
	// ######################
	
	$("#firstNameInput" ).autocomplete({
		source:firstnames,
		minLength: 3
	});
	
	$("#surnameInput" ).autocomplete({
		source:surnames,
		minLength: 3
	});
	
	$("#keywordsInput" ).autocomplete({
		source:autoKeywords,
		minLength: 3
	});

	// #################
	// #### Sorting #### 
	// #################
	
	$("#name").on("click", function(){
		nameSorted = sortTable(0, nameSorted);
		var $this = $(this);			
//		In case we end up adding &and; &or; we can do something like:
//		var aux = $(this).text() + '&and;/&or;';
//		$(this).text(aux);
		if ( $(this).hasClass('sort-results-down') ){
			$(this).css('background-color', 'red');  
			$(this).removeClass('sort-results-down');
			$(this).addClass('sort-results-up');
		} else {
			$(this).css('background-color', 'blue');  
			$(this).removeClass('sort-results-up');
			$(this).addClass('sort-results-down');
		}
	});

	$("#job").on("click", function(){
		jobSorted = sortTable(1, jobSorted);
		var $this = $(this);			
		if ( $(this).hasClass('sort-results-down') ){
			$(this).css('background-color', 'red');  
			$(this).removeClass('sort-results-down');
			$(this).addClass('sort-results-up');
		} else {
			$(this).css('background-color', 'blue');  
			$(this).removeClass('sort-results-up');
			$(this).addClass('sort-results-down');
		}
	});

	$("#departAndDiv").on("click", function(){
		departmentSorted = sortTable(2, departmentSorted);
		var $this = $(this);			
		if ( $(this).hasClass('sort-results-down') ){
			$(this).css('background-color', 'red');  
			$(this).removeClass('sort-results-down');
			$(this).addClass('sort-results-up');
		} else {
			$(this).css('background-color', 'blue');  
			$(this).removeClass('sort-results-up');
			$(this).addClass('sort-results-down');
		}
	});

	$("#specialisms").on("click", function(){
		specialismsSorted = sortTable(3, specialismsSorted);
		var $this = $(this);			
		if ( $(this).hasClass('sort-results-down') ){
			$(this).css('background-color', 'red');  
			$(this).removeClass('sort-results-down');
			$(this).addClass('sort-results-up');
		} else {
			$(this).css('background-color', 'blue');  
			$(this).removeClass('sort-results-up');
			$(this).addClass('sort-results-down');
		}
	});
	
	// ###############################
	// #### Search & More Results #### 
	// ###############################
	
	$("collection").on("change", (function(e) {
		globalMaxResult = 8;
		saveSearchTerms();
	}));
	
	$("division").on("change", (function(e) {
		globalMaxResult = 8;
		saveSearchTerms();
	}));

	$("#search").click(function() {
		globalMaxResult = 8;
		saveSearchTerms();
		searchFunc(globalMaxResult)
	});
	
	$("#show-more").bind('click', function(event) {
		event.preventDefault();
		globalMaxResult += 8;

//		Update text depending on nOf results displayed for Debugging Purposes
//		var $this = $(this);			
//		$this.text('Show More (Showing ' + globalMaxResult + ')');
		
		searchFunc(globalMaxResult);
	});
	
	nameSorted = sortTable(0, nameSorted);
	
	saveSearchTerms();
	searchFunc(globalMaxResult);
});

function saveSearchTerms() {

	name = $("#firstNameInput").val();

	surname = $("#surnameInput").val();

	keywords = $("#keywordsInput").val();
	
	$elementSelected = $("#division option:selected");
	departmentDivision = $elementSelected.val();
	
	$collectionGroupSelected = $("#collection option:selected");
	collectionsGroup = $collectionGroupSelected.val();
	
	if (typeof name === undefined || name === null || name === '') {
		aux = $.getUrlVar('name');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			name = aux;
		}
	} else {
		ignoreURL = true;
	}
	
	if (typeof surname === undefined || surname === null || surname === '') {
		aux = $.getUrlVar('surname');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			surname = aux;
		}
	} else {
		ignoreURL = true;
	}
	
	if (typeof keywords === undefined || keywords === null || keywords === '') {
		aux = $.getUrlVar('specialism');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			keywords = aux;
		}
	} else {
		ignoreURL = true;
	}
	
	if (departmentDivision === 'All') {
		aux = $.getUrlVar('division');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			loadDepartmentFromURL = true;
			departmentDivision = aux;
		} else {
			aux = $.getUrlVar('department');
			if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
				loadDepartmentFromURL = true;
				departmentDivision = aux;
			}
		}
	} else {
		ignoreURL = true;
	}
	
	if (collectionsGroup === 'All') {
		aux = $.getUrlVar('collection');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			loadCollectionsFromURL = true;
			collectionsGroup = aux;
		} else {
			aux = $.getUrlVar('group');
			if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
				loadCollectionsFromURL = true;
				collectionsGroup = aux;
			}
		}
	} else {
		ignoreURL = true;
	}
}

function searchFunc(maxResults) {

	var nodes = $("#peopleList").children().children();

	nodes.css("display", "none");
	
	if(!ignoreURL) {		
		var $name = document.getElementById("firstNameInput");
		$name.value = decodeURIComponent(name);
	} 
	
	if (name.length != 0) {
		var lowercase = name.toLowerCase();
		nodes = nodes.filter(function(){
			
			//This is going to be similar to department
			var $thisFirstName = $(this).attr("firstname").toLowerCase();
			
			if ($thisFirstName.match(lowercase)) {
				return true;
			}
			
			return false;
		});
	}
	
	if(!ignoreURL) {		
		var $surname = document.getElementById("surnameInput");
		$surname.value = decodeURIComponent(surname);
	} 

	if (surname.length != 0) {
		var lowercase = surname.toLowerCase();
		nodes = nodes.filter(function(){
			
			//This is going to be similar to department
			var $thisSurName = $(this).attr("secondname").toLowerCase();
			
			if ($thisSurName.match(lowercase)) {
				return true;
			}
			
			return false;
		});
	}
	
	if(!ignoreURL) {		
		var $keywords = document.getElementById("keywordsInput");
		$keywords.value = decodeURIComponent(keywords);
	} 
	
	if (keywords.length != 0) {
	    var query = keywords.toLowerCase();
	    var querywords = query.split(',');
	    var results = new Array();
	    var regex = '';
	    for ( var i = 0; i < querywords.length; i++ ) {
	        regex = new RegExp( '(?=.*\\b' + querywords[i].split(' ').join('\\b)(?=.*\\b') + '\\b)', 'i' );
	    }
	    
		nodes = nodes.filter(function(){
			var $thisSpecialisms = $(this).attr("specialisms").toLowerCase();
	        if ( regex.test( $thisSpecialisms ) ) {
	            return true;
	        }
	        return false;
		});
	}
	
	if(loadDepartmentFromURL && !ignoreURL) {		
		var $division = document.getElementById("division");
		$division.value = decodeURIComponent(departmentDivision);
		$elementSelected = $("#division option:selected");
	} 

	if (departmentDivision != "All") {
		if ($elementSelected.hasClass("department")) {
			nodes = nodes.filter("[department=" + '"' + $elementSelected.val() + '"' + "]");
		}
		
		if ($elementSelected.hasClass("division")) {
			var department = $elementSelected.data("department");
			var division = $elementSelected.data("division");
			nodes = nodes.filter("[division=" + '"' + division + '"' + "][department=" + '"' + department + '"' + "]");	
		}
	}

	if(loadCollectionsFromURL && !ignoreURL) {		
		var $collection = document.getElementById("collection");
		$collection.value = decodeURIComponent(collectionsGroup);
		$collectionGroupSelected = $("#collection option:selected");
	} 
	
	if (collectionsGroup != "All") {
		if ($collectionGroupSelected.hasClass("collection")) {
			nodes = nodes.filter("[collection=" + '"' + $collectionGroupSelected.val() + '"' + "]");
		}
		
		if ($collectionGroupSelected.hasClass("group")) {
			var collection = $collectionGroupSelected.data("collection");
			var group = $collectionGroupSelected.data("group");
			nodes = nodes.filter("[group=" + '"' + group + '"' + "][collection=" + '"' + collection + '"' + "]");	
		}
	}
	
	if (nodes.length < maxResults) {
		$("#show-more").hide();
	} else {
		$("#show-more").show();
	}
	
	nodes = nodes.filter(":lt(" + maxResults + ")");
	
	nodes.css("display", "");
	
	tableColors();
}

function populateOptions() {

	var names		= [],
		activities 	= [], 
		divisions 	= [];
	
	//Get the nodes
	var nodes = $("#peopleList").children().children();

	//Populates the arrays with the information
	for ( var i = 0; i < nodes.length; i++) {

		var firstName	= nodes[i].getAttribute("firstname"),
			lastName	= nodes[i].getAttribute("secondname"),
			activity 	= nodes[i].getAttribute("activity"), 
			department  = nodes[i].getAttribute("department"),
			division 	= nodes[i].getAttribute("division");

		names.push(lastName + ", " + firstName);
		
		//Needed to not duplicate information
		if (activities.indexOf(activity) == -1) {
			activities.push(activity);
		}
		
		/*if ($("#division option.department[value='" + department + "']").length == 0) {
			$('#division').append(
					'<option class="department" value="'+department+'">' + department + '</option>');
		}
		
		var optionText = department+"-"+division;
		if ($("#division option.division[value='" + optionText + "']").length == 0) 
		{
			$('#division').append(
					'<option class="division" value="'+optionText+'" data-department="'+ department +'" data-division="' + division + '">&nbsp;&nbsp;&nbsp;&nbsp;' + division + '</option>');
		}*/
	}
	
	//Ascending sort of the arrays
	names.sort();
	
	/*$("#division").html($('#division option').sort(function(x, y) {
		var xText = $(x).val();
		var yText = $(y).val();
		
		if (xText == "All") {
			return -1;
		}
		if (yText == "All") {
			return 1;
		}
		
		return xText < yText ? -1 : 1;
	}));
	 $("#division").get(0).selectedIndex = 0;*/
	
	return names;
}

function populateFirstNames() {
	var names = [];
	
	//Get the nodes
	var nodes = $("#peopleList").children().children();
	
	//Populates the arrays with the information
	for ( var i = 0; i < nodes.length; i++) {
		var firstName = nodes[i].getAttribute("firstname");
		if ( $.inArray(firstName, names) < 0 ) {
			names.push( firstName );	
		}
	}
	
	//Ascending sort of the arrays
	names.sort();
	return names;
}

function populateSurNames() {
	var names = [];
	
	//Get the nodes
	var nodes = $("#peopleList").children().children();
	
	//Populates the arrays with the information
	for ( var i = 0; i < nodes.length; i++) {
		var lastName = nodes[i].getAttribute("secondname");
		if ( $.inArray(lastName, names) < 0 ) {
			names.push( lastName );	
		}
	}
	
	//Ascending sort of the arrays
	names.sort();
	return names;
}

function populateKeywords() {
	var keywords = [];
	
	//Get the nodes
	var nodes = $("#peopleList").children().children();
	
	//Populates the arrays with the information
	for ( var i = 0; i < nodes.length; i++) {
		var specialisms = nodes[i].getAttribute("specialisms");
		keywords.push(specialisms);
	}
	
	//Ascending sort of the arrays
	keywords.sort();
	return keywords;
}

function populateAutoKeywords() {
	var autokeywords = [];
	
	//Get the nodes
	var nodes = $("#peopleList").children().children();
	
	//Populates the arrays with the information
	for ( var i = 0; i < nodes.length; i++) {
		var specialisms = nodes[i].getAttribute("specialisms").toLowerCase();
		
		var specialismsArray = specialisms.match(/\w+/g);
		
		if ( specialismsArray != null && specialismsArray.length > 0 ){
			for ( var j = 0; j < specialismsArray.length; j++ ) {
				if ( $.inArray(specialismsArray[j], autokeywords) < 0 ) {
					autokeywords.push( specialismsArray[j] );	
				}
	        }
		}
	}
	
	//Ascending sort of the arrays
	autokeywords.sort();
	return autokeywords;
}

function tableColors() {
	var childDiv = $("#peopleList").children();

	var position = 0;
	for ( var x = 0; x < childDiv.length; x++) {
		if ($(childDiv[x]).height() > 0) {
			childDiv[x].className = "row directory-search-results--row";
//			Old code for even / odd columns 			
//			if (position % 2 == 0) {
//				childDiv[x].className = "row profiles_row odd";
//			} else {
//				childDiv[x].className = "row profiles_row";
//			}
			position++;
		}
	}
}

function sortTable(column, isSorted) {
	var sort_by_name;
	
	if (!isSorted) {
		sort_by_name = function(a, b) {
			if (column == 0) {
				return $(a).children().children().eq(column).children('.profile-content').text().toLowerCase().localeCompare(
						$(b).children().children().eq(column).children('.profile-content').text().toLowerCase());
			} else {
				return $(a).children().children().eq(column).text().toLowerCase().localeCompare(
					$(b).children().children().eq(column).text().toLowerCase());
			}
		}
	} else {
		sort_by_name = function(a, b) {
			if (column == 0) {
				return $(b).children().children().eq(column).children('.profile-content').text().toLowerCase().localeCompare(
						$(a).children().children().eq(column).children('.profile-content').text().toLowerCase());
			} else {
				return $(b).children().children().eq(column).text().toLowerCase().localeCompare(
					$(a).children().children().eq(column).text().toLowerCase());
			}
		}
	}

	$this = $("#peopleList");
	var list = $this.children();
	list.sort(sort_by_name);
	
	$this.html(list); 
	
	tableColors();
	
	return (!isSorted);
}
	
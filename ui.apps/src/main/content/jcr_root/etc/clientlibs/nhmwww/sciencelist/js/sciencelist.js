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
		nameSorted = sortTable(1, nameSorted);
		var $this = $(this);			
		if ( $(this).hasClass('directory-search-results--sort-results-down') ){
			$(this).removeClass('directory-search-results--sort-results-down');
			$(this).addClass('directory-search-results--sort-results-up');
		} else {
			$(this).removeClass('directory-search-results--sort-results-up');
			$(this).addClass('directory-search-results--sort-results-down');
		}
	});

	$("#job").on("click", function(){
		jobSorted = sortTable(2, jobSorted);
		var $this = $(this);			
		if ( $(this).hasClass('directory-search-results--sort-results-down') ){
			$(this).removeClass('directory-search-results--sort-results-down');
			$(this).addClass('directory-search-results--sort-results-up');
		} else {
			$(this).removeClass('directory-search-results--sort-results-up');
			$(this).addClass('directory-search-results--sort-results-down');
		}
	});

	$("#departAndDiv").on("click", function(){
		departmentSorted = sortTable(3, departmentSorted);
		var $this = $(this);			
		if ( $(this).hasClass('directory-search-results--sort-results-down') ){
			$(this).removeClass('directory-search-results--sort-results-down');
			$(this).addClass('directory-search-results--sort-results-up');
		} else {
			$(this).removeClass('directory-search-results--sort-results-up');
			$(this).addClass('directory-search-results--sort-results-down');
		}
	});

//	$("#specialisms").on("click", function(){
//		specialismsSorted = sortTable(4, specialismsSorted);
//		var $this = $(this);			
//		if ( $(this).hasClass('directory-search-results--sort-results-down') ){
//			$(this).removeClass('directory-search-results--sort-results-down');
//			$(this).addClass('directory-search-results--sort-results-up');
//		} else {
//			$(this).removeClass('directory-search-results--sort-results-up');
//			$(this).addClass('directory-search-results--sort-results-down');
//		}
//	});
	
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
	
	nameSorted = sortTable(1, nameSorted);
	
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
	
	// #######################################
	// #### Should URL Parameters be used ####
	// #######################################
	
	if (typeof name !== undefined && name != null && name != '') {
		ignoreURL = true;
	}
	
	if (typeof surname !== undefined && surname != null && surname != '') {
		ignoreURL = true;
	}
	
	if (typeof keywords !== undefined && keywords != null && keywords != '') {
		ignoreURL = true;
	}
	
	if (departmentDivision != 'All') {
		ignoreURL = true;
	}
	
	if (collectionsGroup != 'All') {
		ignoreURL = true;
	}
	
	if (!ignoreURL){
		aux = $.getUrlVar('name');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			name = aux;
		}
		aux = $.getUrlVar('surname');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			surname = aux;
		}
		aux = $.getUrlVar('specialism');
		if (!(typeof aux === 'undefined' || aux === null || aux === '')) {
			keywords = aux;
		}
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
		
		/** New Implementation **/
			//  Looking for [collection="Research"][group="Vertebrates, Birds"] || [collection="Collections"][group="Vertebrates, Birds"] 
			nodes = nodes.filter('[collection="Collections"]');
			//  Looking for [collection="Collections"][group="Vertebrates, Birds"] 
			var group = $collectionGroupSelected.data("group");

			if ($collectionGroupSelected.hasClass("group")) {
				// [group="Vertebrates, Birds"] 
				var query = group.toLowerCase();
				var queryRegex = new RegExp( '(?=.*\\b' + query.split(' ').join('\\b)(?=.*\\b') + '\\b)', 'i' );
				
				nodes = nodes.filter(function(){
					var $thisCollectionsGroup = $(this).attr("group").toLowerCase();
					console.log($thisCollectionsGroup);
			        if ( queryRegex.test( $thisCollectionsGroup ) ) {
			            return true;
			        }
			        return false;
				});
			}
			
			if ($collectionGroupSelected.hasClass("collection")) {
				
				var parentGroup;
				
				switch (collectionsGroup) {
				case "Entomology":
					parentGroup = [ "Hymenoptera", "Coleoptera", "Lepidoptera", "Siphonaptera", "Diptera", "Hemiptera", 
					        "Phthiraptera", "Thysanoptera", "Psocoptera", "Odonata",  "Neuroptera", "Apterygota",
					        "Arachnida", "Myriapoda", "Onychophora", "Tardigrada", "Historical" ];
					break;
				case "Zoology":
					parentGroup = [ "Invertebrates", "Vertebrates", "Birds", "Fish", "Amphibians", "Reptiles", "Mammals" ];
					break;
				default:
					parentGroup = [];
					break;
				}
				
				var queryRegex = new RegExp( '(?=.*\\b(' + parentGroup.join(")|(") + ')\\b)', 'i' );
				
				nodes = nodes.filter(function() {
					var $thisCollectionsGroup = $(this).attr("group").toLowerCase();
					if ( queryRegex.test ( $thisCollectionsGroup )) {
						return true;
					}
				});
			}
		/** New Implementation **/
	}
	
	if (nodes.length < maxResults) {
		$("#show-more").hide();
	} else {
		$("#show-more").show();
	}
	
	nodes = nodes.filter(":lt(" + maxResults + ")");
	
	nodes.css("display", "");
	nodes.addClass("directory-search--result");
	
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
	for ( var x = 0; x < childDiv.length; x++) {
		var personNodes = $(childDiv[x]).children();
		for ( var i = 0; i < personNodes.length; i++) {
			if ($(personNodes[i]).hasClass("directory-search--result")) {
				$(personNodes[i]).removeClass("directory-search--result");
				$(personNodes[i]).addClass("row directory-search-results--row");
			}
		}
	}
}

function sortTable(column, isSorted) {
	var sort_by_name;
	
	if (!isSorted) {
		sort_by_name = function(a, b) {
			return $(a).children().children().children().eq(column).text().toLowerCase().localeCompare(
				$(b).children().children().children().eq(column).text().toLowerCase());
		}
	} else {
		sort_by_name = function(a, b) {
			return $(b).children().children().children().eq(column).text().toLowerCase().localeCompare(
				$(a).children().children().children().eq(column).text().toLowerCase());
		}
	}

	$this = $("#peopleList");
	var list = $this.children();
	list.sort(sort_by_name);
	
	$this.html(list); 
	
	tableColors();
	
	return (!isSorted);
}
	
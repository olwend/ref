
$(document).ready(function() {
	
	var nameSorted 			= false,
		jobSorted			= false,
		departmentSorted	= false,
		specialismsSorted	= false;
	
	var names = [];
	
	tableColors();
	
	names = populateOptions();
	
	//Needed to autocomplete the Name field
	$("#nameInput" ).autocomplete({
		source:names,
		minLength: 3
	});

	$("#search").click(function() {
		searchFunc();
	});

	$("#name").click(function() {
		nameSorted = sortTable(0, nameSorted);
	});

	$("#job").click(function() {
		jobSorted = sortTable(1, jobSorted);
	});

	$("#departAndDiv").click(function() {
		departmentSorted = sortTable(2, departmentSorted);
	});

	$("#specialisms").click(function() {
		specialismsSorted = sortTable(3, specialismsSorted);
	});
	
	nameSorted = sortTable(0, nameSorted);
});

function searchFunc() {

	var name = $("#nameInput").val().split(",");
	
	var $elementSelected = $("#division option:selected");
	var departmentDivision = $elementSelected.val();

	var nodes = $("#peopleList").children().children();

	nodes.css("display", "none");

	if (name[0].length != 0) {
		nodes = nodes.filter(function(){
			var $thisFirstName = $(this).attr("firstname").toLowerCase();
			var $thisSecondName = $(this).attr("secondname").toLowerCase();
			
			if (name.length > 1) {
				var firstName = $.trim(name[1].toLowerCase());
			} else {
				var firstName = "";
			}
			
			var surname = $.trim(name[0].toLowerCase());
			
			if (firstName == "") {
				if ($thisFirstName.match("^" + surname) || $thisFirstName.match(surname + "$")) {
					return true;
				}
				
				if ($thisSecondName.match("^" + surname) || $thisSecondName.match(surname + "$")) {
					return true;
				}
				return false;
			} 
			
			if ($thisSecondName.match(surname + "$") && $thisFirstName.match("^" + firstName)) {
				return true;
			}
			
			return false;
		});
	}

	if (departmentDivision != "All") {
		if ($elementSelected.hasClass("department")) {
			console.log("Department: " + $elementSelected.val());
			nodes = nodes.filter("[department=" + '"' + $elementSelected.val() + '"' + "]");
		}
		
		if ($elementSelected.hasClass("division")) {
			var department = $elementSelected.data("department");
			var division = $elementSelected.data("division");
			console.log("Division: " + department + ", " + division);
			nodes = nodes.filter("[division=" + '"' + division + '"' + "][department=" + '"' + department + '"' + "]");	
		}
	}
	
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
		if (activities.indexOf(activity) == -1) 
		{
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

function tableColors() {
	var childDiv = $("#peopleList").children();

	var position = 0;
	for ( var x = 0; x < childDiv.length; x++) {
		if ($(childDiv[x]).height() > 0) {
			if (position % 2 == 0) 
			{
				childDiv[x].className = "row profiles_row odd";
			} else 
			{
				childDiv[x].className = "row profiles_row";
			}
			position++;
		}
	}
}

function sortTable(column, isSorted) {
	var sort_by_name;
	
	if (!isSorted)
	{
		sort_by_name = function(a, b) {
			if (column == 0) {
				return $(a).children().children().eq(column).children('.profile-content').text().toLowerCase().localeCompare(
						$(b).children().children().eq(column).children('.profile-content').text().toLowerCase());
			} else {
				return $(a).children().children().eq(column).text().toLowerCase().localeCompare(
					$(b).children().children().eq(column).text().toLowerCase());
			}
		}
	} else
	{
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
	
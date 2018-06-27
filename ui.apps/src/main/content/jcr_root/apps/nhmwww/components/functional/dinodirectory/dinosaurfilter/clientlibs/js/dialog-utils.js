/**
 *
 * Dialog utils contains functions to save second filter value as
 * ./filterTwo
 */

//Touch UI
$(document).on("click", ".cq-dialog-submit", function () {
    var dialogTitle = $('.cq-dialog-header').text();
    if(dialogTitle.includes("Dinosaur filter")) {
    	var $form = $(this).closest("form.foundation-form");

    	//Tags
    	var filterOne,
    		filterTwo;

        filterOne = $("select[name='./filterOne']").val();

        if(filterOne == 'body-shapes') {
        	filterTwo = $("select[name='./filterBodyShape']").val();
        }

        if(filterOne == 'countries') {
        	filterTwo = $("select[name='./filterCountry']").val();
        }

        if(filterOne == 'initials') {
        	filterTwo = $("select[name='./filterInitial']").val();
        }

        if(filterOne == 'periods') {
        	filterTwo = $("select[name='./filterPeriod']").val();
        }

        $('<input />').attr('type', 'hidden')
        .attr('name', "./filterTwo")
        .attr('value', filterTwo)
        .appendTo($form);
	}
});

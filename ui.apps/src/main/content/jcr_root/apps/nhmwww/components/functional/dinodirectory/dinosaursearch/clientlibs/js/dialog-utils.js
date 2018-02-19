/**
 * 
 * Dialog utils contains functions to save second filter value as
 * ./filterTwo
 */

//Touch UI
$(document).on("click", ".cq-dialog-submit", function () {
    var dialogTitle = $('.cq-dialog-header').text();
    if(dialogTitle.includes("Dinosaur search")) {
    	var $form = $(this).closest("form.foundation-form");

    	//Tags
    	var filterOne,
    		filterTwo;

        filterOne = $("select[name='./filterOne']").val();

        if(filterOne == 'bodyshape') {
        	filterTwo = $("select[name='./filterBodyShape']").val();
        }
        
        if(filterOne == 'country') {
        	filterTwo = $("select[name='./filterCountry']").val();
        }
        
        if(filterOne == 'initial') {
        	filterTwo = $("select[name='./filterInitial']").val();
        }
        
        if(filterOne == 'period') {
        	filterTwo = $("select[name='./filterPeriod']").val();
        }
        
        $('<input />').attr('type', 'hidden')
        .attr('name', "./filterTwo")
        .attr('value', filterTwo)
        .appendTo($form);
	}
});

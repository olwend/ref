/**
 * 
 * Dialog utils contains functions to concatenate tag and date fields in
 * article dialog.
 * 
 * Has classic and touch implementations.
 */

//Classic
function concatenate_tags(dialog) {

	var allTags   = dialog.findByType('multifield')[0],
	hubTag    = dialog.getField('./hubTag'),
	otherTags = dialog.getField('./otherTags'),
	datePublished = dialog.getField('./datepublished'),
	dateLastUpdated = dialog.getField('./datelastupdated'),

	allTagsArray = [],
	hubTagArray = hubTag.value,
	otherTagsArray = otherTags.value
	dateLastUpdatedValue = dateLastUpdated.value;

	//Concatenate all tags into one property to make querying easier 
	allTags.show();

	for(var i=0; i<hubTagArray.length; i++) {
		allTagsArray.push(hubTagArray[i]);
	}

	for(var i=0; i<otherTagsArray.length; i++) {
		allTagsArray.push(otherTagsArray[i]);
	}

	allTags.setValue(allTagsArray);

	//If no value given for date last updated, copy publish date into
	//this property. This is for querying against in article feed.
	if(dateLastUpdatedValue === undefined) {
		console.log("test");
		dateLastUpdated.setValue(datePublished.value);
	}
}

//Touch UI
$(document).on("click", ".cq-dialog-submit", function () {
    var dialogTitle = $('.cq-dialog-header').text();
    if(dialogTitle.includes("Article")) {
    	var $form = $(this).closest("form.foundation-form");

    	//Tags
    	var hubTagArray = [],
            otherTagsArray = [],
        	allTagsArray = [];

        //Only take the first tag in this field
		hubTagArray.push($("input[name='./hubTag']").val());

        //Get values for each other tag
        $("input[name='./otherTags']").each(function() {
            otherTagsArray.push(this.value);
        });

    	for(var i=0; i<hubTagArray.length; i++) {
    		allTagsArray.push(hubTagArray[i]);
    	}

    	for(var i=0; i<otherTagsArray.length; i++) {
    		allTagsArray.push(otherTagsArray[i]);
    	}

        for(var i=0; i<allTagsArray.length; i++) {
            $('<input />').attr('type', 'hidden')
            .attr('name', "./cq:tags")
            .attr('value', allTagsArray[i])
            .attr('class', 'cq-TagList-tag--existing')
            .appendTo($form);
        }
    	
    	//Dates
		var datePublished = getDate($("input[name='./datepublisheddate']").val()),
			dateLastUpdated = getDate($("input[name='./datelastupdateddate']").val());

        if(dateLastUpdated == null) dateLastUpdated = datePublished;

        $('<input />').attr('type', 'hidden')
        .attr('name', "./datepublished")
        .attr('value', datePublished)
        .appendTo($form);
        
        $('<input />').attr('type', 'hidden')
        .attr('name', "./datelastupdated")
        .attr('value', dateLastUpdated)
        .appendTo($form);
	}
});

function getDate(inputdate) {
    if(inputdate != "" && inputdate != undefined) {
        var datePublished = inputdate;
        var datePublishedItems = datePublished.split("-");
        datePublished = datePublishedItems[0] + "/" + datePublishedItems[1] + "/" + datePublishedItems[2].substring(0,2);
        return datePublished;
    } else {
		return null;
    }
}

//Touch UI
$(document).on("click", ".cq-dialog-submit", function () {
    var dialogTitle = $('.cq-dialog-header').text();
    if(dialogTitle == "Image page") {
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

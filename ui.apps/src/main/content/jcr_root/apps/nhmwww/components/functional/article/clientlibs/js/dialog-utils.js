
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

    allTags.show();

    for(var i=0; i<hubTagArray.length; i++) {
		allTagsArray.push(hubTagArray[i]);
    }

    for(var i=0; i<otherTagsArray.length; i++) {
		allTagsArray.push(otherTagsArray[i]);
    }
    
    allTags.setValue(allTagsArray);
    
    if(dateLastUpdatedValue === undefined) {
        console.log("test");
    	dateLastUpdated.setValue(datePublished.value);
    }
}


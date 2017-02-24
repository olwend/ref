/**
 * Saves value of sold out array back to correct place in repo using
 * hidden field in dialog.
 * 
 * @param dialog
 */
function saveSoldOutArray(dialog) {
    var soldOutField = dialog.getField('../../../../jcr:soldOut'),
    	datesPanel = dialog.getField('./links'),
        datesSubpanels = datesPanel.findByType('CustomMultiField'),
    	items = datesSubpanels[0].items.items;

    var index = -1;
    var subArray = [];
    var recurArray = [];
    var soldOutArray = [];

    for(var i=0; i<items.length; i++) {
        if(items[i].text != undefined) {
            if(items[i].text.match(/(\d)+$/)[0] > index) {
				index++;
                if(recurArray[0] != undefined) {
                    recurArray.push(subArray);
					soldOutArray.push(recurArray);
                    subArray = [];
                    recurArray = [];
                }
                if(subArray[0] != undefined) {
					soldOutArray.push(subArray);
                    subArray = [];
                }
            } else if(items[i].text.match(/(\d)+$/)[0] == index) {
				recurArray.push(subArray);
                subArray = [];
            }
        }
        if(items[i].checked != undefined) {
            subArray.push(items[i].checked);

        }
    }

    //Add final subArray
	if(recurArray[0] != undefined) {
        recurArray.push(subArray);
        soldOutArray.push(recurArray);
        subArray = [];
        recurArray = [];
    }
    if(subArray[0] != undefined) {
        soldOutArray.push(subArray);
        subArray = [];
    }

	var soldOutString = stringifySoldOutArray(soldOutArray);

    soldOutField.setValue(soldOutString);
}
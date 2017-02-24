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

    var count = -1,
        subCount = 0,
        soldOutArray = [],
    	index = -1;

    for(var i=0; i<items.length; i++) {
        if(items[i].text != undefined) {
			count++;
            subCount = 0;
            var subArray = [];

            if(items[i].text.match(/(\d)+$/)[0] > index) {

            }
            console.log(index);
        }
        if(items[i].checked != undefined) {
            subArray[subCount] = items[i].checked;
            subCount++;
        }
        if(count > -1) {
			soldOutArray[count] = subArray;
        }
    }

    soldOutField.setValue(JSON.stringify(soldOutArray));
}
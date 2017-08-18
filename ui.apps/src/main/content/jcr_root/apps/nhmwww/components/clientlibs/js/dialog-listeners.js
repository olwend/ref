//Listener to show/hide item or container based on checkbox value
//See HTI CTA for example implementation
(function ($, $document) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cq-dialog-checkbox-showhide").each( function() {
            showHide($(this));
        });
    });
    
    $document.on("change", ".cq-dialog-checkbox-showhide", function(e) {
        showHide($(this));
    });

    function showHide($el) {
		var shouldShowWhenChecked = $el.data('should-show-when-checked');
        var isChecked = $el.is(":checked");
        var targetElementSelector = $el.data('show-hide-target');
        
        if(targetElementSelector.indexOf(" ") > -1) {
        	var selectors = targetElementSelector.split(" ");
        } else {
        	var selectors = [targetElementSelector];
        }
        
        for(var i=0; i<selectors.length; i++) {
	        var $targetElement = $('#' + selectors[i]);
	
	        var $tabButton = $("[aria-controls='" + selectors[i] + "']");
	
	        var isVisible = shouldShowWhenChecked ? isChecked : !isChecked;
	        $targetElement.toggleClass('hidden', !isVisible);
	        $tabButton.toggleClass('hidden', !isVisible);
        }
    }

})($, $(document));
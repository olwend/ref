//Listener to show/hide item or container based on checkbox value
//See HTI CTA for example implementation
(function ($, $document) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cq-dialog-checkbox-showhide").each( function() {
        	checkboxShowHide($(this));
        });
        
        $(".cq-dialog-radio-showhide").each( function() {
        	radioShowHide($(this));
        });
    });
    
    $document.on("change", ".cq-dialog-checkbox-showhide", function(e) {
        checkboxShowHide($(this));
    });
    
    $document.on("change", ".cq-dialog-radio-showhide", function(e) {
        radioShowHide($(this));
    });

    function checkboxShowHide($checkboxElement) {
		var shouldShowWhenChecked = $checkboxElement.data('should-show-when-checked');
        var isChecked = $checkboxElement.is(":checked");
        var targetElementSelector = $checkboxElement.data('show-hide-target');
        
        if(targetElementSelector.indexOf(" ") > -1) {
        	var selectors = targetElementSelector.split(" ");
        } else {
        	var selectors = [targetElementSelector];
        }
        
        for(var i=0; i<selectors.length; i++) {
	        var $targetElement = $('#' + selectors[i]);
	        var isVisible = shouldShowWhenChecked ? isChecked : !isChecked;

	        $targetElement.toggleClass('hidden', !isVisible);
            var $tabButton = $("[aria-controls='" + selectors[i] + "']");
	        $tabButton.toggleClass('hidden', !isVisible);
        }
    }
    
    function radioShowHide($radioElement){
    	if($radioElement.is(':checked')){
    		var targetElementSelector = $radioElement.data('show-hide-target');
    		
			$(".cq-dialog-container-showhide").each( function() {
				$(this).toggleClass('hidden', true);
				var $tabButton = $("[aria-controls='" + $(this).attr('id') + "']");
				$tabButton.toggleClass('hidden', true);
 	        });  	
			
     		var $targetElement = $('#' + targetElementSelector);
			
			$targetElement.toggleClass('hidden', false);
			var $tabButton = $("[aria-controls='" + $targetElement.attr('id') + "']");
			$tabButton.toggleClass('hidden', false);
    	}
    }

})($, $(document));
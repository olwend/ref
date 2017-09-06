/**
 * Listeners
 */

//Listener to show/hide item or container based on checkbox value
//See HTI CTA for example implementation
(function ($, $document) {
	"use strict";

	$(document).on("foundation-contentloaded", function(e) {
		$(".cq-dialog-checkbox-showhide").each( function() {
			checkboxShowHide($(this));
		});
	});

	$document.on("change", ".cq-dialog-checkbox-showhide", function(e) {
		checkboxShowHide($(this));
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
})($, $(document));


//Listener to show/hide item or container based on radio button value
//See Discover Publication for example implementation
(function ($, $document) {
	"use strict";

	$(document).on("foundation-contentloaded", function(e) {
		$(".cq-dialog-radio-showhide").each( function() {
			radioShowHide($(this));
		});
	});

	$document.on("change", ".cq-dialog-radio-showhide", function(e) {
		radioShowHide($(this));
	});

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

/**
 * Validators
 */

//Validator for Event Detail component to check if a price has been added
(function ($, $document) {
	"use strict";

	$(document).on("click", ".cq-dialog-submit", function (e) {
		if($('.required-price').length) {
			e.stopPropagation();
			e.preventDefault();
	
			var $form = $(this).closest("form.foundation-form");
	        var textfieldNotEmpty = false;
	        
			$('.required-price').each( function() {
				var value = $(this).val();
	
				if(value != "") {
					textfieldNotEmpty = true;
				}
			});
	
			if(textfieldNotEmpty == false) {
				alert("nope");
				//TODO - present the error to the user
			} else {
				$form.submit();
			}
		}
	});
})($, $(document), document, Granite.$, Granite.author);

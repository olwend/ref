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
    
    //Listener to show/hide item or container based on drop down value
    //See Dinosaur Search for example implementation
    (function ($, $document) {
        "use strict";
    
        $(document).on("foundation-contentloaded", function(e) {
            var $multifields = $("[data-eaem-nested]");
    
            if(_.isEmpty($multifields)){
                $(".cq-dialog-select-showhide").each( function() {
                    selectShowHide($(this));
                });
            } else {
                var mNames = getMultiFieldNames($multifields),
                    $form = $(".cq-dialog"),
                    actionUrl = $form.attr("action") + ".infinity.json";
    
                $.ajax(actionUrl).done(postProcess);
    
                function postProcess(data){
                    _.each(mNames, function($multifield, mName){
                        $(".cq-dialog-select-showhide").each( function() {
                            var $fieldset = $(this).closest('.coral-Form-fieldset');

                            var $fields = $fieldset.children().children();
            
                            selectShowHideMultifield($fields);
                        });
                    });
                }
            }
        });
    
        $(document).on('selected.select', '#filterSelection', function(e) {
            if ($(this).parents('.coral-Form-fieldset').length) {

				var $fieldset = $(this).closest('.coral-Form-fieldset');

                var $fields = $fieldset.children().children();

                selectShowHideMultifield($fields);
            } else {
                selectShowHide($(this));
            }
        });

        function selectShowHide($selectElement) {
            console.log($selectElement);
            var currentSelect = "select-" + $selectElement.children('select').val();
    
            $(".showhide-target").each( function() {
                if($(this).attr('id').includes(currentSelect)) {
                    $(this).toggleClass('hidden', false);
                }
    
                if(!$(this).attr('id').includes(currentSelect)) {
                    $(this).toggleClass('hidden', true);
                }
            });
        }

        function selectShowHideMultifield($fields) {
            var currentSelect = "select-" + $fields.find('#filterSelection').children('select').val();

            $fields.each(function (j, field) {
                if($(this).attr('id') != null) {
                    if($(this).attr('id').includes(currentSelect)) {
                    	$(this).toggleClass('hidden', false);
                    }

                    if(!$(this).attr('id').includes(currentSelect)) {
                        $(this).toggleClass('hidden', true);
                    }
                }
            });
        }
    
    })($, $(document));
    
    function getMultiFieldNames($multifields){
        var mNames = {}, mName;
        
        $multifields.each(function (i, multifield) {
            mName = $(multifield).children("[name$='@Delete']").attr("name");
            mName = mName.substring(0, mName.indexOf("@"));
            mName = mName.substring(2);
            mNames[mName] = $(multifield);
        });
        
        return mNames;
    }

(function () {
    var DATA_EAEM_NESTED = "data-eaem-nested",
    	CFFW = ".coral-Form-fieldwrapper",
    	RTE_CONTAINER = "richtext-container",
    	RTE_EDITABLE = ".coral-RichText-editable";
 
    function setCheckBox($field, value){
        $field.attr("checked", value);
    }
    
    //reads multifield data from server, creates the nested composite multifields and fills them
    var addDataInFields = function () {
        $(document).on("dialog-ready", function() {
        	
        	var dialogTitle = $('.cq-dialog-header').text();
        	if(dialogTitle.includes("Carousel")) {
        		
	            var mName = $("[" + DATA_EAEM_NESTED + "]").data("name");
	 
	            if(!mName){
	                return;
	            }
	 
	            //strip ./
	            mName = mName.substring(2);
	 
	            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']"),
	                $form = $fieldSets.closest("form.foundation-form");
	 
	            var actionUrl = $form.attr("action") + ".json";
	 
	            var postProcess = function(data){
	                if(!data || !data[mName]){
	                    return;
	                }
	 
	                var mValues = data[mName], $field, name;
	 
	                if(_.isString(mValues)){
	                    mValues = [ JSON.parse(mValues) ];
	                }
	 
	                _.each(mValues, function (record, i) {
	                    if (!record) {
	                        return;
	                    }
	 
	                    if(_.isString(record)){
	                        record = JSON.parse(record);
	                    }
	 
	                    _.each(record, function(rValue, rKey){
	                        $field = $($fieldSets[i]).find("[name='./" + rKey + "']");
	
	                        if(rValue == "false") { rValue = false; }
	 
	                        if(_.isArray(rValue) && !_.isEmpty(rValue)){
	                        	
	                            fillNestedFields( $($fieldSets[i]).find(".coral-Multifield"), rValue);
	                        }else{
	                            if($field.is(':checkbox')) { 
	                            	setCheckBox($field, rValue)
	                            } else if($field.prop("type") == "hidden") {
	                                setHiddenOrRichText($field, rValue);
	                            } else {
	                            	$field.val(rValue);
	                            }
	                        }
	                    });
	                });
	            };
	 
	            //creates & fills the nested multifield with data
	            var fillNestedFields = function($multifield, valueArr){
	                _.each(valueArr, function(record, index){
	                    $multifield.find(".coral-Button.coral-Button--secondary").click();
	                })
	
	                var key = Object.keys(valueArr[0])[0];
	                var $field = $($multifield.find("[name='./" + key + "']"));
	
	                var count = 0;
	                $field.each( function() {
						var index = Math.trunc(count / 2);
	                    var value = valueArr[index][key];
						$(this).val(value);
	                    count++;
	                })
	            };
	 
	            $.ajax(actionUrl).done(postProcess);
        	}
        });
    };
 
    function setHiddenOrRichText($field, value){
        $field.val(value);

        var $rteContainer = $field.parent();

        if(!$rteContainer.hasClass(RTE_CONTAINER)){
            return;
        }

        $rteContainer.children(RTE_EDITABLE).empty().append(value);
    }
    
    var fillValue = function($field, record){
        var name = $field.attr("name");
 
        if (!name) {
            return;
        }
 
        //strip ./
        if (name.indexOf("./") == 0) {
            name = name.substring(2);
        }

        if($field.is(':checkbox')) {
            if($field.is(":checked")) {
				record[name] = true;
            } else {
                record[name] = false;
            }
        } else {
        	record[name] = $field.val();
        }
 
        //remove the field, so that individual values are not POSTed
        $field.remove();
    };
 
    //collect data from widgets in multifield and POST them to CRX as JSON
    var collectDataFromFields = function(){
        $(document).on("click", ".cq-dialog-submit", function ($multifield) {
        	var dialogTitle = $('.cq-dialog-header').text();
        	if(dialogTitle.includes("Carousel")) {
	            var $form = $(this).closest("form.foundation-form");
	 
	            var mName = $("[" + DATA_EAEM_NESTED + "]").data("name");
	            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");
	            var record,
	            	$fields, 
	            	$field;
	            
	            $fieldSets.each(function (i, fieldSet) {
	                $fields = $(fieldSet).children().children(CFFW);
	 
	                record = {};
	                record["type"] = "static";
	 
	                $fields.each(function (j, field) {
	                    $field = $(field);
                        fillValue($field.find("[name]"), record);
	                });
	 
	                if ($.isEmptyObject(record)) {
	                    return;
	                }
	
	                $('<input />').attr('type', 'hidden')
	                    .attr('name', mName)
	                    .attr('value', JSON.stringify(record))
	                    .appendTo($form);
	            });
        	}
        });
    };
 
    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });
 
    
 
    CUI.Widget.registry.register("multifield", CUI.Multifield);
})();
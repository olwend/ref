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
        	if(dialogTitle.includes("Link list")) {
        		
	            var mName = $("[" + DATA_EAEM_NESTED + "]").data("name");

                if(!mName) {
	                return;
	            }

				var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']"),
                    names = [];

				//Get unique multifield names
                $fieldSets.each(function (i, fieldSet) {
	                var dname = $(fieldSet).data("name");

                    if($.inArray(dname, names) < 0) {
						names.push(dname);
                    }
	            });

	            //strip ./
	            mName = mName.substring(2);
	 
	            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']"),
	                $form = $fieldSets.closest("form.foundation-form");
	 
	            var actionUrl = $form.attr("action") + ".json";
	 
	            var postProcess = function(data){

                    var count = 0;
                    for(var a=0; a<names.length; a++) {

                        console.log(count);
						var dName = names[a].substring(2);

                        if(!data || !data[dName]){
                            return;
                        }
         
                        var mValues = data[dName], $field, name;
         
                        if(_.isString(mValues)){
                            mValues = [ JSON.parse(mValues) ];
                        }
         
                        _.each(mValues, function (record) {
                            if (!record) {
                                return;
                            }
         
                            if(_.isString(record)){
                                record = JSON.parse(record);
                            }
         
                            _.each(record, function(rValue, rKey, i){
                                console.log(i);
								$field = $($fieldSets[count]).find("[name='./" + rKey + "']");
        
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

                            count++;
                        });

                    }
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
        	if(dialogTitle.includes("Link list")) {
	            var $form = $(this).closest("form.foundation-form");

	            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");
	            
	            var record,
	            	$fields,
	            	$fields2, 
	            	$field,
                    names = [];

                //Get unique multifield names
                $fieldSets.each(function (i, fieldSet) {
	                var dname = $(fieldSet).data("name");

                    if($.inArray(dname, names) < 0) {
						names.push(dname);
                    }
	            });

                for(var a=0; a<names.length; a++) {
                    $fieldSets.each(function (i, fieldSet) {

                        if($(fieldSet).data("name") == names[a]) {
                            console.log(names[i] + ", " + $(fieldSet).data("name"));
                            $fields = $(fieldSet).children().children(CFFW);
                            $fields2 = $(fieldSet).children().children(".coral-Form-field.coral-Checkbox");
                            
                            record = {};
             
                            $fields.each(function (j, field) {
                                $field = $(field);
                                fillValue($field.find("[name]"), record);
                            });
                            
                            $fields2.each(function (j, field) {
                                $field = $(field);
                                fillValue($field.find("[name]"), record);
                            });
             
                            if ($.isEmptyObject(record)) {
                                return;
                            }
            
                            $('<input />').attr('type', 'hidden')
                                .attr('name', names[a])
                                .attr('value', JSON.stringify(record))
                                .appendTo($form);
                        }
                    });
                }
        	}
        });
    };
 
    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });

    CUI.Widget.registry.register("multifield", CUI.Multifield);
})();
(function () {
    var DATA_EAEM_NESTED = "data-eaem-nested";
    var CFFW = ".coral-Form-fieldwrapper";
 
    function setCheckBox($field, value){
        $field.attr("checked", value);
    }
    
    //reads multifield data from server, creates the nested composite multifields and fills them
    var addDataInFields = function () {
        $(document).on("dialog-ready", function() {
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
        });
    };
 
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
 
    //for getting the nested multifield data as js objects
    var getRecordFromMultiField = function($multifield){
        var $fieldSets = $multifield.find("[class='coral-Form-fieldset']");
 
        var records = [], record, $fields, name;
 
        $fieldSets.each(function (i, fieldSet) {
            $fields = $(fieldSet).find("[name]");
 
            record = {};
 
            $fields.each(function (j, field) {
                fillValue($(field), record);
            });
 
            //We don't need this property and I don't know where it comes from
            delete record['time@Delete'];
            
            if(!$.isEmptyObject(record)){
                records.push(record)
            }
        });
 
        return records;
    };
 
    //collect data from widgets in multifield and POST them to CRX as JSON
    var collectDataFromFields = function(){
        $(document).on("click", ".cq-dialog-submit", function ($multifield) {
        	var dialogTitle = $('.cq-dialog-header').text();
        	if(dialogTitle.includes("Event Detail")) {
	            var $form = $(this).closest("form.foundation-form");
	 
	            var mName = $("[" + DATA_EAEM_NESTED + "]").data("name");
	            var $fieldSets = $("[" + DATA_EAEM_NESTED + "][class='coral-Form-fieldset']");
	            var record,
	            	$fields, 
	            	$field, 
	            	name, 
	            	$nestedMultiField;
	            
	            var durationsRecurrence = [],
	            	allDayRecurrence = [],
	            	datesRecurrence = "",
	            	timesRecurrence = "[",
	            	soldOutRecurrence = "[",
	            	dateCount = 0;
	 
	            var weekday = new Array(7);
	            weekday[0] =  "Sun";
	            weekday[1] = "Mon";
	            weekday[2] = "Tue";
	            weekday[3] = "Wed";
	            weekday[4] = "Thu";
	            weekday[5] = "Fri";
	            weekday[6] = "Sat";
	            
	            var month = new Array();
	            month[0] = "Jan";
	            month[1] = "Feb";
	            month[2] = "Mar";
	            month[3] = "Apr";
	            month[4] = "May";
	            month[5] = "Jun";
	            month[6] = "Jul";
	            month[7] = "Aug";
	            month[8] = "Sep";
	            month[9] = "Oct";
	            month[10] = "Nov";
	            month[11] = "Dec";
	            
	            $fieldSets.each(function (i, fieldSet) {
	                $fields = $(fieldSet).children().children(CFFW);
	 
	                record = {};
	 
	                $fields.each(function (j, field) {
	                    $field = $(field);
	                    
	                    //may be a nested multifield
	                    $nestedMultiField = $field.find(".coral-Multifield");
	                    //$nestedMultiField = $field.find("[data-init='multifield']");
	 
	                    if($nestedMultiField.length == 0) {
	                        fillValue($field.find("[name]"), record);
	                    } else {
	                        name = $nestedMultiField.find("[class='coral-Form-fieldset']").data("name");
	 
	                        if(!name) {
	                            return;
	                        }
	 
	                        //strip ./
	                        name = name.substring(2);
	
	                        record[name] = getRecordFromMultiField($nestedMultiField);
	                        console.log(record[name]);
	                    }
	                });
	 
	                if ($.isEmptyObject(record)) {
	                    return;
	                }
	                
	                //Add old fields (blame Ensemble)
	                //Durations
	                durationsRecurrence.push(parseInt(record.eventDuration));
	                
	                //All day
	                var allDay = record.allDay;
	                if(allDay === "true") { allDay = true };
	                if(allDay === "false") { allDay = false };
	                
	                allDayRecurrence.push(allDay);
	                
	                //Dates
	                var d = new Date(record.dateTime);
	
	                if(dateCount == 0) {
	                	var dateString = weekday[d.getDay()] + " " + month[d.getMonth()] + " " + d.getDate() + " " + d.getFullYear() + " " + d.toTimeString();
	                	datesRecurrence = datesRecurrence + dateString + dateCount;
	                } else {
	                	var dateString = weekday[d.getDay()] + " " + month[d.getMonth()] + " " + d.getDate() + " " + d.getFullYear() + " " + d.toTimeString();
	                	datesRecurrence = datesRecurrence + "," + dateString + dateCount;
	                }
	
	                //Times
	                var times = record.times,
	                	subTimesString = "[",
                        subSoldOutRecurrence = "[";

	                if(allDay == false) {
		                for(var i=0; i<times.length; i++) {
		                	if(i > 0) {
		                		subTimesString = subTimesString + ',"' + times[i].time + '"';
                                subSoldOutRecurrence = subSoldOutRecurrence + ',false';
		                	} else {
		                		subTimesString = subTimesString + '"' + times[i].time + '"';
		                		subSoldOutRecurrence = subSoldOutRecurrence + 'false';
		                	}
		                }
	                } else {
	                	subSoldOutRecurrence = subSoldOutRecurrence + 'false';
	                }
	                
	                subTimesString = subTimesString + ']';
	                subSoldOutRecurrence = subSoldOutRecurrence + ']'; 

	                if(dateCount == 0) {
	                	timesRecurrence = timesRecurrence + subTimesString;
	                	soldOutRecurrence = soldOutRecurrence + subSoldOutRecurrence;
	                } else {
	                	timesRecurrence = timesRecurrence + "," + subTimesString;
	                	soldOutRecurrence = soldOutRecurrence + ', ' + subSoldOutRecurrence;
	                }
	
	                dateCount++;
	
	                //add the record JSON in a hidden field as string
	                $('<input />').attr('type', 'hidden')
	                    .attr('name', mName)
	                    .attr('value', JSON.stringify(record))
	                    .appendTo($form);
	
	            });
	            
	            $('<input />').attr('type', 'hidden')
	            .attr('name', "./jcr:durationsRecurrence")
	            .attr('value', JSON.stringify(durationsRecurrence))
	            .appendTo($form);
	
	            $('<input />').attr('type', 'hidden')
	            .attr('name', "./jcr:datesRecurrence")
	            .attr('value', datesRecurrence)
	            .appendTo($form);
	            
	            timesRecurrence = timesRecurrence + "]";

	            $('<input />').attr('type', 'hidden')
	            .attr('name', "./jcr:timesRecurrence")
	            .attr('value', timesRecurrence)
	            .appendTo($form);

	            $('<input />').attr('type', 'hidden')
	            .attr('name', "./jcr:allDayRecurrence")
	            .attr('value', JSON.stringify(allDayRecurrence))
	            .appendTo($form);

				soldOutRecurrence = soldOutRecurrence + ']';

                var $field = $form.find("[name='./jcr:soldOut']");
                //If there's already something in this field (i.e. the page has already been
                //initialised - don't add the array full of 'falses'
                if($field.val() == undefined || $field.val() == "") {
                	$field.val(soldOutRecurrence);
                }
        	}
        });
    };
 
    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });
 
    //extend otb multifield for adjusting event propagation when there are nested multifields
    //for working around the nested multifield add and reorder
    CUI.Multifield = new Class({
        toString: "Multifield",
        extend: CUI.Multifield,
 
        construct: function (options) {
            this.script = this.$element.find(".js-coral-Multifield-input-template:last");
        },
 
        _addListeners: function () {
            this.superClass._addListeners.call(this);
 
            //otb coral event handler is added on selector .js-coral-Multifield-add
            //any nested multifield add click events are propagated to the parent multifield
            //to prevent adding a new composite field in both nested multifield and parent multifield
            //when user clicks on add of nested multifield, stop the event propagation to parent multifield
            this.$element.on("click", ".coral-Button.coral-Button--secondary", function (e) {
                e.stopPropagation();
            });
 
            this.$element.on("drop", function (e) {
                e.stopPropagation();
            });
        }
    });
 
    CUI.Widget.registry.register("multifield", CUI.Multifield);
})();


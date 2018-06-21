(function () {
    var DATA_EAEM_NESTED = "data-eaem-nested",
        CFFW = ".coral-Form-fieldwrapper",
        THUMBNAIL_IMG_CLASS = "cq-FileUpload-thumbnail-img",
        SEP_SUFFIX = "-",
        RTE_CONTAINER = "richtext-container",
        RTE_EDITABLE = ".coral-RichText-editable",
        SEL_FILE_UPLOAD = ".coral-FileUpload",
        SEL_FILE_REFERENCE = ".cq-FileUpload-filereference",
        SEL_FILE_NAME = ".cq-FileUpload-filename",
        SEL_FILE_MOVEFROM = ".cq-FileUpload-filemovefrom",
        componentNode = "";

    function getStringBeforeAtSign(str){
        if(_.isEmpty(str)){
            return str;
        }

        if(str.indexOf("@") != -1){
            str = str.substring(0, str.indexOf("@"));
        }

        return str;
    }

    function getStringAfterAtSign(str){
        if(_.isEmpty(str)){
            return str;
        }

        return (str.indexOf("@") != -1) ? str.substring(str.indexOf("@")) : "";
    }

    function getStringAfterLastSlash(str){
        if(!str || (str.indexOf("/") == -1)){
            return "";
        }

        return str.substr(str.lastIndexOf("/") + 1);
    }

    function getStringBeforeLastSlash(str){
        if(!str || (str.indexOf("/") == -1)){
            return "";
        }

        return str.substr(0, str.lastIndexOf("/"));
    }

    function removeFirstDot(str){
        if(str.indexOf(".") != 0){
            return str;
        }

        return str.substr(1);
    }

    function modifyJcrContent(url){
        return url.replace(new RegExp("^" + Granite.HTTP.getContextPath()), "")
                .replace("_jcr_content", "jcr:content");
    }

    function isSelectOne($field) {
        return !_.isEmpty($field) && ($field.prop("type") === "select-one");
    }

    function setSelectOne($field, value) {
        var select = $field.closest(".coral-Select").data("select");

        if (select) {
            select.setValue(value);
        }
    }

    function isCheckbox($field) {
        return !_.isEmpty($field) && ($field.prop("type") === "checkbox");
    }

    function setCheckBox($field, value) {
        $field.prop("checked", $field.attr("value") === value);
    }

    function setHiddenOrRichText($field, value){
        $field.val(value);
 
        var $rteContainer = $field.parent();
 
        if(!$rteContainer.hasClass(RTE_CONTAINER)){
            return;
        }
 
        $rteContainer.children(RTE_EDITABLE).empty().append(value);
    }
    
    function setWidgetValue($field, value) {
        if (_.isEmpty($field)) {
            return;
        }

        if (isSelectOne($field)) {
            setSelectOne($field, value);
        } else if (isCheckbox($field)) {
            setCheckBox($field, value);
        } else if($field.prop("type") == "hidden") {
            setHiddenOrRichText($field, value);
        } else {
            $field.val(value);
        }
    }

    /**
     * Removes multifield number suffix and returns just the fileRefName
     * Input: paintingRef-1, Output: paintingRef
     *
     * @param fileRefName
     * @returns {*}
     */
    function getJustName(fileRefName){
        if(!fileRefName || (fileRefName.indexOf(SEP_SUFFIX) == -1)){
            return fileRefName;
        }

        var value = fileRefName.substring(0, fileRefName.lastIndexOf(SEP_SUFFIX));

        if(fileRefName.lastIndexOf(SEP_SUFFIX) + SEP_SUFFIX.length + 1 == fileRefName.length){
            return value;
        }

        return value + fileRefName.substring(fileRefName.lastIndexOf(SEP_SUFFIX) + SEP_SUFFIX.length + 1);
    }

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

    function buildMultiField(data, $multifield, mName){
        if(_.isEmpty(mName) || _.isEmpty(data)){
            return;
        }
console.log(data);
		var ttt = 0;
        _.each(data, function(value, key){
            if(key == "jcr:primaryType"){
                return;
            }

            //Do something with the data in all dem rows
			var componentType = value['components'];

            if(componentType == "imageandtext") {
				value['caption'] = value['par2']['row2cells13']['par']['caption']['text'];
                value['text'] = value['par2']['row2cells13']['par2']['text']['text'];
                //row layout
            }

            if(componentType == "text") {
				//text
            }

            if(componentType == "twoimages") {
                value['caption'] = value['par2']['row2cellsequal']['par']['caption']['text'];
                value['caption2'] = value['par2']['row2cellsequal']['par2']['caption']['text'];
            }

            if(componentType == "pulloutimage") {
				//caption
                //text
                //layout
            }

            $multifield.find(".js-coral-Multifield-add").click();
var kkk = 0;
            _.each(value, function(fValue, fKey){
                if(fKey == "jcr:primaryType" || _.isObject(fValue)){
                    return;
                }

                //var $field = $multifield.find("[name='./" + fKey + "']").last();
                var $field = $multifield.find("[name='./" + fKey + "']:eq(" + ttt + ")");

                if(_.isEmpty($field)){
                    return;
                }

                setWidgetValue($field, fValue);
                kkk = kkk + 1;
            });

            ttt = ttt+1;
        });
    }

    function buildImageField($multifield, mName){
        var vLength = $multifield.find(".coral-FileUpload").length;
        $multifield.find(".coral-FileUpload:eq(" + (vLength-2) + ")").each(function () {
            var $element = $(this), widget = $element.data("fileUpload"),
                resourceURL = $element.parents("form.cq-dialog").attr("action"),
                counter = $multifield.find(SEL_FILE_UPLOAD).length/2;

            if (!widget) {
                return;
            }

            var fuf = new Granite.FileUploadField(widget, resourceURL);
            addThumbnail(fuf, mName, counter);
        });
        $multifield.find(".coral-FileUpload:eq(" + (vLength-1) + ")").each(function () {
            var $element = $(this), widget = $element.data("fileUpload"),
                resourceURL = $element.parents("form.cq-dialog").attr("action"),
                counter = $multifield.find(SEL_FILE_UPLOAD).length/2;

            if (!widget) {
                return;
            }

            var fuf = new Granite.FileUploadField(widget, resourceURL);
            addThumbnail(fuf, mName, counter);
        });
    }

    function addThumbnail(imageField, mName, counter){
        var $element = imageField.widget.$element,
            $thumbnail = $element.find("." + THUMBNAIL_IMG_CLASS),
            thumbnailDom;

        $thumbnail.empty();

        $.ajax({
            url: imageField.resourceURL + ".2.json",
            cache: false
        }).done(handler);

        function handler(data){
            var fName = getJustName(getStringAfterLastSlash(imageField.fieldNames.fileName)),
                fRef = getJustName(getStringAfterLastSlash(imageField.fieldNames.fileReference)),
                rowString = "row" + counter;

            if(isFileNotFilled(data, rowString, fRef)){
                return;
            }

            var fileName = data[mName][rowString][fName],
                fileRef = data[mName][rowString][fRef];

            if (!fileRef) {
                return;
            }

            if (imageField._hasImageMimeType()) {
                imageField._appendThumbnail(fileRef, $thumbnail);
            }

            var $fileName = $element.find("[name=\"" + imageField.fieldNames.fileName + "\"]"),
                $fileRef = $element.find("[name=\"" + imageField.fieldNames.fileReference + "\"]");

            $fileRef.val(fileRef);
            $fileName.val(fileName);
        }

        function isFileNotFilled(data, rowString, fRef){
            return _.isEmpty(data[mName])
                    || _.isEmpty(data[mName][rowString])
                    || _.isEmpty(data[mName][rowString][fRef])
        }
    }

    //reads multifield data from server, creates the nested composite multifields and fills them
    function addDataInFields() {
        $(document).on("dialog-ready", function() {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            workaroundFileInputPositioning($multifields);

            var mNames = getMultiFieldNames($multifields),
                $form = $(".cq-dialog"),
                actionUrl = $form.attr("action") + ".infinity.json";

            $.ajax(actionUrl).done(postProcess);

            function postProcess(data){
                _.each(mNames, function($multifield, mName){
                    $multifield.on("click", ".js-coral-Multifield-add", function () {
                        buildImageField($multifield, mName);
                    });

                    buildMultiField(data[mName], $multifield, mName);
                });
            }

        });
    }

    function workaroundFileInputPositioning($multifields){
        //to workaround the .coral-FileUpload-input positioning issue
        $multifields.find(".js-coral-Multifield-add")
                    .css("position" ,"relative");
    }

    function collectImageFields($form, $fieldSet, counter){
        var $fields = $fieldSet.children().find(CFFW).not(function(index, ele){
            return $(ele).find(SEL_FILE_UPLOAD).length == 0;
        });

        $fields.each(function (j, field) {
            var $field = $(field),
                $widget = $field.find(SEL_FILE_UPLOAD).data("fileUpload");

            if(!$widget){
                return;
            }
console.log(componentNode);
            var prefix = $fieldSet.data("name") + "/row" + (counter + 1) + "/",

                $fileRef = $widget.$element.find(SEL_FILE_REFERENCE),
                refPath = prefix + getJustName($fileRef.attr("name")),

                $fileName = $widget.$element.find(SEL_FILE_NAME),
                namePath = prefix + getJustName($fileName.attr("name")),

                $fileMoveRef = $widget.$element.find(SEL_FILE_MOVEFROM),
                moveSuffix =   $widget.inputElement.attr("name") + "/" + new Date().getTime()
                                        + SEP_SUFFIX + $fileName.val(),
                moveFromPath =  moveSuffix + "@MoveFrom";

            $('<input />').attr('type', 'hidden').attr('name', refPath)
                .attr('value', $fileRef.val() || ($form.attr("action") + removeFirstDot(moveSuffix)))
                .appendTo($form);

            $('<input />').attr('type', 'hidden').attr('name', namePath)
                .attr('value', $fileName.val()).appendTo($form);

            $('<input />').attr('type', 'hidden').attr('name', moveFromPath)
                .attr('value', modifyJcrContent($fileMoveRef.val())).appendTo($form);

            if(componentNode == "imageandtext") {
				$('<input />').attr('type', 'hidden')
                    .attr('name', $fieldSet.data("name") + "/row" + (counter + 1) + "/par2/imagerow/par2/imageandtext/fileReference")
                    .attr('value', $fileRef.val())
                    .appendTo($form);
            } else if(componentNode == "twoimages") {
				var imageNumber = refPath.substr(refPath.length - 1);

                if(imageNumber == 1) {
                    $('<input />').attr('type', 'hidden')
                        .attr('name', $fieldSet.data("name") + "/row" + (counter + 1) + "/par2/row2cellsequal/par/image/fileReference")
                        .attr('value', $fileRef.val())
                        .appendTo($form);
                }

                if(imageNumber == 2) {
                    $('<input />').attr('type', 'hidden')
                        .attr('name', $fieldSet.data("name") + "/row" + (counter + 1) + "/par2/row2cellsequal/par2/image/fileReference")
                        .attr('value', $fileRef.val())
                        .appendTo($form);
                }
            } else if(componentNode == "pulloutimage") {
				$('<input />').attr('type', 'hidden')
                    .attr('name', $fieldSet.data("name") + "/row" + (counter + 1) + "/par2/row2cells13/par/image/fileReference")
                    .attr('value', $fileRef.val())
                    .appendTo($form);
            }	

            $field.remove();
        });
    }

    function collectNonImageFields($form, $fieldSet, counter){
        var $fields = $fieldSet.children().find(CFFW).not(function(index, ele){
            return $(ele).find(SEL_FILE_UPLOAD).length > 0;
        });

        $fields.each(function (j, field) {
            fillValue($form, $fieldSet.data("name"), $(field).find("[name]"), (counter + 1));
        });
    }

    function fillValue($form, fieldSetName, $field, counter){
        var name = $field.attr("name"), value;

        if (!name) {
            return;
        }

        //strip ./
        if (name.indexOf("./") == 0) {
            name = name.substring(2);
        }

        value = $field.val();

        if (isCheckbox($field)) {
            value = $field.prop("checked") ? $field.val() : "";
        }

        //remove the field, so that individual values are not POSTed
        $field.remove();

        $('<input />').attr('type', 'hidden')
            .attr('name', fieldSetName + "/row" + counter + "/" + name)
            .attr('value', value)
            .appendTo($form);

        if(name == "components") {
			componentNode = value;

            $('<input />').attr('type', 'hidden')
             .attr('name', fieldSetName + "/row" + counter + "/par2/sling:resourceType")
                .attr('value', "foundation/components/parsys")
                .appendTo($form);

			if(value == "imageandtext") {
				$('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/imagerow/par2/imageandtext/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/imagepage/image")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/imagerow/par2/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);


                $('<input />').attr('type', 'hidden')
                    .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
	                .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/textIsRich")
	                .attr('value', "true")
	                .appendTo($form);
            }

            if(value == "imageandtext" || value == "text") {
				$('<input />').attr('type', 'hidden')
                    .attr('name', fieldSetName + "/row" + counter + "/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/layout/herosection")
                    .appendTo($form);

				$('<input />').attr('type', 'hidden')
                .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/layout/row2cells13")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);
            }

            if(value == "text") {
                $('<input />').attr('type', 'hidden')
                    .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);
            }

            if(value == "twoimages") {
				$('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/layout/herosection")
                    .appendTo($form);

				$('<input />').attr('type', 'hidden')
                .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/sling:resourceType")
                   .attr('value', "nhmwww/components/functional/layout/row2cellsequals")
                   .appendTo($form);
				
                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par2/image/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/imagepage/image")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par/image/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/imagepage/image")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par/caption/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par2/caption/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);
            }

            if(value == "pulloutimage") {
                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/layout/herosection")
                    .appendTo($form);

				$('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/layout/row2cells13")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/sling:resourceType")
                    .attr('value', "foundation/components/parsys")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/image/sling:resourceType")
                    .attr('value', "nhmwww/components/functional/imagepage/image")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);

                $('<input />').attr('type', 'hidden')
                 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/textIsRich")
                    .attr('value', "true")
                    .appendTo($form);
            } 
        } else if(name == "rowlayout" && componentNode == "imageandtext") {
            $('<input />').attr('type', 'hidden')
            	.attr('name', fieldSetName + "/row" + counter + "/par2/imagerow/sling:resourceType")
	            .attr('value', "nhmwww/components/functional/layout/" + value)
	            .appendTo($form);
        } else {
            if(componentNode == "imageandtext") {
    	        if(name == "caption") {
					$('<input />').attr('type', 'hidden')
                    	.attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/text")
                        .attr('value', value)
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                   	 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/sling:resourceType")
                        .attr('value', 'nhmwww/components/functional/imagepage/text')
                        .appendTo($form);
                }
            
                if(name == "text") {
                    $('<input />').attr('type', 'hidden')
                    	.attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/text")
                        .attr('value', value)
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                   	 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/sling:resourceType")
                        .attr('value', 'nhmwww/components/functional/imagepage/text')
                        .appendTo($form);
                }
            }

            if(componentNode == "text") {
                if(name == "text") {
                    $('<input />').attr('type', 'hidden')
                    	.attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/text")
                        .attr('value', value)
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                   	 .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/sling:resourceType")
                        .attr('value', 'nhmwww/components/functional/imagepage/text')
                        .appendTo($form);
                }
            }

            if(componentNode == "twoimages") {
                if(name == "caption") {
					$('<input />').attr('type', 'hidden')
                     .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par/caption/text")
                        .attr('value', value)
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                    .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par/caption/sling:resourceType")
                        .attr('value', 'nhmwww/components/functional/imagepage/text')
                        .appendTo($form);
                }

                if(name == "caption2") {
					$('<input />').attr('type', 'hidden')
                     .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par2/caption/text")
                        .attr('value', value)
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                    .attr('name', fieldSetName + "/row" + counter + "/par2/row2cellsequal/par2/caption/sling:resourceType")
                        .attr('value', 'nhmwww/components/functional/imagepage/text')
                        .appendTo($form);
                }
            }

            if(componentNode == "pulloutimage") {
                if(name == "caption") {
					$('<input />').attr('type', 'hidden')
                        .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/sling:resourceType")
                        .attr('value', "nhmwww/components/functional/imagepage/text")
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                        .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par/caption/text")
                        .attr('value', value)
                        .appendTo($form);
                }

                if(name == "text") {
					$('<input />').attr('type', 'hidden')
                        .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/sling:resourceType")
                        .attr('value', "nhmwww/components/functional/imagepage/text")
                        .appendTo($form);

                    $('<input />').attr('type', 'hidden')
                        .attr('name', fieldSetName + "/row" + counter + "/par2/row2cells13/par2/text/text")
                        .attr('value', value)
                        .appendTo($form);
                }
            }
        }
    }

    //collect data from widgets in multifield and POST them to CRX
    function collectDataFromFields(){
        $(document).on("click", ".cq-dialog-submit", function () {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            var $form = $(this).closest("form.foundation-form"),
                $fieldSets;

            $multifields.each(function(i, multifield){
                $fieldSets = $(multifield).find("[class='coral-Form-fieldset']");

                $fieldSets.each(function (counter, fieldSet) {
                    collectNonImageFields($form, $(fieldSet), counter);

                    collectImageFields($form, $(fieldSet), counter);
                });
            });
        });
    }

    function overrideGranite_refreshThumbnail(){
        var prototype = Granite.FileUploadField.prototype,
            ootbFunc = prototype._refreshThumbnail;

        prototype._refreshThumbnail = function() {
            var $imageMulti = this.widget.$element.closest("[" + DATA_EAEM_NESTED + "]");

            if (!_.isEmpty($imageMulti)) {
                return;
            }

            return ootbFunc.call(this);
        }
    }

    function overrideGranite_computeFieldNames(){
        var prototype = Granite.FileUploadField.prototype,
            ootbFunc = prototype._computeFieldNames;

        prototype._computeFieldNames = function(){
            ootbFunc.call(this);

            var $imageMulti = this.widget.$element.closest("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($imageMulti)){
                return;
            }

            var fieldNames = {},
                fileFieldName = $imageMulti.find("input[type=file]").attr("name"),
                counter = $imageMulti.find(SEL_FILE_UPLOAD).length;

            _.each(this.fieldNames, function(value, key){
                if(value.indexOf("./jcr:") == 0){
                    fieldNames[key] = value;
                }else if(key == "tempFileName" || key == "tempFileDelete"){
                    value = value.substring(0, value.indexOf(".sftmp")) + getStringAfterAtSign(value);
                    fieldNames[key] = fileFieldName + removeFirstDot(getStringBeforeAtSign(value))
                                        + SEP_SUFFIX + counter + ".sftmp" + getStringAfterAtSign(value);
                }else{
                    fieldNames[key] = getStringBeforeAtSign(value) + SEP_SUFFIX
                                                    + counter + getStringAfterAtSign(value);
                }
            });

            this.fieldNames = fieldNames;

            this._tempFilePath = getStringBeforeLastSlash(this._tempFilePath);
            this._tempFilePath = getStringBeforeLastSlash(this._tempFilePath) + removeFirstDot(fieldNames.tempFileName);
        }
    }

    function performOverrides(){
        overrideGranite_computeFieldNames();
        overrideGranite_refreshThumbnail();
    }

    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });

    performOverrides();
})();
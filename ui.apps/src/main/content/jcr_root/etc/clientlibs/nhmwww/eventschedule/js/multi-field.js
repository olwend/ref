CQ.form.CustomMultiField = CQ.Ext.extend(CQ.form.CompositeField, {

    hiddenField: null,

    soldOutArray: null,
    timesArray: null,
    datesArray: null,

    constructor: function (field, config) {

        var path = field.ownerCt.path;
		var pathParts=path.split('/');
        pathParts.pop();
        pathParts.pop();
        pathParts.pop();
        pathParts.pop();
        
        var jcrContentPath=pathParts.join('/');

        var response = CQ.utils.HTTP.get(jcrContentPath +'.json');
        var responseText = response.responseText;

        //Get sold out array
		var soldOutMatch = responseText.match("jcr:soldOut\":\"([a-z\\[,\\] ]*)");

        this.soldOutArray = [];
        if(soldOutMatch[0] != null) {
            var s = soldOutMatch[0];
            this.soldOutArray = s.replace("jcr:soldOut\":\"","").split("],[");
        }

        for(var i=0; i<this.soldOutArray.length; i++) {
            this.soldOutArray[i] = this.soldOutArray[i].replace(new RegExp('\\[|\\]|"| ', 'g'), '').split(",");
        }

		//Get times array
        var timesMatch = responseText.match("jcr:timesRecurrence\":\"([\\\\a-z0-9:\\[,\\] \"]*\",)");

        this.timesArray = [];
        if(timesMatch[0] != null) {
            var s = timesMatch[0];
            this.timesArray = s.replace("jcr:timesRecurrence\":\"","").split("],[");
        }

        for(var i=0; i<this.timesArray.length; i++) {
            this.timesArray[i] = this.timesArray[i].replace(new RegExp('\\[|\\]|"| |\\\\', 'g'), '').split(",");
        }

        //Get dates array
        var datesMatch = responseText.match("jcr:datesRecurrence\":\"([a-zA-Z0-9:+\\( \\),]*)");

		this.datesArray = [];
		if(datesMatch[0] != null) {
            var s = datesMatch[0];
            this.datesArray = s.replace("jcr:datesRecurrence\":\"","").split(",");
        }

        config = config || {};
        var defaults = {
            "border": true,
            "labelWidth": 75,
            "layout": "form"
        }; 
        config = CQ.Util.applyDefaults(config, defaults);
        CQ.form.CustomMultiField.superclass.constructor.call(this, config);
    },

    initComponent: function () {

        CQ.form.CustomMultiField.superclass.initComponent.call(this);

        // Hidden field
        this.hiddenField = new CQ.Ext.form.Hidden({
             name: this.name
        });
        this.add(this.hiddenField);

        for(var i=0; i<this.soldOutArray.length; i++) {

            var dateString = this.datesArray[i];

			labelTest = new CQ.Ext.form.Label({
            	text: dateString
            });
            this.add(labelTest);

            for(var j=0; j<this.soldOutArray[i].length; j++) {
                var clsNum = 'customwidget-' + (i+1);

                var onOff = null;

                if(this.soldOutArray[i][j] == "true") {
					onOff = true;
                } else {
					onOff = false;
                }

                // Link openInNewWindow
                window["soldOut" + i] = new CQ.Ext.form.Checkbox({
                    cls: clsNum,
                    boxLabel: this.timesArray[i][j],
                    checked: onOff,
                    listeners: {
                        change: {
                            scope: this,
                            fn: this.updateHidden
                        },
                        check: {
                            scope: this,
                            fn: this.updateHidden
                        }
                    }
                });
                this.add(window["soldOut" + i]);
            }
    	}
    },

    setValue: function (value) {
        var link = JSON.parse(value);
    },

    getValue: function () {
        return this.getRawValue();
    },

    getRawValue: function () { 
        var link = {

        }; 
        return JSON.stringify(link);
    },

    updateHidden: function () {
         this.hiddenField.setValue(this.getValue());
    }
});

CQ.Ext.reg("CustomMultiField", CQ.form.CustomMultiField);
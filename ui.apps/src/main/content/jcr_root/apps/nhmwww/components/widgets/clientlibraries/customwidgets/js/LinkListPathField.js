/* Ensure the BS object exists in the window scope. */
window.hasOwnProperty = function(obj){
	return (this[obj]) ? true : false;
};

if (!window.hasOwnProperty("NHM")) {
    window.NHM = {};
}

/**
 * Object used to create the JSON Data stored by the Widgets in the repository.
 */
NHM.LinkListPathFieldData = CQ.Ext.extend(CQ.Static, {
	type: null,
	text: null,
	path: null,
    date: null,

    constructor: function(type, text, path, date) {
    	this.type = type;
    	if (text != null) {
    		this.text = text;
    	}

    	if (path != null) {
    		this.path = path;
    	}

    	if (date != null) {
    		this.date = date;
    	}
    }
});

/**
 * @class NHM.LinkListPathField
 * @extends CQ.form.CompositeField
 * This is a custom widget based on {@link CQ.form.CompositeField}.
 * @constructor
 * Creates a new LinkListPathField Widget.
 * @param {Object} config The config object
 */
NHM.LinkListPathField = CQ.Ext.extend(CQ.form.CompositeField, {

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */
    typeLabel: null,

    /**
     * @private
     * @type CQ.form.Selection
     */
    typeSelection: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */
    textLabel: null,

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    textField: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */
    pathLabel: null,

    /**
     * @private
     * @type CQ.Ext.form.PathField
     */
    pathField: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */
    dateLabel: null,

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    dateField: null,

    constructor: function(config) {
        config = config || { };
        var defaults = {
            "border": false,
            "layout": {
            	type: "table",
            	columns: 6
            }
        };
        config = CQ.Util.applyDefaults(config, defaults);
        NHM.LinkListPathField.superclass.constructor.call(this, config);
    },

    // overriding CQ.Ext.Component#initComponent
    initComponent: function() {
    	NHM.LinkListPathField.superclass.initComponent.call(this);
        this.typeLabel = new CQ.Ext.form.Label({
            cls:"custommultifield-typelabel",
            rowspan: 2,
            text:"Type:",
            style:"font-size: 12px; font-family: Arial, Verdana, sans-serif; vertical-align:text-bottom; padding-bottom:0px; padding-right:5px; padding-left:10px;"
        });
        this.add(this.typeLabel);

        this.typeSelection = new CQ.form.Selection({
            cls:"custommultifield-typeselection",
            defaultValue: "textandpath",
            value: "textandpath",
            rowspan: 2,
            type: "select",
            width: "155px",
            options: [{
            	text: "Text and Path",
            	value: "textandpath"
            },{
            	text: "Date",
            	value: "date"
            }],
            listeners: {
            	afterlayout: {
            		scope:this,
            		fn: this.selectionCreated
            	},
                selectionchanged: {
                    scope:this,
                    fn:this.selectionChanged
                }
            }
        });
        this.add(this.typeSelection);

        this.textLabel = new CQ.Ext.form.Label({
            cls:"custommultifield-textlabel",
            text:"Text:",
            style:"font-size: 12px; font-family: Arial, Verdana, sans-serif; vertical-align:text-bottom; padding-bottom:0px; padding-right:5px; padding-left:10px;"
        });
        this.add(this.textLabel);

        this.textField = new CQ.Ext.form.TextField({
            cls:"custommultifield-textfield",
            width: "100px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.textField);

        this.pathLabel = new CQ.Ext.form.Label({
            cls:"custommultifield-pathlabel",
            text:"Path:",
            style:"font-size: 12px; font-family: Arial, Verdana, sans-serif; vertical-align:text-bottom; padding-bottom:0px; padding-right:5px; padding-left:10px;"
        });
        this.add(this.pathLabel);

        this.pathField = new CQ.form.PathField({
            cls:"custommultifield-pathfield",
            width: "250px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.pathField);

        this.dateLabel = new CQ.Ext.form.Label({
            cls:"custommultifield-datelabel",
            hidden: true,
            text:"Date: ",
            style:"float: right; font-size: 12px; font-family: Arial, Verdana, sans-serif; vertical-align:text-bottom; padding-bottom:0px; padding-right:5px; padding-left:10px;"
        });
        this.add(this.dateLabel);

        this.dateField = new CQ.Ext.form.DateField({
        	colspan: 3,
            cls:"custommultifield-datefield",
            hidden: true,
            width: "208px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.dateField);

        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);
        
        this.setFieldsValue(value);
    },

    // overriding CQ.form.CompositeField#processPath
    processPath: function(path) {
    	this.hiddenField.processPath(path);
    },

    // overriding CQ.form.CompositeField#processRecord
    processRecord: function(record, path) {
    	this.hiddenField.processRecord(record, path);
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
    	var data = CQ.Ext.util.JSON.decode(value);

        this.hiddenField.setValue(value);

    	this.typeSelection.setValue(data.type);

    	if (data.type == "date") {
    		this.dateField.setValue(data.date);
    	} else {
    		this.textField.setValue(data.text);
            this.pathField.setValue(data.path);
    	}

        this.showHideDate();
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        return this.getRawValue();
    },
    setFieldsValue: function(value) {
        /*var data = CQ.Ext.util.JSON.decode(value);
        console.log("set value inform: " + data);
        this.hiddenField.setValue(value);
        this.hiddenType.setValue(data.type);
        //console.log("Item data.type:", data.type);
        this.item.setValue(data.item);
        this.item.setType(data.type);
        this.itemMobile.setValue(data.itemMobile);
        //console.log("Item item.getType():", this.item.getType());
        this.itemURL.setValue(data.itemURL);
        this.itemHeading.setValue(data.itemHeading);
        this.itemCaption.setValue(data.itemCaption);*/
       
    },

    // overriding CQ.form.CompositeField#getRawValue
    getRawValue: function() {
    	var data = null;
    	
    	if (this.typeSelection.getValue() == "date") {
    		data = new NHM.LinkListPathFieldData(this.typeSelection.getValue(), null, null, this.dateField.getValue());
    	} else {
    		data = new NHM.LinkListPathFieldData(this.typeSelection.getValue(), this.textField.getValue(), this.pathField.getValue(), null);
    	}
    	
    	return CQ.Ext.util.JSON.encode(data);
    },

    // private
    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    },

    // private
    selectionChanged: function() {
    	this.showHideDate();
        this.updateHidden();
    },

    // private
    showHideDate: function() {
    	if (this.typeSelection != null) {
	    	var value = this.typeSelection.getValue();
	    	if (value == "date") {
	    		this.textLabel.hide();
	    		this.textField.hide();
	    		this.pathLabel.hide();
	    		this.pathField.hide();
	    		this.dateLabel.show();
	    		this.dateField.show();
	    	} else {
	    		this.dateLabel.hide();
	    		this.dateField.hide();
	    		this.textLabel.show();
	    		this.textField.show();
	    		this.pathLabel.show();
	    		this.pathField.show();
	    	}
    	}
    },

    // private
    // Used to fixed the issue if the element is included as Multifield's Field Config.
    selectionCreated: function(selection, layout) {
    	var el = selection.getEl();
        var inputs = el.query('input');

        for (var i = 0; i < inputs.length; i++) {
        	var input = $(inputs[i]);
        	input.css({ width: "130px"});
        }
    }

});

// register xtype
CQ.Ext.reg('custommultifield', NHM.LinkListPathField);
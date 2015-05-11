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
NHM.CustomMultifieldData = CQ.Ext.extend(CQ.Static, {
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
 * @class NHM.CustomPathFieldWidget
 * @extends CQ.form.CompositeField
 * This is a custom path field with link text and target based on {@link CQ.form.CompositeField}.
 * @constructor
 * Creates a new CustomWidget.
 * @param {Object} config The config object
 */
NHM.CustomPathFieldWidget = CQ.Ext.extend(CQ.form.CompositeField, {

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    linkText: null,

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    linkURL: null,

    /**
     * @private
     * @type CQ.Ext.form.CheckBox
     */
    openInNewWindow: null,

    /**
     * @private
     * @type CQ.Ext.form.FormPanel
     */
    formPanel: null,

    constructor: function(config) {
        config = config || {};
        var defaults = {
            "border": true,
            "labelWidth": 75,
            "layout": "form"
                //"columns":6
        };
        config = CQ.Util.applyDefaults(config, defaults);
        NHM.CustomPathFieldWidget.superclass.constructor.call(this, config);
    },

    //overriding CQ.Ext.Component#initComponent
    initComponent: function() {
        NHM.CustomPathFieldWidget.superclass.initComponent.call(this);

        // Hidden field
        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);

        // Link text
        this.add(new CQ.Ext.form.Label({
            cls: "customwidget - label",
            text: "Link Text"
        }));
        this.linkText = new CQ.Ext.form.TextField({
            cls: "customwidget - 1",
            fieldLabel: "Link Text: ",
            maxLength: 80,
            maxLengthText: "A maximum of 80 characters is allowed for the Link Text.",
            allowBlank: true,
            listeners: {
                change: {
                    scope: this,
                    fn: this.updateHidden
                }
            }
        });
        this.add(this.linkText);

        // Link URL
        this.add(new CQ.Ext.form.Label({
            cls: "customwidget - label",
            text: "Link URL"
        }));
        this.linkURL = new CQ.form.PathField({
            cls: "customwidget - 2",
            fieldLabel: "Link URL: ",
            allowBlank: false,
            width: 225,
            listeners: {
                change: {
                    scope: this,
                    fn: this.updateHidden
                },
                dialogclose: {
                    scope: this,
                    fn: this.updateHidden
                }
            }
        });
        this.add(this.linkURL);

        // Link openInNewWindow
        this.openInNewWindow = new CQ.Ext.form.Checkbox({
            cls: "customwidget - 3",
            boxLabel: "New window",
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
        this.add(this.openInNewWindow);
        
//        this.setFieldsValue();

    },

    processInit: function(path, record) {
        this.linkText.processInit(path, record);
        this.linkURL.processInit(path, record);
        this.openInNewWindow.processInit(path, record);
    },

    setValue: function(value) {
        var link = JSON.parse(value);
        this.linkText.setValue(link.text);
        this.linkURL.setValue(link.url);
        this.openInNewWindow.setValue(link.openInNewWindow);
        this.hiddenField.setValue(value);
    },

//	setFieldsValue: function(value) {
//        var link = JSON.parse(value);
//        this.linkText.setValue(link.text);
//        this.linkURL.setValue(link.url);
//        this.openInNewWindow.setValue(link.openInNewWindow);
//        this.hiddenField.setValue(value);       
//    },

    getValue: function() {
        return this.getRawValue();
    },

    getRawValue: function() {
        var link = {"url": this.linkURL.getValue(),
            "text": this.linkText.getValue(),
            "openInNewWindow": this.openInNewWindow.getValue()
        };
        return JSON.stringify(link);
    },

    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    }
});

CQ.Ext.reg('mypathfield', NHM.CustomPathFieldWidget);
/* Ensure the BS object exists in the window scope. */
window.hasOwnProperty = function(obj){
	return (this[obj]) ? true : false;
};

if (!window.hasOwnProperty("NHM")) {
    window.NHM = {};
}

/**
 * @class NHM.LinkListPathFieldWidget
 * @extends CQ.form.CompositeField
 * This is a custom path field with link text and target based on {@link CQ.form.CompositeField}.
 * @constructor
 * Creates a new CustomWidget.
 * @param {Object} config The config object
 */
NHM.LinkListPathFieldWidget = CQ.Ext.extend(CQ.form.CompositeField, {

    hiddenField: null,
    linkText: null,
    linkURL: null,
    openInNewWindow: null,
    
	constructor: function(config) {
        NHM.LinkListPathFieldWidget.superclass.constructor.call(this, config);
    },

    //overriding CQ.Ext.Component#initComponent
    initComponent: function() {
        NHM.LinkListPathFieldWidget.superclass.initComponent.call(this);

        // Hidden field
        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);

        // Link text
        this.add(new CQ.Ext.form.Label({
             text: "Link Text"
        }));
        this.linkText = new CQ.Ext.form.TextField({
           maxLength: 80,
           listeners: {
                change: {
                    scope: this,
                    fn: this.updateHidden
                },
                dialogopen: {
                    scope: this,
                    fn: this.updateHidden
                }, 
                dialogselect: {
                    scope: this,
                    fn: this.updateHidden
                }
            }
        });
        this.add(this.linkText);

        // Link URL
        this.add(new CQ.Ext.form.Label({
            text: "Link URL"
        }));
        this.linkURL = new CQ.form.PathField({
            listeners: {
                change: {
                    scope: this,
                    fn: this.updateHidden
                },
                dialogclose: {
                	//aux: console.log("Debug Calling OnDialogClose"),
                    scope: this,
                    fn: this.updateHidden
                }, 
                dialogopen: {
                	//aux: console.log("Debug Calling OnDialogOpen"),
                    scope: this,
                    fn: this.updateHidden
                }, 
                dialogselect: {
                    scope: this,
                    fn: this.updateHidden
                }
            }
        });
        this.add(this.linkURL);

        // Link openInNewWindow
        this.openInNewWindow = new CQ.Ext.form.Checkbox({
           listeners: {
                change: {
                    scope: this,
                    fn: this.updateHidden
                },
                check: {
                    scope: this,
                    fn: this.updateHidden
                }, 
                dialogopen: {
                    scope: this,
                    fn: this.updateHidden
                }, 
                dialogselect: {
                    scope: this,
                    fn: this.updateHidden
                }
            }
        });
        this.add(this.openInNewWindow);
        
//        this.setFieldsValue();

    },

    /*processInit: function(path, record) {
        this.linkText.processInit(path, record);
        this.linkURL.processInit(path, record);
        this.openInNewWindow.processInit(path, record);
    },*/

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

CQ.Ext.reg('linklistpathfield', NHM.LinkListPathFieldWidget);
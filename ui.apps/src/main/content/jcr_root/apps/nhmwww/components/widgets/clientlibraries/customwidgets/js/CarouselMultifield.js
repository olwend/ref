window.hasOwnProperty = function(obj){
return (this[obj]) ? true : false;
};

if (!window.hasOwnProperty("NHM")) {
    window.NHM = {};
}

NHM.CarouselItemField = CQ.Ext.extend(CQ.form.PathField, {
	type:null,
	/*style:{width:'200px'},*/
	constructor : function(config){
	    NHM.CarouselItemField.superclass.constructor.call(this, config);
	    
	},
    
    initComponent : function(){
    	NHM.CarouselItemField.superclass.initComponent.call(this);
     
        CQ.WCM.registerDropTargetComponent(this);
    },

    getType: function() {
    	
    	return this.type;
    },
    setType: function(value) {
    	this.type = value;
    }, 
    
    getDropTargets : function() {
        var pathFieldComponent = this;
        var target = new CQ.wcm.EditBase.DropTarget(this.el, {
            "ddAccept": "*/*",
            "notifyDrop": function(dragObject, evt, data) {
                if (dragObject && dragObject.clearAnimations) {
                    dragObject.clearAnimations(this);
                }
                if (data && data.records && data.records[0]) {
                	if (data.records[0].get("videoId") != null) {
                		pathFieldComponent.setType("video");
                	} else {
                		pathFieldComponent.setType("static");
                	}
                	
                    var pathInfo = data.records[0].get("path");
                    if (pathInfo) {
                        pathFieldComponent.setValue(pathInfo);
                        return true;
                    }
                }
                return false;
            }
        });
        target.groups["youtube"] = true;
        target.groups["media"] = true;
        return [target];
    }
});

NHM.CarouselMultifieldData = CQ.Ext.extend(CQ.Static, {
	type: null,
	item: null,
	itemURL: null,
	itemHeading: null,
	itemCaption: null,

    constructor: function(type, item, itemURL, itemHeading, itemCaption) {
    	this.type = type;
    	if (item != null) {
    		this.item = item;
    	}

    	if (itemURL != null) {
    		this.itemURL = itemURL;
    	}

    	if (itemHeading != null) {
    		this.itemHeading = itemHeading;
    	}
    	
    	if (itemCaption != null) {
    		this.itemCaption = itemCaption;
    	}
    }
});

NHM.CustomWidget = CQ.Ext.extend(CQ.form.CompositeField, {
	cls:"carouselmultifield",
	/**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,
    
    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenType: null,

   /**
    * @private
    * @type CQ.Ext.form.
    */
    item: null,

    /**
    * @private
    * @type CQ.Ext.form.PathField
    */
    itemURL: null,


    /**
     * @private
     * @type CQ.Ext.form.ComboBox
     */
    itemHeading: null,
    /**
     * @private
     * @type CQ.Ext.form.ComboBox
     */
    itemCaption: null,
    
       
    constructor: function(config) {
        config = config || { };
        var defaults = {
            "border": true,
            "layout": {
            		type:'table',
            		columns:10,
            		tableAttrs: {
            			style:{width:'100%',
            					'text-align':'left'
            					}
            		}
            		
            }
        };
        config = CQ.Util.applyDefaults(config, defaults);
        NHM.CustomWidget.superclass.constructor.call(this, config);
    },

    // overriding CQ.Ext.Component#initComponent
    initComponent: function() {
        NHM.CustomWidget.superclass.initComponent.call(this);
        //Hidden Field
        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });

        this.add(this.hiddenField);
        
        this.hiddenType = new CQ.Ext.form.Hidden({});

        this.add(this.hiddenType);
        

        //ITEM PATH FIELD       
        this.item = new NHM.CarouselItemField({
            cls:"carouselmultifield-itempathfield",
            width:"150px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden,
                },
        		dialogclose: {
        			scope: this,
        			fn: this.updateHidden,
        		},
        		valid: {
        			scope: this,
        			fn: this.updateHidden,
        		}
            }
        });
        this.add(new CQ.Ext.form.Label({
            cls:"carouselmultifield-label",
            text: "Item: "}));
        this.add(this.item);
        
        
        //Link HREF Starts

        this.itemURL = new CQ.form.PathField({
           cls:"carouselmultifield-itemURL",
           width:"150px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(new CQ.Ext.form.Label({
            cls:"carouselmultifield-label",
            text: "URL: "}));
        this.add(this.itemURL);

        //Link HREF ends

        
      //Link heading
        this.itemHeading = new CQ.Ext.form.TextField({
            cls:"carouselmultifield-itemheading",
            width:"150px",
             listeners: {
                 change: {
                     scope:this,
                     fn:this.updateHidden
                 }
             }
         });
        this.add(new CQ.Ext.form.Label({
            cls:"customwidget-label",
            text: "Heading: "}));
        this.add(this.itemHeading);
        
        //Link Text
        this.itemCaption = new CQ.Ext.form.TextField({
            cls:"carouselmultifield-itemcaption",
           width:"150px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(new CQ.Ext.form.Label({
            cls:"customwidget-label",
            text: "Caption: "}));
        this.add(this.itemCaption);
        
        
    },

    // overriding CQ.form.CompositeField#processPath
    processPath: function(path) {
        //console.log("CarouselMultifield#processPath", path);
        //console.log("CarouselMultifield#processPath:this.item", this.item.getValue());
        
        this.item.processPath(path);
        
        
    },

    // overriding CQ.form.CompositeField#processRecord
    processRecord: function(record, path) {
        //console.log("CarouselMultifield#processRecord", path, record);
        this.item.processRecord(record, path);
      
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
        var data = CQ.Ext.util.JSON.decode(value);
        this.hiddenField.setValue(value);
        this.hiddenType.setValue(data.type);
        console.log("Item data.type:", data.type);
        this.item.setValue(data.item);
        this.item.setType(data.type);
        console.log("Item item.getType():", this.item.getType());
        this.itemURL.setValue(data.itemURL);
        this.itemHeading.setValue(data.itemHeading);
        this.itemCaption.setValue(data.itemCaption);
       
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        return this.getRawValue();
    },

    // overriding CQ.form.CompositeField#getRawValue
    getRawValue: function() {
    	console.log("item type value:", this.item.getType());
    	var data = new NHM.CarouselMultifieldData( this.item.getType(), this.item.getValue(), this.itemURL.getValue(), this.itemHeading.getValue(), this.itemCaption.getValue());
    	return CQ.Ext.util.JSON.encode(data);
    },

    // private
    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    },
    
    show: function() {
    	this.doLayout();
    }
    
    

});

// register xtype
CQ.Ext.reg("carouselmultifield", NHM.CustomWidget);
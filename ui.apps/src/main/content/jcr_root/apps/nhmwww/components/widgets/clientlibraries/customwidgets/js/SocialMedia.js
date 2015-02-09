/* Ensure the BS object exists in the window scope. */
window.hasOwnProperty = function(obj){
		return (this[obj]) ? true : false;
	};

if (!window.hasOwnProperty("NHM")) {
    window.NHM = {};
}

NHM.SocialMedia = CQ.Ext.extend(CQ.form.MultiField, {

	removeComponentContent: false,

    constructor: function(config) {
        var list = this;

        if (typeof config.orderable === "undefined") {
            config.orderable = true;
        }

        if (!config.fieldConfig) {
            config.fieldConfig = {};
        }
        if (!config.fieldConfig.xtype) {
            config.fieldConfig.xtype = "socialmediaelement";
        }
        config.fieldConfig.name = config.name;
        config.fieldConfig.ownerCt = this;
        config.fieldConfig.orderable = config.orderable;
        config.fieldConfig.dragDropMode = config.dragDropMode;

        var items = new Array();

        if(config.readOnly) {
            //if component is defined as readOnly, apply this to all items
            config.fieldConfig.readOnly = true;
        }

        this.hiddenDeleteField = new CQ.Ext.form.Hidden({
            "name":config.name + CQ.Sling.DELETE_SUFFIX
        });
        items.push(this.hiddenDeleteField);

        if (config.typeHint) {
            this.typeHintField = new CQ.Ext.form.Hidden({
                name: config.name + CQ.Sling.TYPEHINT_SUFFIX,
                value: config.typeHint + "[]"
            });
            items.push(this.typeHintField);
        }

        config = CQ.Util.applyDefaults(config, {
            "defaults":{
                "xtype":"socialmediaitem",
                "fieldConfig":config.fieldConfig
            },
            "items":[
                {
                    "xtype":"panel",
                    "border":false,
                    "bodyStyle":"padding:" + this.bodyPadding + "px",
                    "items":items
                }
            ]
        });
        CQ.form.MultiField.superclass.constructor.call(this,config);
        if (this.defaults.fieldConfig.regex) {
            // somehow regex get broken in this.defaults, so fix it
            this.defaults.fieldConfig.regex = config.fieldConfig.regex;
        }
        this.addEvents(
            /**
             * @event change
             * Fires when the value is changed.
             * @param {CQ.form.MultiField} this
             * @param {Mixed} newValue The new value
             * @param {Mixed} oldValue The original value
             */
            "change",
            /**
             * @event removeditem
             * Fires when an item is removed.
             * @param {CQ.form.MultiField} this
             */
            "removeditem"
        );
    },

    initComponent: function() {
        CQ.form.MultiField.superclass.initComponent.call(this);

        this.on("resize", function() {
            // resize fields
            var item = this.items.get(0);
            this.calculateFieldWidth(item);
            if (this.fieldWidth > 0) {
                for (var i = 0; i < this.items.length; i++) {
                    try {
                        this.items.get(i).field.setWidth(this.fieldWidth);
                    }
                    catch (e) {
                        CQ.Log.debug("CQ.form.MultiField#initComponent: " + e.message);
                    }
                }
            }
        });

        this.on("disable", function() {
            this.hiddenDeleteField.disable();
            if (this.typeHintField) this.typeHintField.disable();
            this.items.each(function(item/*, index, length*/) {
                if (item instanceof CQ.form.MultiField.Item) {
                    item.field.disable();
                }
            }, this);
        });

        this.on("enable", function() {
            this.hiddenDeleteField.enable();
            if (this.typeHintField) this.typeHintField.enable();
            this.items.each(function(item/*, index, length*/) {
                if (item instanceof CQ.form.MultiField.Item) {
                    item.field.enable();
                }
            }, this);
        });

        this.addItem('Twitter: %separator%');
        this.addItem('Facebook: %separator%');
        this.addItem('Linked in: %separator%');
        this.addItem('YouTube: %separator%');
    },

    // private
    calculateFieldWidth: function(item) {
        try {
            this.fieldWidth = this.getSize().width - 2*this.bodyPadding; // total row width
            for (var i = 0; i < item.items.length; i++) {
                var button = item.items.get(i);
                // subtract each button
                if (button.items && button.items.length > 0 && button.items.get(0) === item.field) {
                    // item field; not a button
                    continue;
                }
                var w = item.items.get(i).getSize().width;
                if (w == 0) {
                    // button has no size, e.g. because MV is hidden >> reset fieldWidth to avoid setWidth
                    this.fieldWidth = 0;
                    return;
                }

                this.fieldWidth -= item.items.get(i).getSize().width;
            }
        }
        catch (e) {
            // initial resize fails if the MF is on the visible first tab
            // >> reset to 0 to avoid setWidth
            this.fieldWidth = 0;
        }
    },

    removeElements: function() {
    	var items = this.findByType("socialmediaitem");
        for (var i = 0; i < items.length; i++) {
            this.remove(items[i], true);
        }

        this.doLayout();
    },

    /**
     * Adds a new field with the specified value to the list.
     * @param {String} value The value of the field
     */
    addItem: function(value) {
    	if (this.removeComponentContent) {
    		// Is going to add an item after process the path, this meanings there are attributes recovered from the content.
    		// Delete the current nodes
    		this.removeElements();

    		this.removeComponentContent = false;
    	}

        var item = this.insert(this.items.getCount() - 1, {});
        var form = this.findParentByType("form");
        if (form)
            form.getForm().add(item.field);
        this.doLayout();

        if (item.field.processPath) item.field.processPath(this.path);
        if (value) {
            item.setValue(value);
        }

        if (this.fieldWidth < 0) {
            // fieldWidth is < 0 when e.g. the MultiField is on a hidden tab page;
            // do not set width but wait for resize event triggered when the tab page is shown
            return;
        }
        if (!this.fieldWidth) {
            this.calculateFieldWidth(item);
        }
        try {
            item.field.setWidth(this.fieldWidth);
        }
        catch (e) {
            CQ.Log.debug("CQ.form.MultiField#addItem: " + e.message);
        }
    },

    processPath: function(path) {
        this.path = path;
        this.removeComponentContent = true;
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        var value = new Array();
        this.items.each(function(item, index/*, length*/) {
            if (item instanceof CQ.form.MultiField.Item) {
                value[index] = item.getValue();
                index++;
            }
        }, this);
        return value;
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
        this.fireEvent("change", this, value, this.getValue());
        var oldItems = this.items;
        oldItems.each(function(item/*, index, length*/) {
            if (item instanceof CQ.form.MultiField.Item) {
                this.remove(item, true);
                this.findParentByType("form").getForm().remove(item);
            }
        }, this);
        this.doLayout();
        if ((value != null) && (value != "")) {
            if (value instanceof Array || CQ.Ext.isArray(value)) {
                for (var i = 0; i < value.length; i++) {
                    this.addItem(value[i]);
                }
            } else {
                this.addItem(value);
            }
        }
    }

});

CQ.Ext.reg("socialmedia", NHM.SocialMedia);

NHM.SocialMediaItem = CQ.Ext.extend(CQ.Ext.Panel, {

	constructor: function(config) {
        var fieldConfig = CQ.Util.copyObject(config.fieldConfig);

        var items = new Array();

        if (fieldConfig.dragDropMode) {
            this.constructDrapDropConfig(items, fieldConfig);
        } else {
            this.constructButtonConfig(items, fieldConfig);
        }

        config = CQ.Util.applyDefaults(config, {
            "layout":"table",
            "anchor":"100%",
            "border":false,
            "layoutConfig":{
                "columns":4
            },
            "defaults":{
                "bodyStyle":"padding:3px"
            },
            "items":items
        });
        CQ.form.MultiField.Item.superclass.constructor.call(this, config);

        if (config.value) {
            this.field.setValue(config.value);
        }
    },

    constructButtonConfig: function(items, fieldConfig) {
        var item = this;
        this.field = CQ.Util.build(fieldConfig, true);
        items.push({
            "xtype":"panel",
            "border":false,
            "cellCls":"cq-multifield-itemct",
//            "width": 100,
            "items":item.field
        });

        if (!fieldConfig.readOnly) {
            if (fieldConfig.orderable) {
                items.push({
                    "xtype": "panel",
                    "border": false,
                    "items": {
                        "xtype": "button",
                        "iconCls": "cq-multifield-up",
                        "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        "handler": function(){
                            var parent = item.ownerCt;
                            var index = parent.items.indexOf(item);

                            if (index > 0) {
                                item.reorder(parent.items.itemAt(index - 1));
                            }
                        }
                    }
                });
                items.push({
                    "xtype": "panel",
                    "border": false,
                    "items": {
                        "xtype": "button",
                        "iconCls": "cq-multifield-down",
                        "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>'),
                        "handler": function(){
                            var parent = item.ownerCt;
                            var index = parent.items.indexOf(item);

                            if (index < parent.items.getCount() - 1) {
                                item.reorder(parent.items.itemAt(index + 1));
                            }
                        }
                    }
                });
            }
        }
    },

    constructDrapDropConfig: function(items, fieldConfig) {
        var item = this;

        this.field = CQ.Util.build(fieldConfig, true);

        if (!fieldConfig.readOnly) {
            if (fieldConfig.orderable) {
                var move = CQ.Util.build({
                    "xtype":"button",
                    "iconCls": "cq-multifield-drag-handle",
                    "template": new CQ.Ext.Template('<span><button class="x-btn" type="{0}"></button></span>')
                }, true);

                items.push(move);

                move.on("render", function() {
                    move.dragZone = new CQ.Ext.dd.DragZone(move.getEl(), {
                        item: item,
                        ddGroup: fieldConfig.name,
                        getDragData: function(e) {
                            var sourceEl = move.ownerCt.getEl();
                            sourceEl.setVisibilityMode(CQ.Ext.Element.DISPLAY);
                            if (sourceEl) {
                                d = sourceEl.dom.cloneNode(true);
                                d.id = CQ.Ext.id();
                                return {
                                    ddel: d,
                                    sourceEl: sourceEl,
                                    repairXY: CQ.Ext.fly(sourceEl).getXY()
                                }
                            }
                        },

                        getRepairXY: function() {
                            return this.dragData.repairXY;
                        },

                        onDrag: function() {
                            item.dropTarget.lock();
                        },

                        onInvalidDrop: function(target, e, id) {
                            CQ.Ext.dd.DragZone.superclass.onInvalidDrop.call(this,target, e, id);
                            item.dropTarget.unlock();
                        }
                    });
                });

                this.field.on("render", function() {
                    item.createDropTarget();
                })
            }
        }

        items.push({
            "xtype":"panel",
            "border":false,
            "cellCls":"cq-multifield-itemct",
//            "width": 100,
            "items":item.field
        });

    },

    createDropTarget: function() {
        if( !this.field ) return;
        if( this.dropTarget ) {
            this.dropTarget.destroy();
        }

        var item = this;

        this.dropTarget = new CQ.Ext.dd.DropTarget(this.getEl(), {
            item: item,
            ddGroup : this.field.getName(),

            getPosition: function(e, element) {
                var y = e.getXY()[1];
                var region = CQ.Ext.fly(element).getRegion();
                var h = region.bottom - region.top;
                var ypos = region.bottom - y;

                if ( ypos >= (h / 2)) {
                    return "before";
                } else {
                    return "after";
                }
            },

            showTargetLine: function(el, position) {
                var extra = 2;
                var region = CQ.Ext.fly(el).getRegion();
                var x = region.left;
                var y = region.bottom - 1;
                if (position == "before") {
                    //display target obj before drop target
                    y = region.top - 1;
                }

                CQ.form.MultiField.getTargetLine().setWidth(el.getWidth());
                CQ.form.MultiField.getTargetLine().show();
                CQ.form.MultiField.getTargetLine().setPosition(x,y)
            },

            notifyOver : function(draggedObj, e, data){
                this.lastPosition =  this.getPosition(e, this.el);
                this.showTargetLine(this.el, this.lastPosition);
                return this.dropAllowed;
            },

            notifyOut : function(draggedObj, e, data){
                CQ.form.MultiField.getTargetLine().hide();
                if(this.overClass){
                    this.el.removeClass(this.overClass);
                }
            },

            notifyDrop : function(draggedObj, e, data){
                CQ.form.MultiField.getTargetLine().hide();

                var movingItem = draggedObj.item;
                var toItem = this.item;

                movingItem.move(toItem, this.lastPosition);

                return true;
            }
        });
    },

    remove: function() {
        this.ownerCt.remove(this, true);
    },

    move: function(toItem, position) {
        var movingItem = this;
        var parent = movingItem.ownerCt;

        var index = parent.items.indexOf(toItem);

        if( position == "before") {
            index--;
        } else {
            index++;
        }
        index = index == -1 ? 0 : index;
        index = index > parent.items.getCount() ? parent.items.getCount() : index;

        parent.items.insert(index, movingItem);

        if( position == "before") {
            movingItem.getEl().insertBefore(toItem.getEl());
        } else {
            movingItem.getEl().insertAfter(toItem.getEl());
        }

        movingItem.createDropTarget();

        parent.doLayout(false, true);
    },

    /**
     * Reorders the item above the specified item.
     * @param item {CQ.form.MultiField.Item} The item to reorder above
     * @member CQ.form.MultiField.Item
     */
    reorder: function(item) {
        var value = item.field.getValue();
        item.field.setValue(this.field.getValue());
        this.field.setValue(value);
    },

    /**
     * Returns the data value.
     * @return {String} value The field value
     * @member CQ.form.MultiField.Item
     */
    getValue: function() {
        return this.field.getValue();
    },

    /**
     * Sets a data value into the field and validates it.
     * @param {String} value The value to set
     * @member CQ.form.MultiField.Item
     */
    setValue: function(value) {
        this.field.setValue(value);
    }
});

CQ.Ext.reg('socialmediaitem', NHM.SocialMediaItem);

NHM.SocialMediaElement = CQ.Ext.extend(CQ.form.CompositeField, {

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */
    socialMediaName: null,

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    linkField: null,

    constructor: function(config) {
        config = config || { };
        var defaults = {
            "border": false,
            "height": 25,
            "layout": "table",
            "columns": 8
        };
        config = CQ.Util.applyDefaults(config, defaults);
        NHM.SocialMediaElement.superclass.constructor.call(this, config);
    },

    // overriding CQ.Ext.Component#initComponent
    initComponent: function() {
    	NHM.SocialMediaElement.superclass.initComponent.call(this);

        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);

        this.socialMediaName = new CQ.Ext.form.Label({
        	autoWidth: false,
            text: "Type:",
            style:"font-size: 12px; font-family: Arial, Verdana, sans-serif; vertical-align:text-bottom; padding-bottom:0px; padding-right:10px; padding-left:0px;",
        });
        this.socialMediaName.setWidth(150);
        this.add(this.socialMediaName);



        this.linkField = new CQ.Ext.form.TextField({
            width: 380,
            style:"position:absolute; left:60px; top:0px",
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.linkField);

        this.doLayout();
    },

    // overriding CQ.form.CompositeField#processPath
    processPath: function(path) {
        this.linkField.processPath(path);
    },

    // overriding CQ.form.CompositeField#processRecord
    processRecord: function(record, path) {
        this.linkField.processRecord(record, path);
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
        var parts = value.split("%separator%");
    	this.socialMediaName.setText(parts[0]);
        this.linkField.setValue(parts[1]);

        this.updateHidden();
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        return this.getRawValue();
    },

    // overriding CQ.form.CompositeField#getRawValue
    getRawValue: function() {
    	return this.socialMediaName.text + "%separator%" + this.linkField.getValue();
    },

    // private
    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    }
});

// register xtype
CQ.Ext.reg('socialmediaelement', NHM.SocialMediaElement);

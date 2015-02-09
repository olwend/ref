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
NHM.YoutubeVideoField = CQ.Ext.extend(CQ.Ext.form.TextField, {
	
	constructor : function(config){
	    NHM.YoutubeVideoField.superclass.constructor.call(this, config);
	},
    
    initComponent : function(){
    	NHM.YoutubeVideoField.superclass.initComponent.call(this);
     
        CQ.WCM.registerDropTargetComponent(this);
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
        return [target];
    }
});

//register xtype
CQ.Ext.reg('youtubevideofield', NHM.YoutubeVideoField);
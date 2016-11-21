CQ.Ext.ns("NHM");

NHM.TagInputField = CQ.Ext.extend(CQ.tagging.TagInputField, {
    nhmTagsBasePaths: null,

    loadTagNamespaces: function() {
        this.tagNamespaces = {};

        if(!this.nhmTagsBasePaths || $.isEmptyObject(this.nhmTagsBasePaths)){
            NHM.TagInputField.superclass.loadTagNamespaces.call(this);
            return;
        }

        CQ.Ext.each(this.nhmTagsBasePaths, function(tUrl) {
            var pUrl = tUrl.substring(0, tUrl.lastIndexOf("/"));

            var tagJson = this.loadJson(pUrl + CQ.tagging.TAG_LIST_JSON_SUFFIX + "?count=false");

            if (tagJson && tagJson.tags) {
                CQ.Ext.each(tagJson.tags, function(t) {
                    if(t.path === tUrl){
                        this.tagNamespaces[t.name] = t;
                    }
                }, this);
            }
        }, this);

        this.setupPopupMenu();

        this.tagNamespacesLoaded = true;
    },

    setupPopupMenu: function() {
        NHM.TagInputField.superclass.setupPopupMenu.call(this);

        if(!this.nhmTagsBasePaths || $.isEmptyObject(this.nhmTagsBasePaths)){
            return;
        }

        var panel, treePanel, path, nsName;

        CQ.Ext.each(this.namespacesTabPanel, function(tabPanel) {
            for(var i = 0; i < tabPanel.items.length; i++){
                panel = tabPanel.items.get(i);
                treePanel = panel.items.get(0);

                nsName = treePanel.root.attributes.name;
                nsName = nsName.substring(nsName.lastIndexOf("/") + 1);

                path = this.tagNamespaces[nsName].path;

                treePanel.getLoader().path = path.substring(0, path.lastIndexOf("/"));
                treePanel.root.attributes.name = path.substring(1);
            }
        }, this);
    }
});

CQ.Ext.reg("nhmtags", NHM.TagInputField);
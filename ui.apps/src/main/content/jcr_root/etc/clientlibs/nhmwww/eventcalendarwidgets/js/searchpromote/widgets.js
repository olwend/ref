CQ.Ext.namespace("CQ.searchpromote");

CQ.searchpromote.FacetList = CQ.Ext.extend(CQ.Ext.form.ComboBox, {
    constructor: function(config) {
        CQ.Util.applyDefaults(config, {
            triggerAction: "all",
            mode: "local",
            autoLoad: false,
            displayField: "label",
            valueField: "name",
            store: new CQ.Ext.data.JsonStore({
                fields: ["name", "label"],
                root: "facets",
                id: "name",
                //just some dummy to have the http proxy constructed. the url will be replaced on loadcontent
                url: "localhost" 
            }),
            listeners: {
                "loadcontent": function(field, record, path) {
                    field.store.proxy.setUrl( path + ".facetlist.json");
                    field.store.load();
                }
            }
        });
        CQ.searchpromote.FacetList.superclass.constructor.call(this, config);
    }
});

CQ.Ext.reg("searchpromote-facetlist", CQ.searchpromote.FacetList);
CQ.Ext.namespace("CQ.searchpromote");

CQ.searchpromote.SearchPromote = {

    SP_REMOTEIDX_ERROR: CQ.I18n.getMessage("Search&amp;Promote remote control indexing service returned an error."),
    SP_REMOTEIDX_SERVER_ERR: CQ.I18n.getMessage("There was an error sending the request to Search&amp;Promote remote control indexing service"),
    SP_REMOTEIDX_SUCCESS: CQ.I18n.getMessage("Search&amp;Promote remote control indexing service request was successful."),

    /**
     * Shows a progress bar dialog.
     *
     * @param dialog Parent dialog to attach the progress bar to.
     * @param isShown Indicator if progress dialog is shown or not.
     */
    showButtonIndicator: function(dialog, isShown) {
        var btn = dialog.find("localName", "connectButton")[0];
        if (this.labelBtn == null) {
            this.labelBtn = btn.getText();
        }
        if (!isShown) {
            CQ.Ext.Msg.wait(CQ.I18n.getMessage("Connection successful")).hide();
        } else {
            CQ.Ext.Msg.wait(CQ.I18n.getMessage("Connecting to Search&Promote..."));
        }
    },

    /**
     * Gets a field with the provided key from a panel.
     *
     * @param {Panel} panel Panel which holds the field.
     * @param {String} key Field name
     */
    getField: function(panel, key) {
        var items = panel.find("name", "./" + key);
        if ((CQ.Ext.isArray(items)) && (items.length > 0))
            return items[0];
    },

    /**
     * Fetches search form XML from a remote location and stores it in a hidden
     * text area for later persisting.
     *
     * @param dialog
     */
    connect: function(dialog) {
        var memberid = this.getField(dialog, 'memberid');
        var accountno = this.getField(dialog, 'accountno');
        var that = this;

        this.showButtonIndicator(dialog, true);

        function fieldEmpty(field, msg) {
            if (!field || field.getValue() == "") {
                that.showButtonIndicator(dialog, false);
                CQ.Ext.Msg.alert(CQ.I18n.getMessage("Error"), msg);
                return true;
            }
            return false;
        }

        if (fieldEmpty(memberid, CQ.I18n.getMessage("Please enter the member id.")) ||
            fieldEmpty(accountno, CQ.I18n.getMessage("Please enter the account no."))) {
            return;
        }

        CQ.HTTP.post(CQ.HTTP.externalize("/libs/cq/searchpromote/searchform"),
            function(options, success, response) {
                that.showButtonIndicator(dialog, false);
                if (success) {
                    var formxml = CQ.HTTP.eval(response);
                    if (formxml && formxml.xml) {
                        var formxmlField = that.getField(dialog, "searchformxml");
                        formxmlField.setValue(formxml.xml);
                        dialog.find("localName", "connectButton")[0].setText(CQ.I18n.getMessage('Re-Connect to Search&Promote'));
                        CQ.Ext.Msg.show({
                            title: CQ.I18n.getMessage("Success"),
                            msg: CQ.I18n.getMessage("Connection successful"),
                            buttons: CQ.Ext.Msg.OK,
                            icon: CQ.Ext.Msg.INFO});
                        CQ.cloudservices.getEditOk().enable();
                    } else if (formxml && formxml.error) {
                        var cause = formxml.error;
                        if (formxml.error.indexOf("Credentials") > -1) {
                            cause = "Login to Search&Promote failed";
                        }
                        CQ.Ext.Msg.alert(CQ.I18n.getMessage("Error"), CQ.I18n.getVarMessage(cause));
                    }
                } else {
                    CQ.Ext.Msg.alert(CQ.I18n.getMessage("Error"), CQ.I18n.getMessage("Connection to Search&Promote could not be established."));
                }
            },
            {
                "memberid": memberid.getValue(),
                "accountno": accountno.getValue()
            }, this, true
        );
    },

    callRemoteIndex: function(dialog) {
        var fullindex = this.getField(dialog, "fullindex");

        var that = this;

        var configPath = dialog.path.substring(0, dialog.path.lastIndexOf("/"));
        CQ.HTTP.post(CQ.HTTP.externalize("/libs/cq/searchpromote/remoteindex"),
            function(options, success, response) {
                if (success) {
                    if (response.responseText.indexOf("Error") > -1) {
                        CQ.Ext.Msg.alert(CQ.I18n.getMessage("Error"),that.SP_REMOTEIDX_ERROR);
                    } else {
                        CQ.Ext.Msg.show({
                            title: CQ.I18n.getMessage("Success"),
                            msg: that.SP_REMOTEIDX_SUCCESS,
                            buttons: CQ.Ext.Msg.OK,
                            icon: CQ.Ext.Msg.INFO});
                    }
                } else {
                    CQ.Ext.Msg.alert(CQ.I18n.getMessage("Error"), that.SP_REMOTEIDX_SERVER_ERR);
                }

            },
            {
                "fullfeed": fullindex.getValue(),
                "configPath": configPath
            }, this, true
        );
    },

    enableSchedulerControlChanged: function(dialog, checked) {
        this.toggleScheduleExpressionField(dialog, checked);
    },

    toggleScheduleExpressionField: function(dialog, showControl) {
        var scheduleExpressionField = this.getField(dialog, "schedule");
        scheduleExpressionField.setVisible(showControl);
    },

    /**
     * Creates or updates the scheduler configuration.
     * A scheduler's configuration consists of a node of type cq:ExportConfig
     * having the following properties:
     * <ul>
     *     <li>type</li>
     *     <li>expression</li>
     *     <li>enabled</li>
     * </ul>
     *
     * @param dialog
     */
    updateSchedulerConfiguration: function(dialog) {
        var enabled = this.getField(dialog, "enabled");
        var schedule = this.getField(dialog, "schedule");

        var configPath = dialog.path.substring(0, dialog.path.lastIndexOf("/"));

        CQ.HTTP.post(CQ.HTTP.externalize(dialog.path + "/config"),
            function(options, success, response) {

            }, {
                "jcr:primaryType": "cq:ExporterConfigFolder",
                "expression": schedule.getValue(),
                "type": "searchpromote-index-caller",
                "source": "(not set)",
                "enabled": enabled.getValue(),
                "enabled@TypeHint": "Boolean",
                "configPath": configPath
            }, this, true);
    },

    onBeforeShow: function(dialog) {
        var enabled = this.getField(dialog, "enabled");
    },

    onSelectionChanged: function(component, newValue, oldValue) {
        var dialog = component.findParentByType("dialog");
        var labelField = this.getField(dialog, "schedulelabel");

        var optLength = component.options.length;
        for (var i=0; i < optLength; i++) {
            if (component.options[i].value === newValue) {
                labelField.setValue(component.options[i].text);
            }
        }
    }
};

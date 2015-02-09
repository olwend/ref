window.NHM = window.NHM || {};

NHM.HideComponentsPanel = {
	IN_ID : '',
	disableFn: function(){
		try{
			var sk = CQ.WCM.getSidekick();
 
            if(!sk){
            	return;
            }
 
            $.ajax({
    		    type: "POST",
    		    crossDomain: true,
    		    async: false,
    		    url: "/bin/users/checkcomponentpanelrights",
    		    success: function (data, success) {
    		    	if (data && data != null && data != "true") {
    		    		var compTab = sk.panels["COMPONENTS"];
    		    		 
    		            if (compTab) {
    		            	/*compTab.setVisible(false);*/
    		                compTab.setDisabled(true);
    		            }
    		    	}
    		    	
    		    	clearInterval(NHM.HideComponentsPanel.IN_ID);
    		    },
    	        error :function( jqxhr, textStatus, error ) { 
    	            var err = textStatus + ', ' + error;
    	            console.log( "Request Failed: " + err);
    	        }
    		});
        }catch(err){
            console.log("Error:" + err);
        }
    }
}
 
CQ.Ext.onReady(function(){
    var h = NHM.HideComponentsPanel;
    h.IN_ID = setInterval(h.disableFn, 500);
});
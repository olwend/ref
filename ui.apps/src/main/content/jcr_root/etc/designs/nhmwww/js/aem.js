jQuery(document).ready(function() {
	jQuery('.global-header').css("position","static");	// Stop the megamenu being sticky
	jQuery('.global-header').css("z-index","1");	// Allow AEM Dialogs to show above the megamenu
	jQuery('.cq-element-megamenu').css("height","100px"); // prevent components from displaying under the megamenu
	jQuery('.hero').css("padding-top","0"); // Padding added to hero is only neded when megamenu is sticky
	jQuery('body').css("padding-top","0"); // Padding added to body (separating logo and nav) is not needed in Edit mode (WR-1059) 
	onYouTubeIframeAPIReady();
	//$(document).foundation('equalizer', 'reflow');
});
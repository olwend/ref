jQuery(document).ready(function() {
	jQuery('.global-header').css("position","static");	// Stop the megamenu being sticky
	jQuery('.global-header').css("z-index","1");	// Allow AEM Dialogs to show above the megamenu
	jQuery('.cq-element-megamenu').css("height","100px"); // prevent components from displaying under the megamenu
	jQuery('.hero').css("padding-top","0"); // Padding added to hero is only neded when megamenu is sticky
	onYouTubeIframeAPIReady();
	//$(document).foundation('equalizer', 'reflow');
});
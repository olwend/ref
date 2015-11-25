$(document).ready(function(){
    
    //scrollto function
	$('.legacy-css #microsite-body ul li a, .legacy-css #microsite-body ol li a, .legacy-css #microsite-body p a').on('click', function(){
        //strips out # tag
		var linkName = $(this).attr("href").replace("#" , "");
        $('html, body').animate({scrollTop: $("[name='" + linkName + "']").offset().top -165});
    });

});
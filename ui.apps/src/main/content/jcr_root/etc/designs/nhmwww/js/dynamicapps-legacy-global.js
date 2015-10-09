$(document).ready(function(){
    
    //scrollto function
	$('.legacy-css #microsite-body ul li a').on('click', function(){
        //strips out # tag
		var linkName = $(this).attr("href").replace("#" , "");
        $('html, body').animate({scrollTop: $("[name='" + linkName + "']").offset().top -165});
    });

});
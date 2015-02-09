$(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	$.ajax({
		type: 'POST',    
		url:'/bin/discover/navigation.json',
		data:{
			publication: $('.discoverpublication .leftarrow').data('path')
		},
		success: function(data){
			var json = jQuery.parseJSON(data);

			$('.discoverpublication .leftarrow > a').attr('href', json.previous);
			$('.discoverpublication .rightarrow > a').attr('href', json.next);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$('.discoverpublication .leftarrow').hide();
			$('.discoverpublication .rightarrow').hide();
		}
	});
	
	$('.discover-image-caption-icon').click(function() {
		$(this).hide();
		$(this).next().show();
	});
	
	$('.discover-image-caption .close-icon').click(function() {
		$(this).parent().hide();
		$(this).parent().prev().show();
	});
});
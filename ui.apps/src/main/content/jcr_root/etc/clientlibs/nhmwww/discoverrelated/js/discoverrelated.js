$(document).ready(function() {
	startMansonry();
});
$(document).on('replace', 'img', function (e, new_path, original_path) {
	startMansonry();
});
function startMansonry() {
	var $container = $('.discoverrelated .related-posts');
	$container.imagesLoaded(function(){
		$container.masonry({
			itemSelector: '.discover-element'
		});
	});
}
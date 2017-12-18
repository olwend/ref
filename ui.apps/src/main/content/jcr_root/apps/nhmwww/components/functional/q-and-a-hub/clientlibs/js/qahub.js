$(document).ready(function() {

	var questionCount = $(".qa-question").length - 1;
	$('#prev-question').data('question', questionCount);
	$("#qa-question-0").addClass("selected");

	$(".qa-nav").click(function(event) {
		// Prevent window jump due to unfulfilled link
		event.preventDefault();

		// Check if answer window is off the screen - if so, scroll to top of answer window
		// '150' refers to pixel count, to offset the fixed menu
		// '180' refers to pixel count, to provide 30px margin on top of the answer window
		if ( $('#answers').offset().top < ($(window).scrollTop() + 150) ) {
			$(window).scrollTop( $('#answers').offset().top - 180 );
		}

		var position = parseInt( $(this).data('question') );
		var targetElement = $("#qa"+position);
		targetElement.show();
		targetElement.siblings(".answer").hide();

		previousQ = findPrevious(position);
		nextQ = findNext(position);

		var prevElement = $('#prev-question');
		prevElement.data('question', previousQ);

		var nextElement = $('#next-question');
		nextElement.data('question', nextQ);

		$(".qa-question").removeClass("selected");
		$("#qa-question-"+position).addClass("selected");
	});

	function findPrevious(position) {
		previousQ = parseInt( position - 1 );

		if(previousQ < 0) {
			var previousQ = questionCount;
		}

		return previousQ;
	}

	function findNext(position) {
		nextQ = parseInt( position + 1 );

		if(nextQ > questionCount) {
				var nextQ = 0;
		}

		return nextQ;
	}

});
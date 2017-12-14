$(document).ready(function() {

	var questionCount = $(".qa-question").length - 1;
	$('#prev-question').data('question', questionCount);
	$("#qa-question-0").addClass("selected");

	$(".qa-nav").click(function() {
		var position = parseInt( $(this).data('question') );
		var targetElement = $("#qa"+position);
		targetElement.stop().slideDown();
		targetElement.siblings(".answer").stop().slideUp();

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
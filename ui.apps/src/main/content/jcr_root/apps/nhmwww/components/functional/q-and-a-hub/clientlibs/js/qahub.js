$(document).ready(function() {

	var questionCount = $('.qa-question').length - 1;
	$('#prev-question').data('question', questionCount);
	$('#qa-question-0').addClass('js-qahub--question-selected');

	// Function to capture any click of questions in the main list, or the navigation buttons
	$('.js-qa-nav').click(function(event) {
		// Prevent window jump due to unfulfilled link
		event.preventDefault();

		// Check if answer window is off the screen - if so, scroll to top of answer window
		// '150' refers to pixel count, to offset the fixed menu
		// '180' refers to pixel count, to provide 30px margin on top of the answer window
		if ( $('.qahub--answer-list').offset().top < ($(window).scrollTop() + 150) ) {
			$(window).scrollTop( $('.qahub--answer-list').offset().top - 180 );
		}

		// var position = parseInt( $(this).data('question') );
		var position = $(this).data('question');
		var targetElement = $('#qa'+position);
		targetElement.show();
		targetElement.siblings('.answer').hide();

		// Find values of previous and next questions, based on current question position
		previousQ = findPrevious(position);
		nextQ = findNext(position);

		// Set value of Previous Question arrow
		var prevElement = $('#prev-question');
		prevElement.data('question', previousQ);

		// Set value of Next Question arrow
		var nextElement = $('#next-question');
		nextElement.data('question', nextQ);

		$('.qa-question').removeClass('js-qahub--question-selected');
		$('#qa-question-'+position).addClass('js-qahub--question-selected');

		// Check if question in list is hidden at the bottom, and scroll to it if necessary
		if ( $('#qa-question-'+position).offset().top >
			( $('.qahub--question-list').offset().top + $('.qahub--question-list').height() ) ) {
			// "-200" used to prevent extreme acceleration in getting to the bottom of a short list
			$('.qahub--question-list').stop().animate({
				scrollTop: $('.qahub--question-list').scrollTop() + ( $('#qa-question-'+position).position().top - 200)
				}, 250, 'swing' );
		}
		// Check if question in list is hidden at the top, and scroll to it if necessary
		if ( $('#qa-question-'+position).offset().top < $('.qahub--question-list').offset().top) {
			// "+20" used to ensure the window is scrolled completely to the top, including padding
			$('.qahub--question-list').stop().animate({
				scrollTop: $('#qa-question-'+position).position().top - ( $('#qa-question-'+position).height() + 20)
				}, 250, 'swing' );
		}
	});


	// Function to control scrolling in question list
	$('.qahub--question-list-nav').click(function(event) {
		// Prevent window jump due to unfulfilled link
		event.preventDefault();

		var direction = $(this).data('direction');
		if (direction == 'up') {var scrollAmount = $('.qahub--question-list').scrollTop() - 50; }
		if (direction == 'down') {var scrollAmount = $('.qahub--question-list').scrollTop() + 50; }

		$('.qahub--question-list').stop().animate( {scrollTop: scrollAmount }, 250, 'swing' );
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
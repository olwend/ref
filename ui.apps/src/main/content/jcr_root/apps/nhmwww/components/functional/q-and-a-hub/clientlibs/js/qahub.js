$(document).ready(function() {

	var questionCount = $('.qa-question').length - 1,
		questionListTotalHeight = 0;

	// Calculate total height of question list for mobile pulldown
	$('.qa-question').each(function() {
	  questionListTotalHeight += $(this).outerHeight();
	});

	$('#prev-question').data('question', questionCount);
	$('#qa-question-0').addClass('js-qahub--question-selected');

	// Function to capture any click of questions in the main list, or the navigation buttons
	$('.js-qa-nav').click(function(event) {
		// Prevent window jump due to unfulfilled link
		event.preventDefault();

		var position = $(this).data('question'),
			targetElement = $('#qa'+position),
			questionListContainerOffset = $('.qahub--question-container').offset(),
			questionListContainerScrollLimit = null,
			questionListContainerScrollOffsetLimit = null,
			questionListOffset = $('.qahub--question-list').offset(),
			questionListHeight = $('.qahub--question-list').height(),
			questionListScrollTop = $('.qahub--question-list').scrollTop(),
			questionOffset = $('#qa-question-'+position).offset(),
			questionHeight = $('#qa-question-'+position).height(),
			questionPosition = $('#qa-question-'+position).position(),
			previousQ = null,
			nextQ = null,
			prevElement = null,
			nextElement = null;


		// Check if answer window is off the screen - if so, scroll to top of answer window
		if ( $('.js-qahub-control').is(":visible") ) {
			// '150' refers to pixel count, to offset the fixed menu
			questionListContainerScrollLimit = $(window).scrollTop() + 150;
			// '160' refers to pixel count, to provide 10px margin on top of the answer window
			questionListContainerScrollOffsetLimit = questionListContainerOffset.top - 160;
		} else {
			// Does not alter mobile values as the menu is not fixed
			questionListContainerScrollLimit = $(window).scrollTop();
			questionListContainerScrollOffsetLimit = questionListContainerOffset.top;
			// Call the exand function to hide the question list
			expand('up');
		}
		if ( questionListContainerOffset.top < questionListContainerScrollLimit ) {
			$('html,body').animate({
				scrollTop: questionListContainerScrollOffsetLimit
				}, 250, 'swing' );
		}

		// Control answer visibility
		// Use :visible selector to prevent fadeIn callback from firing immediately
		// Use stop() to prevent fast concurrent clicks from queueing animations
		targetElement.siblings('.answer:visible').stop().fadeOut('fast', function() {
			targetElement.fadeIn('slow');
		});

		// Find values of previous and next questions, based on current question position
		previousQ = findPrevious(position);
		nextQ = findNext(position);

		// Set value of Previous Question arrow
		prevElement = $('#prev-question');
		prevElement.data('question', previousQ);

		// Set value of Next Question arrow
		nextElement = $('#next-question');
		nextElement.data('question', nextQ);

		// Control question selected class
		$('.qa-question').removeClass('js-qahub--question-selected');
		$('#qa-question-'+position).addClass('js-qahub--question-selected');

		// Check if question in list is hidden at the bottom, and scroll to it if necessary
		if ( ( questionOffset.top + questionHeight + 10 ) > ( questionListOffset.top + questionListHeight ) ) {
			// "-150" used to prevent extreme acceleration in getting to the bottom of a short list
			$('.qahub--question-list').stop().animate({
				scrollTop: questionListScrollTop + ( questionPosition.top - 150 )
				}, 250, 'swing' );
		}
		// Check if question in list is hidden at the top, and scroll to it if necessary
		if ( questionOffset.top < questionListOffset.top ) {
			// "- 50" used to ensure the window is scrolled completely to the top, including padding
			$('.qahub--question-list').stop().animate({
				scrollTop: (questionOffset.top - questionListOffset.top) + ( questionListScrollTop - 50 )
				}, 250, 'swing' );
		}
	});


	// Function to control scrolling in question list
	$('.qahub--question-list-nav').click(function(event) {
		// Prevent window jump due to unfulfilled link
		event.preventDefault();

		var direction = $(this).data('direction');
		if (direction == 'expand') {
			if ( $('.qahub--question-expand').hasClass('open') ) {
				expand('up');
			} else {
				expand('down');
			}
		} else {
			if (direction == 'up') {var scrollAmount = $('.qahub--question-list').scrollTop() - 50; }
			if (direction == 'down') {var scrollAmount = $('.qahub--question-list').scrollTop() + 50; }
			$('.qahub--question-list').stop().animate( {scrollTop: scrollAmount }, 250, 'swing' );
		}
	});

	function expand(direction) {
		if ("up" == direction) {
			$('.qahub--question-list').animate({height: '0'}, 350, 'swing', function() {
				$('.qahub--question-list').css('overflow-y', 'scroll');
				$('.qahub--question-expand').removeClass('open');
				$('#expand-down').show();
				$('#expand-up').hide();
			});
		} else if ("down" == direction) {
			$('.qahub--question-list').animate({height: questionListTotalHeight}, 350, 'swing', function() {
				$('.qahub--question-list').css('overflow-y', 'hidden');
				$('.qahub--question-expand').addClass('open');
				$('#expand-down').hide();
				$('#expand-up').show();
			});
		}
	}

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
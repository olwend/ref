$(document).ready(function() {
	$('.js-social-signup form').each(function() {
		var $this = $(this);
		$($this).validate({
			rules: {
				firstname: 'required',
				lastname: 'required',
				email: {
					required: true,
					email: true
				},
				question: {
					maxlength: 0
				}
			},
			showErrors: function(errorMap, errorList) {
				if (this.numberOfInvalids() > 0) {
					$this.find('.errors').html('Please fill in the required fields.');
					$this.find('.errors').show();
					$this.find('.item-input').css({"background-color":"#F5E6E6", "border":"2px solid #AE3C39"});
					$this.find(".item-input[aria-invalid='false']").removeAttr("style");
					$this.find('.social-signup--link-icons').hide();
				} else {
					$this.find('.errors').hide();
					$this.find('.errors').html('');
					$this.find('.item-input').removeAttr("style");
					$this.find('.social-signup--link-icons').show();
				}
			},
			submitHandler: function (form) {
				$this.find('.errors').html('Submitting...');
				$this.find('.errors').show();
				// Make the ajax call to the servlet and wait for the response to redirect to the thank you page or show the errors.
				$.ajax({
					crossDomain: true,
					async: false,
					type: $(form).attr('method'),
					url: $(form).attr('action'),
					data: $(form).serialize(),
					dataType : 'json',
					success: function (data, success) {
						if (data.success == 'true') {
							window.location.href = data.thankyouURL;
						} else {
							$this.find('.errors').html(data.errorMessage);
							$this.find('.errors').show();
							$this.find(".social-signup--link-icons").hide();
						}
					},
					error :function( jqxhr, textStatus, error ) {
						$this.find('.errors').html('There has been an error and we cannot subscribe you to the newsletter at this time.');
						$this.find('.errors').show();
						$this.find(".social-signup--link-icons").hide();
					}
				});
				return false;
			}
		});
	});

	// WR-1340 - refactor social signup
	// This determines the width of the container and the window
	// and adds a class based on the perceived size of the container.
	$('.social-signup--input-container').each(function() {
		var social = $(this);
		if ( window.outerWidth < 750 ) { // Captures mobile in landscape up to iPhone 8 plus
			social.addClass("js-social-signup--small");
		} else if ( window.outerWidth < 780 ) { // Captures tablet in portrait
			if ( social.context.offsetWidth < 300 ) {
				social.addClass("js-social-signup--small");
			} else if ( social.context.offsetWidth < 500 ) {
				social.addClass("js-social-signup--medium");
			} else {
				social.addClass("js-social-signup--large");
			}
		} else if ( window.outerWidth < 1050 ) { // Captures tablet in landscape
			if ( social.context.offsetWidth < 350 ) {
				social.addClass("js-social-signup--small");
			} else if ( social.context.offsetWidth < 700 ) {
				social.addClass("js-social-signup--medium");
			} else {
				social.addClass("js-social-signup--large");
			}
		} else { // Desktop
			if ( social.context.offsetWidth < 400 ) {
				social.addClass("js-social-signup--small");
			} else if ( social.context.offsetWidth < 800 ) {
				social.addClass("js-social-signup--medium");
			} else {
				social.addClass("js-social-signup--large");
			}
		}
	});
});

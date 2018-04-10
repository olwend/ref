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
					$this.find('.social-signup--icon-container').hide();
				} else {
					$this.find('.errors').hide();
					$this.find('.errors').html('');
					$this.find('.item-input').removeAttr("style");
					$this.find('.social-signup--icon-container').show();
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
							}
						},
							error :function( jqxhr, textStatus, error ) {
								$this.find('.errors').html('There has been an error and we cannot subscribe you to the newsletter at this time.');
								$this.find('.errors').show();
							}
					});
				return false;
			}
		});
	});
});
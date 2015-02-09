$(document).ready(function() {
	$('.newslettersignup form').each(function() {
		var $this = $(this);
		$(this).validate({
			rules: {
				name: 'required',
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
					$this.find('.errors').html('Please enter a valid email address and fill in the necessary fields.');
					$this.find('.errors').show();
					$this.find('.policy').hide();
					
				} else {
					$this.find('.errors').hide();
					$this.find('.errors').html('');
					$this.find('.policy').show();
				}
			},
			submitHandler: function (form) {
				// Make the ajax call to the servlet and wait for the response to redirect to the whank you page or show the errors.
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
							$this.find('.policy').hide();
		    			}
		    		},
		    	    error :function( jqxhr, textStatus, error ) { 
		    	    	$this.find('.errors').html('There has been an error and we cannot subscribe you to the newsletter at this time.');
		    	    	$this.find('.errors').show();
						$this.find('.policy').hide();
		    	    }
		    	});
				return false;
			}
		});
	});
});
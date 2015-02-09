$(document).ready(function() {
	$('.newslettersignup form').each(function() {
		var $this = $(this);
		
		$this.validate({
			
			debug: true,
			
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
					$this.find('.errors').html('Please, fill the fields and provide a valid email address.');
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
		    	    	$this.find('.errors').html('There has been an error and we cannot collect your email at this time.');
		    	    	$this.find('.errors').show();
						$this.find('.policy').hide();
		    	    }
		    	});
				return false;
			}
		});
		
	});
	
	$('.footer-utility form').validate({
		debug: true,
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
		messages: {
			name: "Please tell us your name",
			email: {
				required: "We'll need your email address to sign you up",
				email: "Please check your email address and try again"
			}
		}
	});
});
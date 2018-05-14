$( function() {
	//Get a list of names from the API
    var availableDinosaurs = [];
    $.getJSON('http://www.nhm.ac.uk/api/dino-directory-api/dinosaurs?view=genus', function(data) {

        for(var i=0; i<data.length; i++) {
            var url = "/content/nhmwww/en/home/discover/dino-directory/" + data[i].genus.toLowerCase() + ".html";
            var dinosaur = {value:url, label:data[i].genus};
            availableDinosaurs.push(dinosaur);
        }
    });

    $("#dinosaur-input").autocomplete({
        source: function(request, response) {
			var results = $.ui.autocomplete.filter(availableDinosaurs, request.term);

			//Only show 10 items in the autocomplete list
            response(results.slice(0, 10));
        },
        focus: function (event, ui) {
            event.preventDefault();
            this.value = ui.item.label;
        },
        select: function(event, ui) {
        	//Override default and use label rather than value for input text
            event.preventDefault();
        	$("#dinosaur-input").val(ui.item.label);

			window.location.href = ui.item.value;
        }
    });

    //This will filter the results in the autocomplete list to only return names that
    //start with the letters entered by the user
    $.ui.autocomplete.filter = function(array, term) {
        var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(term), "i");
        return $.grep(array, function (value) {
            return matcher.test(value.label || value.value || value);
        });
    };
});

$( document ).ready(function() {
    $('.js-dinosaurnav--category-control').click(function() {
        if ( $(this).hasClass('active') ) {
            $(this).removeClass('active');
            $(this).next('.js-dinosaurnav--category').stop().slideUp();
            $(this).find('.js-dinosaurnav--category-contract').hide();
            $(this).find('.js-dinosaurnav--category-expand').show();
        } else {
            $('.js-dinosaurnav--category-control').removeClass('active');
            $('.js-dinosaurnav--category').stop().slideUp();
            $('.js-dinosaurnav--category-contract').hide();
            $('.js-dinosaurnav--category-expand').show();
            $(this).addClass('active');
            $(this).next('.js-dinosaurnav--category').stop().slideDown();
            $(this).find('.js-dinosaurnav--category-expand').hide();
            $(this).find('.js-dinosaurnav--category-contract').show();
        }
    });

    $('.dinosaurnav--description').click(function(){
        if (window.innerWidth < 750) {
            if ( $(this).hasClass('active') ) {
                $(this).removeClass('active');
                $('.dinosaurnav--categories').stop().slideUp();
                $('.js-dinosaurnav--categories-contract').hide();
                $('.js-dinosaurnav--categories-expand').show();
            } else {
                $(this).addClass('active');
                $('.dinosaurnav--categories').stop().slideDown();
                $('.js-dinosaurnav--categories-contract').show();
                $('.js-dinosaurnav--categories-expand').hide();

            }
        }
    });
});

function svgInliner() {

    if(Modernizr.svg === true){// && jQuery('.html').hasClass('ie9')) {
        
        var changeColor = function(svg, color) {
            var elms = svg.selectAll('path');

            elms.forEach(function(elm, i) {
                // console.log(elm);
                elm.animate({'stroke': color}, 200);
            });
        },
        count = 0;
        
        jQuery('.svg-ico').each(function(i){
        	
            count++;
            var $this = jQuery(this),
                svgPath = $this.data('svg-src'),
                title = $this.data('svg-title'),
                alt = $this.attr('alt'),
                strokeWidth = $this.data('stroke-width'),
                curSvg;

            // $this.before('<figure id="svg-' + title + '" class="no-boxsizing ' + svgClass +'"></figure>');
            $this.attr('id', 'svg-' + title + "-" + count);

            curSvg = Snap('#svg-' + title + "-" + count);

            Snap.load(svgPath, function(svg) {
                var baseColor;
                
                curSvg.append(svg);

                if ($this.data('base-color') !== undefined && $this.data('base-color') !== '') {
                    baseColor = $this.data('base-color');
                }else{
                    baseColor = $this.css('color');
                }

                changeColor(curSvg, baseColor);

                if ($this.data('stroke-width') !== undefined) {
                    if (typeof strokeWidth === 'string') {
                        strokeWidth = parseInt(strokeWidth);
                    }

                    curSvg.attr({
                        strokeWidth: strokeWidth
                    });
                }

                if (!!$this.data('hover-color')) {
                    var hoverColor = $this.data('hover-color');
                    $this.closest('a').hover(function(e) {                        
                        if(e.type === 'mouseenter') {
                            changeColor(curSvg, hoverColor);
                        }
                        if (e.type === 'mouseleave') {
                            changeColor(curSvg, baseColor);
                        }
                    });
                }

                if ($this.data('mobile-base') !== undefined) {
                    var mobileBase = $this.data('mobile-base');
                    
                    if (jQuery(window).width() < 768) {
                        console.log('running and val is: ' + mobileBase);
                        changeColor(curSvg, mobileBase);
                        $this.closest('a').off('mouseenter mouseleave');
                    }

                    jQuery(window).on('resize', function(e) {
                        var $w = jQuery(this);

                        if ($w.width() < 768) {
                            changeColor(curSvg, mobileBase);
                            $this.closest('a').off('mouseenter mouseleave');
                        } else {
                            changeColor(curSvg, $this.data('base-color'));

                            $this.closest('a').hover(function(e) {

                                if(e.type === 'mouseenter') {
                                    changeColor(curSvg, hoverColor);
                                }

                                if (e.type === 'mouseleave') {
                                    changeColor(curSvg, baseColor);
                                }
                            });
                        }
                    });
                }
            });

        });
    } else {
        jQuery('.svg-ico').each(function(i) {
            var $this = jQuery(this),
                alt = $this.attr('alt'),
                imgPath = $this.data('fallback');
            if(imgPath){
                $this.append('<img src="'+ imgPath +'" alt="'+ alt +'" />');
            }
        });
    }
}

jQuery(window).load(function(){
    jQuery('.shop-slider-wrapper').prepend(jQuery('.shop-slider-wrapper .lSAction'));
    //jQuery('.shop-slider-wrapper').prepend('.shop-slider-wrapper .lSAction');
});

var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

function onYouTubeIframeAPIReady() {
	
   /* player = new YT.Player('player', {
        height: '649',
        width: '1440',
        videoId: 'irSDq4WMVmE',
        playerVars: { 'autoplay': 1, 'controls': 1, 'showinfo': 0, 'color': 'white' },
        events: { 
        }});*/
	
	$('.video-wrapper').each(function (){
        var $this = $(this),
            nhmvideoId = $this.data('nhm-videoid'),
            player = new YT.Player(nhmvideoId, {
	            //height: '649',
	            //width: '1440',
	            videoId: nhmvideoId,
	            playerVars: { 'loop': 1, 'playlist': nhmvideoId, 'autoplay': 0, 'controls': 1, 'showinfo': 0, 'color': 'white' },
                events: { 
                    onReady: onPlayerReady
	            }
            });
            $this.data('player', player);
	});
	resizeYoutubeFrames();
}

function resizeYoutubeFrames() {
	// Find all YouTube videos
	var $allVideos = $("iframe[src^='http://www.youtube.com'],iframe[src^='https://www.youtube.com']"),

	    // The element that is fluid width
	    $fluidEl = $(".video-wrapper");

	// Figure out and save aspect ratio for each video
	$allVideos.each(function() {
		$(this)
			.data('aspectRatio', this.height / this.width)
			.data('originalHeight', this.height)
			.data('originalWidth', this.width)
			// and remove the hard coded width/height
			.removeAttr('height')
			.removeAttr('width');

	});

	// When the window is resized
	// (You'll probably want to debounce this)
	$(window).resize(function() {

		//var newWidth = $fluidEl.width(); //dhis - dont use 1st video-wrapper
	
		// Resize all videos according to their own aspect ratio
		$allVideos.each(function() {
			
			var $el = $(this);
			var newWidth = $el.closest('.video-wrapper').width();  //dhis use items video-wrapper parent width instead
			
			if(newWidth == $el.data('originalWidth')) {
				$el
				.width(newWidth)
				.height($el.data('originalHeight'));
			} else {
			
				$el
				.width(newWidth)
				.height(newWidth * $el.data('aspectRatio'));
			}
		});

	// Kick off one resize to fix all videos on page load
	}).resize();

}

function onPlayerReady(event) {
    var player = event.target;
    if(player.playWhenReady){
        player.playVideo();
    }
}

jQuery(document).ready(function() {
    
    svgInliner();

    jQuery(document).foundation();

    
	
    //var thumbnails = $(this).data('nhm-thumbnails');
    $('.carousel').each(function (carousel){
        var $this = $(this),
            items =  $this.find('li').length,
            thumbnails = $this.data('nhm-thumbnails'),
            autoscroll = $this.data('nhm-autoscroll'),
            autoscrollDuration = $this.data('nhm-autoscroll-duration'),
            grouping = $this.data('nhm-grouping') || 1;
	    
	    carousel = $this.lightSlider({
	    	gallery: thumbnails,
	        item: grouping,
	        controls: true,
            pager: true,
	        slideMove: grouping,
            slideMargin: 0,
	        thumbMargin: 4,
	        auto: autoscroll,
	        pause: autoscrollDuration,
	        loop: true,
	        currentPagerPosition: 'left',
	        mode: 'slide',
	        onAfterSlide: function(){
	            var currentSlide = carousel.getCurrentSlideCount(),
	                totalSlides = carousel.find('li').length,
	                // set variable names for the previous and next arrows
	                prev = carousel.closest('.lSSlideOuter').find('.lSPrev'),
	                next = carousel.closest('.lSSlideOuter').find('.lSNext');

	            // if the current slide is the first slide    
	            if (currentSlide===1){
	            	// and if the window width is less than 1025px       			
	            	if (jQuery(window).width() < 1025) {
	              		//hide the previous/next arrows
	              		prev.css("display", "none");
	              		next.css("display", "none");
	              	} else {
	              		// else fade them in/out
	              		prev.fadeOut(100);
	              		next.fadeIn(100);
	              	}
	            } else if (currentSlide===totalSlides){ // else if current slide is the last slide
	            	// and if the window width is less than 1025px
	            	if (jQuery(window).width() < 1025) {
	                	// hide the previous/next arrows
	                	prev.css("display", "none");
	                	next.css("display", "none");
	                } else {
	                	// else fade them in/out
	                	prev.fadeIn(100);
	                	next.fadeOut(100);
	                }
	              } else { // else for all other slides
		            // if window width is less than 1025px
		            if (jQuery(window).width() < 1025) {
	                // hide the previous/next arrows
	                prev.css("display", "none");
	                next.css("display", "none");
	              } else {
	                // else fade them in/out
	                prev.fadeIn(100);
	                next.fadeIn(100);
	              }
	            }
	          },

	          onSliderLoad: function(){
	          	var width = $this.closest('.carousel-wrapper').width(),
	          	thumbWidth = Math.ceil((width/items)-(items));
	          	sliderOuter.find('.lSGallery').find('li').css('min-width', thumbWidth+"px").css('max-width', thumbWidth+"px");

              // set variable names for the previous and next arrows
              prev = carousel.closest('.lSSlideOuter').find('.lSPrev'),
              next = carousel.closest('.lSSlideOuter').find('.lSNext');

              // if the window width is less than 1025px
              if (jQuery(window).width() < 1025) {
              // hide the previous/next arrows
              prev.css("display", "none");
              next.css("display", "none");
            } else {
            // else fade them in/out
            prev.fadeOut(100);
            next.fadeIn(100);
          }
        },
        
        onBeforeStart: function() {
            $('.js--carousel-image').css('display','block');
        }
      });

      var sliderOuter = carousel.closest('.lSSlideOuter');
      sliderOuter.find('.lSAction').addClass('desktop'); 

    });    
    
    jQuery('.quote-slider').lightSlider({
        item:1,
        controls:false,
        currentPagerPosition:'middle'
    });

    var events = jQuery('.event-slider').lightSlider({
        item:3,
        slideMove:3,
        slideMargin:20,
        controls:true,
        responsive:[
            {
                breakpoint:768,
                settings: {
                    item:1,
                    slideMove:1,
                }
            }
        ],
        onAfterSlide: function(){
            var currentSlide = events.getCurrentSlideCount(),
                totalSlides = events.closest('.lSSlideOuter').find('.lSpg li').length,
                prev = events.closest('.lSSlideOuter').find('.lSPrev'),
                next = events.closest('.lSSlideOuter').find('.lSNext');
            if(currentSlide===1){
                prev.fadeOut(100);
                next.fadeIn(100);
            } else if (currentSlide===totalSlides){
                prev.fadeIn(100);
                next.fadeOut(100);
            } else {
                prev.fadeIn(100);
                next.fadeIn(100); 
            }
        },
        
        onBeforeStart: function() {
            $('.js--carousel-image').css('display','block');
        }
    });

    var shop = jQuery('.shop-slider').lightSlider({
        item:1,
        slideMove:1,
        slideMargin:60,
        controls:true,
        currentPagerPosition:'middle',
        galleryMargin:10,
        onAfterSlide: function(){
            var currentSlide = shop.getCurrentSlideCount(),
                totalSlides = shop.find('li').length,
                prev = shop.closest('.shop-slider-wrapper').find('.lSPrev'),
                next = shop.closest('.shop-slider-wrapper').find('.lSNext');
            if(currentSlide===1){
                prev.fadeOut(100);
                next.fadeIn(100);
            } else if (currentSlide===totalSlides){
                prev.fadeIn(100);
                next.fadeOut(100);
            } else {
                prev.fadeIn(100);
                next.fadeIn(100); 
            }
        }

    });
    
    jQuery('.feature--sections').on('click', 'a', function(e){
        e.preventDefault();
        var item = jQuery(this),
            pos = item.parent().index(),
            list = jQuery(this).closest('.feature--sections'),
            content = list.next('.feature--content');
        list.find('.selected').removeClass('selected');
        content.find('.selected').removeClass('selected');
        item.parent().addClass('selected');
        content.find('li').eq(pos).addClass('selected');
    });

    jQuery('.image-info').on('click', function(){
        jQuery(this).toggleClass('open');
    });

    jQuery('.expandable').on('click', function(e){
        e.preventDefault();
        jQuery(this).toggleClass('open');
        jQuery(this).next().slideToggle();
    });

    jQuery('.play-video').on('click', function(e){
        e.preventDefault();
        var hero = jQuery(this).closest('.hero'),
            player = hero.find('.video-wrapper').data('player');
        hero.find('.video-wrapper, .close-video').addClass('open');
        jQuery('.youtubeplayer').fadeIn();
        if(player.playVideo){
            player.playVideo();
        }else{
            player.playWhenReady = true;
        }
        if (jQuery(window).width() < 768) { jQuery(this).closest('.promo-link').hide(); }
    });
    
    jQuery('.close-video').on('click', function(e){
        e.preventDefault();
        var $this = jQuery(this),
            player = $this.next('.video-wrapper').data('player');
        player.pauseVideo();
        $this.closest('.video-wrapper, .close-video').removeClass('open');
        jQuery('.youtubeplayer').hide();
        if (jQuery(window).width() < 768) { jQuery('.hero .promo-link').fadeIn(); }
    });

    // Still needed? Can't see any subnav-class elements in current HTML
    // jQuery('.subnav').on('click', 'a', function(e){
    //     e.preventDefault();
    //     var adjust = jQuery('.global-header').height() + jQuery('.subnav').height();
    //     jQuery('html, body').animate({ scrollTop: jQuery('a[name='+jQuery(this).attr('href').replace('#','')+']').offset().top - adjust }, 1000);
    // });

    // Dynamic hover code to override CSS and provide a bit of delay before open/close
    jQuery('.level-1 > .nav-list__item.has-children').hoverIntent({ 
        over: function(){ jQuery(this).addClass('open'); }, 
        out: function(){ jQuery(this).removeClass('open'); }, 
        timeout: 200
    });

    // Megamenu click handling

    // if the width of the window is less than 768px i.e. mobile screen size...
    if(jQuery(window).width() < 768){
    // if a main nav link is clicked...
      jQuery('.level-1 > .nav-list__item.has-children').on('click', function(e){
        // set variable for "this"
        var $this = jQuery(this);

        // if the main nav link has children i.e. has a sub-nav...
      	if(jQuery(e.target).closest('li').hasClass('has-children')){
					// stop touch acting as a click on items with submenus
					e.preventDefault();

					// if the main nav link already has a "selected" class, remove all the classes that make it "selected"
					if($this.hasClass('selected')) {
						$this.removeClass('touch');
            jQuery('.global-menu-trigger').removeClass('return');
            $this.removeClass('selected').siblings().removeClass('selected-siblings');
          } else {
          // else add all the classes that make it "selected"
						$this.addClass('touch');
            jQuery('.global-menu-trigger').addClass('return');
            jQuery('.nav-list__item').removeClass('selected');
            $this.addClass('selected').siblings().addClass('selected-siblings');
          }
        }
      });

      // if a link in the sub-nav is clicked...
      jQuery('.level-2 > .nav-list__item.has-children').on('click', function(e){
      // allow the link to work as normal
      	return true;
      // set variable for "this"
        var $this = jQuery(this);

        // if the main nav link already has a "selected" class, remove all the classes that make it "selected" and allow the link to work as normal
        if($this.hasClass('selected')) {
					return true;
          jQuery('.global-menu-trigger').removeClass('return');
          $this.removeClass('selected').siblings().removeClass('selected-siblings');
        }
      });
    } else {
	    // Megamenu touch handling for screens above 768px
	    jQuery('.level-1 > .nav-list__item.has-children').on('touchstart', function(e){
        if(jQuery(e.target).closest('li').hasClass('has-children')){
          e.preventDefault(); // stop touch acting as a click on items with submenus
          e.stopPropagation(); // stop a click event from also firing
          var $this = jQuery(this);

          if($this.hasClass('open')) {
            $this.removeClass('open').removeClass('touch');
          } else {
            jQuery('.nav-list__item').removeClass('open');
            $this.addClass('open').addClass('touch');
          }

          if(jQuery(window).width() < 768){
            if($this.hasClass('selected')) {
              jQuery('.global-menu-trigger').removeClass('return');
              $this.removeClass('selected').siblings().removeClass('selected-siblings');
            } else {
              jQuery('.global-menu-trigger').addClass('return');
              jQuery('.nav-list__item').removeClass('selected');
              $this.addClass('selected').siblings().addClass('selected-siblings');
            }
          }
        }
	    });
		}

    // Mobile nav
    jQuery('#mobile-navigation').on('click', function(e){
        e.preventDefault();
        if(jQuery('.global-menu-trigger').hasClass('return')) {
            jQuery('.nav-list__item').removeClass('selected selected-siblings');
            jQuery('.global-menu-trigger').removeClass('return');
        } else {
            jQuery('.global-nav-menu').toggle(function() {
                if(jQuery('.global-menu-trigger').hasClass('clicked')) {
                    jQuery('.global-menu-trigger, .global-nav-menu, .global-header-info').removeClass('clicked');
                } else {
                    jQuery('.global-menu-trigger, .global-nav-menu, .global-header-info').addClass('clicked');
                }
            });
        }
    });

    jQuery('#megamenu--search-bar__button').on('click', function(e){
        e.preventDefault();
        if(jQuery('.global-menu-trigger').hasClass('return')) {
            jQuery('.nav-list__item').removeClass('selected selected-siblings');
            jQuery('.global-menu-trigger').removeClass('return');
        } else {
            if(jQuery('.global-menu-trigger').hasClass('clicked')) {
                jQuery('.global-nav-menu').slideUp('slow');
                setTimeout(function() {
                    jQuery('.global-menu-trigger, .global-nav-menu').removeClass('clicked');
                }, 600);
                
            }
        }
        jQuery('html').addClass('js-noScroll');
        jQuery('.megamenu--search-bar').slideDown('slow');
        jQuery('.megamenu--search-bar__overlay').slideDown('slow');
        jQuery('.megamenu--search-bar__content').slideDown('slow');
    });

    jQuery('#megamenu--search-bar__close').on('click', function(e){
        e.preventDefault();
        jQuery('.megamenu--search-bar').slideUp('slow');
        jQuery('.megamenu--search-bar__overlay').slideUp('slow');
        jQuery('.megamenu--search-bar__content').slideUp('slow');
        setTimeout(function() {
            jQuery('html').removeClass('js-noScroll');
        }, 600);        
    });

    jQuery(document).scroll(function() {
        if (jQuery(window).width() > 767) {
            var position=jQuery(this).scrollTop(),
                // subNav = jQuery('.subnav'),
                mainNav = jQuery('.global-header'),
                mainBody = jQuery('.main-section'),
                globalHeaderBar = jQuery('.global-header-bar');
                // hero = jQuery('.hero'),
                // infoSection = jQuery('.row.info'),
                // heroPos = hero.position();

            if(position > globalHeaderBar.height()) {
                mainNav.addClass('sticky');
                mainBody.addClass('js-top-padding');
            } else {
                mainNav.removeClass('sticky');
                mainBody.removeClass('js-top-padding');
            }

            // if(!!heroPos && position >= heroPos.top + hero.height() - mainNav.height()){
            //     subNav.addClass('fixed');
            //     infoSection.addClass('fixed');

            //     jQuery('.subnav-section').each(function(i){
            //         var fixedNav = mainNav.height() + subNav.height(),
            //             section = jQuery(this);
            //         if(section.position().top <= position + fixedNav){
            //             jQuery('.subnav__item').removeClass('active').eq(i).addClass('active');
            //         }
            //     });
            // } else {
            //     subNav.removeClass('fixed');
            //     infoSection.removeClass('fixed');
            //     subNav.find('.subnav__item').removeClass('active');
            // }
        }
    });

    // IE8 interchange image shim - SVG support began with IE9
    if(!Modernizr.svg){
        $('[data-interchange]').each(function(){
            var icimg = $(this),
                match = icimg.attr('data-interchange').match(/.*\[(.*), \(large\)\]/m);
            if(match && match.length > 0){
                icimg.attr('src', match[1]);
            }
        });
    }
});

//WR-953 - TOR iFrame scrollbar fix supplied by TOR (Nov 2016)

//PARENT IFRAME NEEDS THIS SCRIPT
//browser compatibility: get method for event 
//addEventListener(FF, Webkit, Opera, IE9+) and attachEvent(IE5-8)
var myEventMethod = window.addEventListener ? "addEventListener" : "attachEvent";

//create event listener
var myEventListener = window[myEventMethod];

//browser compatibility: attach event uses onmessage
var myEventMessage = myEventMethod == "attachEvent" ? "onmessage" : "message";

//register callback function on incoming message
myEventListener(myEventMessage, function (e) {
//we will get a string (better browser support) and validate
//if it is an int - detect the height required by the iframe with class "js--tor-iframe", set the height of the iframe and add 350px
if (e.data === parseInt(e.data)) {
      $('.js--tor-iframe').height(e.data + 350);
  }                             
}, false);

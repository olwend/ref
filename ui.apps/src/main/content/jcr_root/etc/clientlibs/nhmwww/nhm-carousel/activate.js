jQuery(document).ready(function() {

    jQuery(document).foundation();

    var carousel = jQuery('.carousel').lightSlider({
        gallery:false,
        item:1,
        controls:true,
        thumbItem:5,
        thumbMargin:4,
        currentPagerPosition:'middle',
        mode:'slide',
        onAfterSlide: function(){
            var currentSlide = carousel.getCurrentSlideCount(),
                totalSlides = carousel.find('li').length,
                prev = carousel.closest('.lSSlideOuter').find('.lSPrev'),
                next = carousel.closest('.lSSlideOuter').find('.lSNext');
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
});
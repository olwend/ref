$(document).ready(function() {
    /** WR-1335 - resize single and filter for 1-3 row **/
    $('.dinosaurfilter--dinosaur').each(function() {

        var dinosaur = $(this),
            nameWidth = dinosaur.find(".dinosaurfilter--name-unhyphenated")[0].scrollWidth, // nameWidth checks the full width of the element
            containerWidth = dinosaur.width(); // containerWidth checks the displayed width of the element
        if (nameWidth > containerWidth) { // If in portrait mode and full name width is greater than displayed width, i.e. overflowing
            dinosaur.find(".dinosaurfilter--name-unhyphenated").hide();
            dinosaur.find(".dinosaurfilter--name-hyphenated").show();
        }
    });

});

$(document).ready(function() {
    /** WR-1335 - resize single and filter for 1-3 row **/
    $('.dinosaurfilter--dinosaur').each(function() {

        var dinosaur = $(this),
            nameWidth = dinosaur.find(".dinosaurfilter--name-unhyphenated")[0].scrollWidth,
            containerWidth = dinosaur.width();
        if (nameWidth > ( containerWidth + 2 )) { // containerWidth has 2px added to account for variance in Foundation's block-grid column sizing
            dinosaur.find(".dinosaurfilter--name-unhyphenated").hide();
            dinosaur.find(".dinosaurfilter--name-hyphenated").show();
        }
    });

});

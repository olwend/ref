$(document).ready(function() {

    //$("#question").click(function() {
    $('a').click(function() {
        var targetElement = $("#"+$(this).attr('rel'));
        targetElement.show();

        targetElement.siblings("div").hide();
    });

});
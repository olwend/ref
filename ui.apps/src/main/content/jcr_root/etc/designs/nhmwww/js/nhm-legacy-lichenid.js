/*
function to fix the position of the page when you click on a link which scroll to a different #achor, before the sticky nav was added the #anchor would scroll to the top of the page but now the sticky nav covers it so have used offset to position it below the nav. chrome and IE gets a different  offset to FF so the #anchor is positioned a little further down the page.
this only happens when you click a link from http://www.nhm.ac.uk/nature-online/life/plants-fungi/lichen-id-guide/help.dsml to www.nhm.ac.uk/nature-online/life/plants-fungi/lichen-id-guide/glossary.dsml
*/ 

function anchorTagPosition() {

    if (window.location.hash) {
        var anchorName = window.location.hash.replace("#" , "");
        $('html, body').animate({scrollTop: $("[name='" + anchorName + "']").offset().top -170});
    } else {
        return false;
    }

}

window.onload = anchorTagPosition;
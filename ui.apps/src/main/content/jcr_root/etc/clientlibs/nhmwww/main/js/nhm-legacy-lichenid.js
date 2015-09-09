function anchorTagPosition() {

    if (window.location.hash) {
        var anchorName = window.location.hash.replace("#" , "");
        var name = $("[name='" + anchorName + "']");
        console.log(name);
        console.log(name.offset());
        $('html, body').animate({scrollTop: $("[name='" + anchorName + "']").offset().top -170});
    } else {
        return false;
    }

}

window.onload = anchorTagPosition;
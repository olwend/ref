$(document).ready(function(){

    //scrollto function for butmoth - classification.html
    $('.legacy-css #microsite-body form select[name="SuperList"]').on('change', function(){
		var selectValue = $(this).val();
        //removes unwanted parts of selectValue
        var re= /.+#/
		var newSelectValue = selectValue.replace(re,'');
        $('html, body').animate({scrollTop: $("[name='" + newSelectValue + "']").offset().top -80});
    });

});
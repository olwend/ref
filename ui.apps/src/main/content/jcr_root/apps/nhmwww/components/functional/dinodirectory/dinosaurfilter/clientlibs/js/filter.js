
$(document).on("change", ".filterDinosaurs", function () {
    var filterValue = $(this).val();
    var row = $('.dinosaur'); 
      
    row.hide()
    row.each(function(i, el) {
         if($(el).attr('data-body-shape') == filterValue) {
             $(el).show();
         }
    })


    if(filterValue == "All") {
        row.show();
    } 
     
});
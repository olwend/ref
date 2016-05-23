$(document).ready(function () {

    document.getElementById("showMore").addEventListener("click", function () {
        $(this).hide();
        var tbody = document.getElementById("datesTable").tBodies[0];
        var trArrray = tbody.getElementsByTagName("tr");
        
        for (var i = 0; i < trArrray.length; i++) {
            var td = trArrray[i].getElementsByTagName("td");
            $(td).show();
            $(window).trigger('resize');
        }        
    });

});
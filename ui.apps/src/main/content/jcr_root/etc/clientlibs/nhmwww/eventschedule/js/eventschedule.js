$(document).ready(function () {
    //Function to display more results on the Events Dates table
    var showMoreEl = document.getElementById('showMore');
    if(showMoreEl){
        showMoreEl.addEventListener('click', function () {
            $(this).hide();
            var tbody = document.getElementById('datesTable').tBodies[0];
            var trArray = tbody.getElementsByTagName('tr');

            for (var i = 0; i < trArray.length; i++) {
                var td = trArray[i].getElementsByTagName('td');
                $(td).show();
                $(window).trigger('resize');
            }        
        });
    }
});
/* The follwing variables can be used to configure
 * the behaviour of the wayfinding events display.
 *
 * pageLength:  	The number of events to show per page.
 * ajaxUrl:     	URL of XML file to read events from.
 * defDisplay:  	Default HTML to display if no events can be loaded.
 * displayDuration:     Either the total length of time this display appears for in the playlist (ms)
 *			or the length of time to display each page for (see divideDuration)
 * headline:		The header text for the top of the page.
 * divideDuration:	Divide the display duration equally between pages (true) or display each page
 *			for the displayDuration (false)
 *
 * */

var windowProtocol = window.location.protocol;
var windowHost = window.location.host;
var XMLpath = "content/nhmwww/visitorfeed.xml";
var pageLength = 4;
// var ajaxUrl = "xml/day.html";
// var ajaxUrl = "http://localhost:4502/content/nhmwww/visitorfeed.xml";
// var ajaxUrl = "http://localhost:4503/content/nhmwww/visitorfeed.xml";
// var ajaxUrl = "https://wcm-pblsh1-dev.nhm.ac.uk/content/nhmwww/visitorfeed.xml";
// var ajaxUrl = "https://aem-pblsh2-stg.nhm.ac.uk:5434/content/nhmwww/visitorfeed.xml";
// var ajaxUrl = "https://aem-disp1-stg.nhm.ac.uk/content/nhmwww/visitorfeed.xml";
// var ajaxUrl = "https://staging.nhm.ac.uk/content/nhmwww/visitorfeed.xml";
//var ajaxUrl = "https://www.nhm.ac.uk/content/nhmwww/visitorfeed.xml";
var ajaxUrl = windowProtocol + "//" + windowHost + "/" + XMLpath;
var defDisplay = '<img src="/content/dam/nhmwww/wayfindingscreen/emergency.jpg" alt="" />';
var displayDuration = 20000;
var headline = "What&#146;s on today";
var divideDuration = false;

/*
 *
 *
 * */

var events;
var eventsBuffer;
var poll;
var slide;
var curPage = 0;

function blankScreen() {
    if((typeof eventsBuffer == 'undefined') || (eventsBuffer.length == 0)) {
        $('#main').html(defDisplay);
    }
}

function loadEvents() {
    $.get(ajaxUrl, function( data ) {
        eventsBuffer = $(data).find("item");
        if(eventsBuffer.length > events.length) {
            window.location.reload();
        }
    },"xml");
}

function buildTable( itemPointer ) {
    var table = $("<table></table>");

    for(i = 0; i < pageLength; i++) {
        var row = $("<tr></tr>");
        var left = $("<td></td>");
        var right = $("<td></td>");
        left.addClass("left");
        if(i == (pageLength - 1)) left.addClass("last");
        row.append(left);
        row.append(right);
        table.append(row);

        if(itemPointer < events.length) {
            var curItem = $(events.get(itemPointer));

            var time = $("<h2></h2>");
            var location = $("<p></p>");
            var eventType = $("<p></p>");
            time.html(curItem.find('custom_3').text());
            location.html(curItem.find('placemark > name').text());
            eventType.html(curItem.find('categories').text());
            left.append(time);
            left.append(location);
            left.append(eventType);

            var title = $("<h2></h2>");
            var description = $("<p></p>");
            var price = $("<p></p>");
            var audienceInfo = $("<p></p>");
            var audienceType;
            // var info = $("<p></p>");
			// var free = curItem.find('custom_2').text();
			// var fee;

   //          // fix for "light up dippy" - miks - 2011/11
   //          var tempTitle = curItem.find('title').text();

   //          if(free.indexOf('Free') > -1) {

   //              // this if/else is nasty hack to fix this for dippy (which is not really free, but doesn't have an admission fee as such either) - miks - 2011/11
   //              if (tempTitle != 'Light up Dippy') {
			// 		fee = "Free";   // original line - everything else in this block is to do with the dodgy hack
   //              } else {
			// 		fee = "";
   //              }

   //          } else {
   //                  fee = "Admission fee applies";
   //          }
   //          // end fix for "light up dippy" - miks - 2011/11

			// var eventStatus = curItem.find('status').text();
			// if(eventStatus.length > 0) {
			// 	fee = '<span class="highlight">' + eventStatus + '</span>';
			// }

			// var booking = curItem.find('booking').text();
			// if(booking.length > 0) {
			// 	booking = booking + ", " + fee.toLowerCase();
			// } else {
			// 	booking = fee;
			// }

            title.html(curItem.find('> name').text());
            description.html(curItem.find('description').text());
            price.html(curItem.find('custom_2').text());
			// var audience = curItem.find('audience').text().replace('aged all ages','of all ages');
            var audienceType = curItem.find('custom_1').text();

            if (audienceType) {
                audienceInfo.html("Suitable for "  + audienceType);
            } else {
                audienceInfo.html("suitable for is blank");
            }

            right.append(title);
            right.append(description);
            right.append(audienceInfo);
            right.append(price);

            itemPointer++;
        }
    }
    return table;
}

function buildInitial() {
	if(eventsBuffer) {
		if(eventsBuffer.length > 0) {
	        events = eventsBuffer;
	        window.clearInterval(poll);
	        var table = buildTable(0);
	        $('#main').html('<div id="header"><h1>' + headline + '</h1></div>');
	        $('#main').append(table);
	        curPage = 1;
			var pageDisplay;
			if(divideDuration) {
				var numPages = Math.ceil(events.length / pageLength);
				pageDisplay = Math.ceil(displayDuration / numPages);
			} else {
				pageDisplay = displayDuration;
			}
	        slide = window.setInterval(slidePage,pageDisplay);
	        poll = window.setInterval(loadEvents,10000);
	    }
	}
}

function slidePage() {

    var pageStart = curPage * pageLength;
    if(pageStart >= events.length) {
        if(eventsBuffer.length > 0) events = eventsBuffer;
        curPage = 0;
        pageStart = 0;
    }
    var table = buildTable(pageStart);
    table.hide();
    table.css('z-index',1);
    $('#main').append(table);
	table.show();
	/*$('table:first').remove();
    table.css('z-index', 0);*/

  /*  table.slideDown(2000, function() {
        $('table:first').remove();
        $(this).css('z-index', 0);
    });*/
    curPage++;
}

$(document).ready(function() {
    loadEvents();
    poll = window.setInterval(buildInitial, 10);
    window.setTimeout(blankScreen,500);
});
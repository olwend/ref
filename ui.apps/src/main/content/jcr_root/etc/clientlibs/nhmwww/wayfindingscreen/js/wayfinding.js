/* The following variables can be used to configure
 * the behaviour of the wayfinding events display.
 *
 * pageLength:    The number of events to show per page.
 * ajaxUrl:       URL of XML file to read events from.
 * defDisplay:    Default HTML to display if no events can be loaded.
 * displayDuration:     Either the total length of time this display appears for in the playlist (ms)
 *      or the length of time to display each page for (see divideDuration)
 * headline:    The header text for the top of the page.
 * divideDuration:  Divide the display duration equally between pages (true) or display each page
 *      for the displayDuration (false)
 *
 * */

var windowProtocol = window.location.protocol;
var windowHost = window.location.host;
var XMLpath = "content/nhmwww/visitorfeed.xml";
var pageLength = 4;
var ajaxUrl = windowProtocol + "//" + windowHost + "/" + XMLpath;
var defDisplay = '<img src="/content/dam/nhmwww/home/events-calendar/n-placeholder.jpg" alt="" />';
var displayDuration = 20000;
var headline = "What&#146;s on today";
var divideDuration = false;

/*
 *
 *
 * */

var events;
var eventsBuffer = [];
var poll;
var slide;
var curPage = 0;

function blankScreen() {
    if((typeof eventsBuffer == 'undefined') || (eventsBuffer.length == 0)) {
        $('#main').html(defDisplay);
    }
}

function loadEvents() {
    eventsBuffer = [];
    $.get(ajaxUrl, function( data ) {
        $(data).find("item").each(function () {
            var calendar_type = $(this).find('calendar_type').text();
            
            // Get DateTime of event end - Format = "MMM DD, YYYY HH:mm"
            var calendar_time = $(this).find('dtend').text(); 
            // Convert DateTime string to Date object format and select only the time component as an object
            var calendar_datetime = new Date(calendar_time).getTime(); 
            // Select simple time field to check for All Day events
            var calendar_time_simple = $(this).find('custom_3').text();
            // Get time now
            var time_now = new Date().getTime(); 
            // Only append if event is tagged Visitor
            if (calendar_type === "Visitor") {  
                // Only append if event starts after current time, or event is All Day
                if (calendar_datetime > time_now || calendar_time_simple=="All day") { 
                    eventsBuffer.push($(this));
                }
            }
        });

        /* Sort array */
        eventsBuffer.sort(function (a, b) {
            var timeA = a.find('custom_3').text();
            var timeB = b.find('custom_3').text();
            if (timeA < timeB) { return -1; }
            if (timeA > timeB) { return 1; }
        });
        /* End sort */

        if (events) {
        if(eventsBuffer.length > events.length) {
            window.location.reload();
        }
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
            var curEventBit = events[itemPointer];
            var curItem = $(curEventBit.get());
                var time = $("<h2></h2>");
                var location = $("<p></p>");
                var eventType = $("<p></p>");
                time.html(curItem.find('custom_3').text().replace(":", "."));
                location.html(curItem.find('placemark > name').text());

                // WR-946-wayfinding-tag-names
                // Sets event categories in an array to lowercase
                var eventString = curItem.find('categories').text();
                    // Split string at first comma
                var tmp_eventString = eventString.split(/,(.+)/);
                    // Check if a second array item exists (i.e. more than one item in the array)
                if (tmp_eventString[1]) {
                    tmp_eventString[1] = tmp_eventString[1].toLowerCase();
                    eventString = tmp_eventString[0].concat(", ", tmp_eventString[1]);
                }

                eventType.html(eventString);

                left.append(time);
                left.append(location);
                left.append(eventType);

                var title = $("<h2></h2>");
                var description = $("<p></p>");
                var price = $("<p></p>");
                var audienceInfo = $("<p></p>");
                var audienceType;

                title.html(curItem.find('> name').text());
                description.html(curItem.find('description').text());
                price.html(curItem.find('custom_2').text());
                // var audience = curItem.find('audience').text().replace('aged all ages','of all ages');
                var audienceType = curItem.find('custom_1').text().toLowerCase();

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
    curPage++;
}

$(document).ready(function() {
    loadEvents();
    poll = window.setInterval(buildInitial, 10);
    window.setTimeout(blankScreen,500);
});
$(document).ready(function() {

	var questionCount = $("a[id='question']").length - 1;

    $("a#question").click(function() {
        var targetElement = $("#"+$(this).attr('rel'));
        targetElement.show();
        targetElement.siblings("div").hide();

		var curRel = $(this).attr('rel');

        //Work out and set new rel
        var num = parseInt(curRel.match(/\d+$/));
        var pos = curRel.indexOf(num);
        var str = curRel.slice(0,pos);
        
        var prevNum = num - 1;
        var nextNum = num + 1;

        if(prevNum == -1) {
        	var prevRel = "";
        } else {
        	var prevRel = str + (prevNum);
        }

        var prevElement = $('#prev-question');
		prevElement.attr('rel', prevRel);

        if(nextNum > questionCount) {
            var nextRel = ""
        } else {
			var nextRel = str + (nextNum);
        }

        var nextElement = $('#next-question');
        nextElement.attr('rel', nextRel);
    });

    $('#next-question').click(function() {
        var targetElement = $("#"+$(this).attr('rel'));
		var curRel = $(this).attr('rel');

        if(curRel != "") {
            targetElement.show();
            targetElement.siblings("div").hide();

            //Work out and set new rel
            var num = parseInt(curRel.match(/\d+$/));
            var pos = curRel.indexOf(num);
            var str = curRel.slice(0,pos);
    
            var prevNum = num - 1;
            var nextNum = num + 1;
    
            var prevRel = str + (prevNum);

        	if(nextNum > questionCount) {
                var nextRel = "";
            } else {
            	var nextRel = str + (nextNum);
            }
    
            $(this).attr('rel', nextRel);
    
            var prevElement = $('#prev-question');
            prevElement.attr('rel', prevRel);
        }

    });

    $('#prev-question').click(function() {
        var targetElement = $("#"+$(this).attr('rel'));
		var curRel = $(this).attr('rel');

        if(curRel != "") {
            targetElement.show();
            targetElement.siblings("div").hide();

            //Work out and set new rel
            var num = parseInt(curRel.match(/\d+$/));
            var pos = curRel.indexOf(num);
            var str = curRel.slice(0,pos);
    
            var prevNum = num - 1;
            var nextNum = num + 1;
    
            if(prevNum == -1) {
                var prevRel = "";
            } else {
                var prevRel = str + (prevNum);
            }
            var nextRel = str + (nextNum);
    
            $(this).attr('rel', prevRel);
    
            var nextElement = $('#next-question');
            nextElement.attr('rel', nextRel);
        }
    });

});
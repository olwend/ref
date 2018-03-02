
    $interactive = $('#bigsplash-interactive #filter');
    $title = $('.bigsplash-text--title');
    $wrap = $('.wrap');
    hide = false;

    $(".animal").mouseenter(function() {
        var audio = $('#' + this.id + '-sound')[0];
        if(audio) {
            audio.play();
        }
        $('#' + this.id + '-glowballs').toggleClass('glow');
    });

    $(".animal").mouseleave(function() {
        var audio = $('#' + this.id + '-sound')[0];
        if(audio) {
            audio.pause();
            audio.currentTime = 0;
        }

        $('#' + this.id + '-glowballs').toggleClass('glow');
    });


function update(e){
    var x = e.clientX || e.touches[0].clientX;
    var y = e.clientY || e.touches[0].clientY;
   // $interactive.css('--cursorX', x + 'px');  jquery 1.11 (website) doesnt understand these CSS rules.
   // $interactive.css('--cursorY', y + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorX', x + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorY', y + 'px');
   }


function torch() {
     if(!hide) {
         hide = true;
         $interactive.on('mousemove', update);
         $interactive.on('touchmove', update);
         //$interactive.on('mouseout', removeTorch);
         //$interactive.on('mouseleave', removeTorch);
         $interactive.toggleClass('glow');
         $title.toggleClass('glow');
         $wrap.toggleClass('glow');
         $('.animal').toggleClass('glow');
     }

    else {
         hide=false;
         $interactive.off('mousemove', update);
         $interactive.off('touchmove', update);
         //$interactive.off('mouseout', removeTorch);
         //$interactive.off('mouseleave', removeTorch);
         $interactive.toggleClass('glow');
         $title.toggleClass('glow');
         $wrap.toggleClass('glow');
         $('.animal').toggleClass('glow');
     }
}


function removeTorch() {
    $interactive.addClass('hideOut');
  }



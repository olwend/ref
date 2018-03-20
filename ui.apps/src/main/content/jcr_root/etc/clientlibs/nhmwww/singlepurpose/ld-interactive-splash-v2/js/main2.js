
$interactive = $('#bigsplash-interactive #filter');
$title = $('.bigsplash-text--title');
$wrap = $('.wrap');
glow = false;

window.addEventListener('load', function() {
    $('#bigsplash-interactive').addClass('animate');
}, true);


$(".animal").mouseenter(function() {
    var audio = $('#' + this.id + '-sound')[0];
    if(audio) {
        audio.play();
    }
    $('#' + this.id + '-glowballs').fadeOut(700);
});

$(".animal").mouseleave(function() {
    var audio = $('#' + this.id + '-sound')[0];
    if(audio) {
        audio.pause();
        audio.currentTime = 0;
    }
    $('#' + this.id + '-glowballs').fadeIn(1700);
});



function torch() {
    if(!glow) {

        glow = true;
        //$interactive.on('mousemove', update);
        //$interactive.on('touchmove', update);
        //$interactive.on('mouseout', removeTorch);
        //$interactive.on('mouseleave', removeTorch);
        $interactive.toggleClass('glow');
        $wrap.toggleClass('glow');
        $('.animal').toggleClass('glow');
        $('#bigsplash-interactive').toggleClass('showLayers');

        if (!/Mobi/.test(navigator.userAgent)) {
            // not mobile!
            $title.toggleClass('glow');
        }
    }

    else {
        glow =false;
        //$interactive.off('mousemove', update);
        //$interactive.off('touchmove', update);
        //$interactive.off('mouseout', removeTorch);
        //$interactive.off('mouseleave', removeTorch);
        $interactive.toggleClass('glow');
        $wrap.toggleClass('glow');
        $('.animal').toggleClass('glow');
        $('#bigsplash-interactive').toggleClass('showLayers');

        if (!/Mobi/.test(navigator.userAgent)) {
            // not mobile!
            $title.toggleClass('glow');
        }
    }
}


/* Torch effect
function update(e){
    var x = e.clientX || e.touches[0].clientX;
    var y = e.clientY || e.touches[0].clientY;
    // $interactive.css('--cursorX', x + 'px');  jquery 1.11 (website) doesnt understand these CSS rules.
    // $interactive.css('--cursorY', y + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorX', x + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorY', y + 'px');
   }

function removeTorch() {
    $interactive.addClass('hideOut');
  }
  */





    $interactive = $('#bigsplash-interactive');
    $title = $('.bigsplash-text--title');
    $wrap = $('.wrap');
    hide = false;


function update(e){
    var x = e.clientX - $('#bigsplash-interactive').offset().left  || e.touches[0].clientX - $('#bigsplash-interactive').offset().left;
    var y = e.clientY - $('#bigsplash-interactive').offset().top || e.touches[0].clientY - $('#bigsplash-interactive').offset().top;
    //$interactive.css('--cursorX', x + 'px');
    //$interactive.css('--cursorY', y + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorX', x + 'px');
    document.getElementById('bigsplash-interactive').style.setProperty('--cursorY', y + 'px');
    console.log('--cursorX:' + $interactive.css('--cursorX'));
   }


function torch() {
     if(!hide) {
         hide = true;
         $interactive.on('mousemove', update);
         $interactive.on('touchmove', update);
         //$interactive.on('mouseout', removeTorch);
         //$interactive.on('mouseleave', removeTorch);
         $interactive.addClass('dark');
         $title.addClass('dark');
         $wrap.addClass('dark');
     }

    else {
         hide=false;
         $interactive.off('mousemove', update);
         $interactive.off('touchmove', update);
         //$interactive.off('mouseout', removeTorch);
         //$interactive.off('mouseleave', removeTorch);
         $interactive.removeClass('dark');
         $title.removeClass('dark');
         $title.removeClass('dark');
         $wrap.removeClass('dark');
     }
}


function removeTorch() {
    $interactive.addClass('torchOut');
  }

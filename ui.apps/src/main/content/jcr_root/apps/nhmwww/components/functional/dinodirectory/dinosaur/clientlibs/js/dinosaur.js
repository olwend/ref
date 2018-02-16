/** WR-1213 - Single dino page size comparison **/
$(document).ready(function() {
  var humanSize = 1, // Set manually here
    dinoSize = parseInt(jQuery('.dinosaur--description').html().match(/\d{1,3}[m]/).toString().slice(0,-1)),
    ratioDinoHuman = ( humanSize / dinoSize ) * 100,
    totalSize = humanSize + dinoSize,
    dinoWidth = ( 100 / totalSize ) * dinoSize,
    humanWidth = ( 100 / totalSize ) * humanSize,
    humanOutline = 'human-look-up';

  // console.log('Ratio: ' + ratioDinoHuman);
  // console.log(dinoSize);

  if ( ratioDinoHuman >= 75 ) {
    humanOutline = 'human-crouching';
  } else if ( ratioDinoHuman >= 25 ) {
    humanOutline = 'human-look-down';
  } else if ( ratioDinoHuman >= 15 ) {
    humanOutline = 'human-look-ahead';
  }

  jQuery('.dinosaur--comparison-human').html("<img src='/etc/designs/nhmwww/img/svgs/dinodirectory/human/"+humanOutline+".svg'/>").css('width', humanWidth+'%');
  jQuery('.dinosaur--comparison-dino').css('width', dinoWidth+'%');
});
/** End WR-1213 **/
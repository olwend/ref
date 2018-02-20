/** WR-1213 - Single dino page size comparison **/
$(document).ready(function() {
  var dinoSize = parseInt(jQuery('.dinosaur--description').html().match(/\d{1,3}[m]/).toString().slice(0,-1)),
    humanSize = 1.1; // Default human size for larger dinosaurs

  // Sliding scale of human size to give a better layout on smaller dinosaurs
  if (dinoSize < 5) { humanSize = 0.7; } else
  if (dinoSize < 10) { humanSize = 0.9; }

  var ratioDinoHuman = ( humanSize / dinoSize ) * 100,
    totalSize = humanSize + dinoSize,
    dinoWidth = ( 100 / totalSize ) * dinoSize,
    humanWidth = ( 100 / totalSize ) * humanSize,
    humanOutline = 'human-look-up', // Default human outline for larger dinosaurs
    margin = 0;

  // console.log('Ratio: ' + ratioDinoHuman);
  // console.log(dinoSize);

  // Change human outline base on ratio of dinosaur size to human size
  if ( ratioDinoHuman >= 8 ) {
    humanOutline = 'human-crouching';
    margin = -120
  } else if ( ratioDinoHuman >= 35 ) {
    humanOutline = 'human-look-down';
  } else if ( ratioDinoHuman >= 15 ) {
    humanOutline = 'human-look-ahead';
  }

  jQuery('.dinosaur--comparison-human').html("<img src='/etc/designs/nhmwww/img/svgs/dinodirectory/human/"+humanOutline+".svg'/>").css('width', humanWidth+'%').css('margin-top', margin+'px');
  jQuery('.dinosaur--comparison-dino').css('width', dinoWidth+'%');
});
/** End WR-1213 **/
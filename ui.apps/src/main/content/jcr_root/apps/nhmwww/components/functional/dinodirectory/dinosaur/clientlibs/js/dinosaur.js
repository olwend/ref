$(document).ready(function() {
  /** WR-1213 - Single dino page size comparison **/
  var dinoSize = parseFloat(jQuery('.dinosaur--description').data('dino-length')),
    humanSize = 1.0; // Default human size for larger dinosaurs

  // Sliding scale of human size to give a better layout on smaller dinosaurs
  if (dinoSize <= 5) { humanSize = 0.8; }

  var ratioDinoHuman = ( humanSize / dinoSize ) * 100,
    totalSize = humanSize + dinoSize,
    dinoWidth = ( 100 / totalSize ) * dinoSize,
    humanWidth = ( 100 / totalSize ) * humanSize,
    humanOutline = 'human-look-up', // Default human outline for larger dinosaurs
    humanSuffix = '';

  // Change human outline base on ratio of dinosaur size to human size
  if ( ratioDinoHuman >= 30 ) {
    humanOutline = 'human-crouching';
  // } else if ( ratioDinoHuman >= 20 ) {
    // humanOutline = 'human-look-down';
  } else if ( ratioDinoHuman >= 25 ) {
    humanOutline = 'human-look-ahead';
  }

  // Determine if the outline should be male or female
  Math.random() < 0.5 ? humanSuffix = '-m' : humanSuffix = '-f';

  jQuery('.dinosaur--comparison-human').html("<img src='/etc/designs/nhmwww/img/svgs/dinodirectory/human/"+humanOutline+humanSuffix+".svg'/>").css('width', humanWidth+'%');
  jQuery('.dinosaur--comparison-dino').css('width', dinoWidth+'%');
  /** End WR-1213 **/

  /** WR-1300 - Hyphenated dinosaur names **/
  function nameHyphenate() {
    var nameFullWidth = jQuery('.dinosaur--name-unhyphenated')[0].scrollWidth, // nameFullWidth checks the full width of the element
      nameDisplayedWidth = jQuery('.dinosaur--name-unhyphenated').innerWidth(); // nameDisplayedWidth checks the displayed width of the element

    if ( window.innerWidth > window.innerHeight ) { // Quick "is this in landscape mode" check
      jQuery('.dinosaur--name-unhyphenated').show();
      jQuery('.dinosaur--name-hyphenated').hide();
    } else if ( nameFullWidth > nameDisplayedWidth ) { // If in portrait mode and full name width is greater than displayed width, i.e. overflowing
      jQuery('.dinosaur--name-unhyphenated').hide();
      jQuery('.dinosaur--name-hyphenated').show();
    }
  };

  // Run the function immediately
  nameHyphenate();

  // Run the function on window resize, i.e. move between portrait and landscape
  jQuery(window).resize(nameHyphenate);
  /** End WR-1300 **/

});

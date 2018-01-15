var searchTypes = [ '', 'default_tab', 'print_tab', 'elec_tab' ];
var images = [ 'library-search-asia-reeves.jpg', 'library-search-blatterzeolith.jpg', 'library-search-dragonfly.jpg', 'library-search-tufted-auk.jpg', 'library-search-water-lillies.jpg' ];

$(document).ready(function() {
	// random search panel background
	$('.primo-search-container').css({'background-image' : 'url(/etc/designs/nhmwww/img/archivessearch/' + images[Math.floor(Math.random() * images.length)] + ')' });
});

function searchPrimo() {
	document.getElementById("primoQuery").value = "any,contains," + document.getElementById("primoQueryTemp").value.replace(/[,]/g, " ");
	document.forms["PrimoSearchForm"].submit();
}	
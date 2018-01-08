var searchTypes = [ '', 'default_tab', 'print_tab', 'elec_tab' ];
var images = [ 'Vines.jpg', 'Caterpillar.jpg', 'Jellyfish.jpg', 'Map.jpg', 'Swan.jpg' ];

$(document).ready(function() {
	// random search panel background
	$('.primo-search-container').css({'background-image' : 'url(/etc/designs/nhmwww/img/archivessearch/' + images[Math.floor(Math.random() * images.length)] + ')' });
});

function searchPrimo() {
	document.getElementById("primoQuery").value = "any,contains," + document.getElementById("primoQueryTemp").value.replace(/[,]/g, " ");
	document.forms["PrimoSearchForm"].submit();
}	
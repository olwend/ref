var searchTypes = [ '', 'default_tab', 'print_tab', 'elec_tab' ];
var images = [ 'Vines.jpg', 'Caterpillar.jpg', 'Eyes.jpg', 'Jellyfish.jpg', 'Map.jpg', 'Swan.jpg' ];

$(document)
		.ready(
				function() {
					// random search panel background
					$('.primo-search-container').css({'background-image' : 'url(/etc/designs/nhmwww/img/archivessearch/' + images[Math.floor(Math.random() * images.length)] + ')' });

					// get which current tab in form (i.e. form entered via back
					// button
					currentTab = $("#PrimoSearchForm input[name=tab]").val();

					for (i = 1; i <= 3; i++) {
						if (currentTab == searchTypes[i]) {
							$('#tab' + i).addClass('selected');
						} else {
							$('#tab' + i).removeClass('selected');
						}
					}
				});

function setSearchOptions(idElement) {
	tot_tab = 3;
	tab = document.getElementById('tab' + idElement);
	search_option = $("#PrimoSearchForm input[name=tab]");

	for (i = 1; i <= 3; i++) {
		if (i == idElement) {
			tab.setAttribute("class", "selected");
			// console.log(searchTypes[i]);
			search_option.val(searchTypes[i]);
			// console.log( search_option.val());
			// change background randomly - probably dont want this - dhis
			// $('.primo-search-container').css({'background-image':
			// 'url(/etc/designs/nhmwww/img/archivessearch/' +
			// images[Math.floor(Math.random() * images.length)] + ')'});
		} else {
			document.getElementById('tab' + i).setAttribute("class", "");
		}
	}
}
$(document).ready(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	var rootPath = $('.js-feed-wrapper').data('rootpath');
	var pageSize = $('.js-feed-wrapper').data('pagesize');
	
	if (rootPath && pageSize) {
		showFeeds(rootPath, 1, pageSize);
	}
});

function addMoreResultsButton() {
	var moreElementsDiv = document.createElement("div");
	
	moreElementsDiv.className = "row more-results";
	moreElementsDiv.id = "more_results";
	var aTag = document.createElement("a");
	var h5Tag = document.createElement("h5");
	h5Tag.id = "more_results_text";
	h5Tag.className = "more-results-text";
	h5Tag.innerHTML = "More results";
	aTag.appendChild(h5Tag);
	moreElementsDiv.appendChild(aTag);
	document.getElementById("js-feed-wrapper").appendChild(moreElementsDiv);
	
	$('.js-feed-wrapper .more-results').click(function(){
		var rootPath = $('.js-feed-wrapper').data('rootpath');
		var pageSize = $('.js-feed-wrapper').data('pagesize');
		removeMoreResultsButton();
		var elementsShowed = $('.feed--item').length;
		var elementsToAdd = pageSize;
		currentPage = elementsShowed / pageSize;
		showFeeds(rootPath, currentPage+1, pageSize);
		
	});
}

function removeMoreResultsButton() {
	 var wrapperDiv = document.getElementById('js-feed-wrapper');
	 var divToDelete = document.getElementById("more_results");
	 wrapperDiv.removeChild(divToDelete);

	
}
	

function showFeeds(rootPath, pageNumber, pageSize) {
	$.ajax({
		type: 'GET',    
		url: '/bin/list/pagination.json',
		data:{
			rootPath: rootPath,
			pageNumber: pageNumber,
			pageSize: pageSize
		},
		success: function(data){
			var json = jQuery.parseJSON(data);
			buildNavigators(pageNumber, json.pages);
			showItems(json.pageJson);
			if(pageNumber != json.pages){
				addMoreResultsButton();
			}
			$(document).foundation('reflow');
			$(document).foundation('interchange', 'reflow');
			
			
		},
		error: function (){
		}
	});
}

function buildNavigators(pageNumber, numberOfPages) {
	
	/*if (numberOfPages > 1) {
		$.each($('.js-feed-wrapper .pagination-centered'), function(index, item) { 
			//var pagination = document.createElement('ul');
			//pagination.className = 'pagination';
			
			///var toFirst = document.createElement('li');
			//if (pageNumber == 1) {
				toFirst.innerHTML = '<li class="arrow unavailable"><a href="#">«</a></li>';
			//} else {
			//	toFirst.innerHTML = '<li class="arrow"><a data-index="1" href="#">«</a></li>';
			//}
				
			var showMore = document.createElement('a');
			showMore.appendChild('h5');
			pagination.appendChild(toFirst);
			
			var toPrev = document.createElement('li');
			if (pageNumber == 1) {
				toPrev.innerHTML = '<li class="unavailable"><a data-index="' + (pageNumber - 1) + '" href="#">Prev</a></li>';
			} else {
				toPrev.innerHTML = '<li><a data-index="' + (pageNumber - 1) + '" href="#">Prev</a></li>';
			}
			pagination.appendChild(toPrev);
			
			for (i = 1; i <= numberOfPages; i++) {
				var toI = document.createElement('li');
				
				if (i == pageNumber) {
					toI.innerHTML = '<li class="current"><a data-index="' + i + '" href="#">' + i + '</a></li>'
				} else {
					toI.innerHTML = '<li><a data-index="' + i + '" href="#">' + i + '</a></li>'
				}
				
				pagination.appendChild(toI);
			}
			
			var toNext = document.createElement('li');
			if (pageNumber == numberOfPages) {
				toNext.innerHTML = '<li class="unavailable"><a data-index="' + (pageNumber + 1) + '" href="#">Next</a></li>';
			} else {
				toNext.innerHTML = '<li><a data-index="' + (pageNumber + 1) + '" href="#">Next</a></li>';
			}
			pagination.appendChild(toNext);
			
			var toLast = document.createElement('li');
			if (pageNumber == numberOfPages) {
				toLast.innerHTML = '<li class="arrow unavailable"><a data-index="' + numberOfPages + '" href="#">»</a></li>';
			} else {
				toLast.innerHTML = '<li class="arrow"><a data-index="' + numberOfPages + '" href="#">»</a></li>';
			}
			pagination.appendChild(toLast);
			
			$(this).append(pagination);
			
			$('.js-feed-wrapper .pagination-centered .pagination li:not(.unavailable) a').click(function(){
				var to = $(this).data('index');
				
				if ($(this).parent().hasClass('unavailable')) {
					return false;
				}
				
				var rootPath = $('.js-feed-wrapper').data('rootpath');
				var pageSize = $('.js-feed-wrapper').data('pagesize');
				
				if (rootPath && pageSize) {
					$('.js-feed-wrapper .pagination-centered').empty();
					$('.press-room--list').empty();
					
					showFeeds(rootPath, to, pageSize);
				}
				
				return false;
			}); 
		});
	} */
}

function showItems(pageJson) {
	$.each(pageJson, function(index, item) {
		var title = item.title; 
		var intro = item.intro; 
		var imagePath = item.imagePath;
		var date = item.date;
		
		var link = item.path + ".html";
		
		var element = createFeed(title, intro, date, imagePath, link);
		$('.feed--list').append(element);
	});
	
	setTimeout(function(){
		$(document).foundation('reflow');
		$(document).foundation('equalizer','reflow');
	}, 1000);
	
}

function createFeed(title, intro, date, imagePath, url) {
	var element = document.createElement("div");
	element.className = 'feed--item';
	element.setAttributeNode(document.createAttribute('data-equalizer'));
	
	var dateDiv = document.createElement("div");
	dateDiv.className = 'small-12 columns feed--item--caption';
	dateDiv.innerHTML = date;
	
	element.appendChild(dateDiv);
	
	var wrapperDiv = document.createElement("div");
	wrapperDiv.className = 'small-12 columns feed--item--content-wrapper';
	var firstColumnDiv = document.createElement("div");
	firstColumnDiv.className = 'small-12 medium-6 columns';
	firstColumnDiv.setAttributeNode(document.createAttribute('data-equalizer'));
	var imageDiv = document.createElement("div");
	imageDiv.className = 'adaptiveimage parbase foundation5image image image_0';
	
	var link = document.createElement('a');
	link.href = url;
	
	var image = document.createElement('img');
	image.alt = title;
	
	var dataInterchangeAttribute = document.createAttribute('data-interchange');
	dataInterchangeAttribute.value = "[" + imagePath + ".img.full.medium.jpg, (default)], " +
									  "[" + imagePath + ".img.full.low.jpg, (small)], " +
									  "[" + imagePath + ".img.620.high.jpg, (retina)], " +
									  "[" + imagePath + ".img.full.medium.jpg, (medium)], " +
									  "[" + imagePath + ".img.full.high.jpg, (large)]";
	image.setAttributeNode(dataInterchangeAttribute);
	link.appendChild(image);
	
	var noscript = document.createElement('noscript')
	var image2 = document.createElement('img');
	image2.src = imagePath + '.img.full.medium.jpg';
	image2.alt = title;
	noscript.appendChild(image2);
	link.appendChild(noscript);
	
	imageDiv.appendChild(link);
	firstColumnDiv.appendChild(imageDiv);
	wrapperDiv.appendChild(firstColumnDiv);
	
	var secondColumnDiv = document.createElement('div');
	secondColumnDiv.className = 'small-12 medium-6 columns';
	
	var contentDiv = document.createElement('div');
	contentDiv.className = 'feed--item--content';
	
	var h4 = document.createElement('h4');
	h4.className = 'feed--item--title';
	
	var link2 = document.createElement('a');
	link2.href = url;
	link2.innerHTML = title;
	
	h4.appendChild(link2);
	
	contentDiv.appendChild(h4);
	
	var p = document.createElement('p');
	p.innerHTML = intro;
	p.className = 'feed--item--tagline';
	
	contentDiv.appendChild(p);
	secondColumnDiv.appendChild(contentDiv);
	wrapperDiv.appendChild(secondColumnDiv);
	element.appendChild(wrapperDiv);
	
	return element;
}

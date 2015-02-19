$(document).ready(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	var rootPath = $('.pressreleaselistfeed-wrapper').data('rootpath');
	var pageSize = $('.pressreleaselistfeed-wrapper').data('pagesize');
	
	if (rootPath && pageSize) {
		showPressReleases(rootPath, 1, pageSize);
	}
	
});
	

function showPressReleases(rootPath, pageNumber, pageSize) {
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
			
			$(document).foundation('interchange', 'reflow');
		},
		error: function (){
		}
	});
}

function buildNavigators(pageNumber, numberOfPages) {
	
	if (numberOfPages > 1) {
		$.each($('.pressreleaselistfeed-wrapper .pagination-centered'), function(index, item) { 
			var pagination = document.createElement('ul');
			pagination.className = 'pagination';
			
			var toFirst = document.createElement('li');
			if (pageNumber == 1) {
				toFirst.innerHTML = '<li class="arrow unavailable"><a href="#">«</a></li>';
			} else {
				toFirst.innerHTML = '<li class="arrow"><a data-index="1" href="#">«</a></li>';
			}
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
			
			$('.pressreleaselistfeed-wrapper .pagination-centered .pagination li:not(.unavailable) a').click(function(){
				var to = $(this).data('index');
				
				if ($(this).parent().hasClass('unavailable')) {
					return false;
				}
				
				var rootPath = $('.pressreleaselistfeed-wrapper').data('rootpath');
				var pageSize = $('.pressreleaselistfeed-wrapper').data('pagesize');
				
				if (rootPath && pageSize) {
					$('.pressreleaselistfeed-wrapper .pagination-centered').empty();
					$('.press-room--list').empty();
					
					showPressReleases(rootPath, to, pageSize);
				}
				
				return false;
			}); 
		});
	} 
}

function showItems(pageJson) {
	$.each(pageJson, function(index, item) {
		var title = item.title; 
		var intro = item.intro; 
		var imagePath = item.imagePath;
		var date = item.date;
		
		var link = item.path + ".html";
		
		var element = createPressRelease(title, intro, date, imagePath, link);
		$('.press-room--list').append(element);
	});
	
	setTimeout(function(){
		$(document).foundation('reflow');
		$(document).foundation('equalizer','reflow');
	}, 500);
	
}

function createPressRelease(title, intro, date, imagePath, url) {
	var element = document.createElement("div");
	element.className = 'press-room--list-item';
	element.setAttributeNode(document.createAttribute('data-equalizer'));
	
	var dateDiv = document.createElement("div");
	dateDiv.className = 'small-12 columns press-room--list-item--caption';
	dateDiv.innerHTML = date;
	
	element.appendChild(dateDiv);
	
	var wrapperDiv = document.createElement("div");
	wrapperDiv.className = 'small-12 columns press-room--list-item--content-wrapper';
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
	contentDiv.className = 'press-room--list-item--content';
	
	var h4 = document.createElement('h4');
	h4.className = 'press-room--list-item--title';
	
	var link2 = document.createElement('a');
	link2.href = url;
	link2.innerHTML = title;
	
	h4.appendChild(link2);
	
	contentDiv.appendChild(h4);
	
	var p = document.createElement('p');
	p.innerHTML = intro;
	p.className = 'press-room--list-item--tagline';
	
	contentDiv.appendChild(p);
	secondColumnDiv.appendChild(contentDiv);
	wrapperDiv.appendChild(secondColumnDiv);
	element.appendChild(wrapperDiv);
	
	return element;
}

$(document).ready(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	$('.pressreleaselistfeed-wrapper').each(function (){
		console.log("Inside Parapa the wrapper");
		var componentID = $(this).data('componentid');
		var rootPath = $(this).data('rootpath');
		var pageSize = $(this).data('pagesize');
		var hideMonths = $(this).data('hidemonths');
		var isMultilevel = $(this).data('multilevel');
		var resourceType = $(this).data('resourcetype');
		var tags = $(this).data('tags');
		console.log("ComponentID " + componentID + ", rootPath " + rootPath + ", pageSize "+ pageSize +
					", hideMonths "+ hideMonths + ", isMultilevel "+ isMultilevel + ", tags "+ tags + ", resourceType " + resourceType);
		if (rootPath && pageSize) {
			showPressReleases(rootPath, 1, pageSize, componentID, tags, hideMonths, isMultilevel, resourceType);
		}
	});
});	

function showPressReleases(rootPath, pageNumber, pageSize, componentID, tags, hideMonths, isMultilevel, resourceType ) {
	console.log("Doing a doGet call");
	$.ajax({
		type: 'GET',    
		url: '/bin/list/pagination.json',
		data:{
			rootPath: rootPath,
			pageNumber: pageNumber,
			pageSize: pageSize,
			isMultilevel: isMultilevel,
			resourceType: resourceType,
			tags: tags
		},
		success: function(data){
			console.log("Success on doGet");
			var json = jQuery.parseJSON(data);
			buildNavigators(pageNumber, json.pages);
			showItems(json.pageJson, componentID, hideMonths);
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

function showItems(pageJson, componentID, hideMonths) {
	console.log("Inside showItems"); 
	var currentGroup = "";
	$.each(pageJson, function(index, item) {
		
		var addGroup = false;
		if(currentGroup != item.group){
			addGroup = true;
			currentGroup = item.group;
		}
		currentGroup = item.group;
		var title = item.title; 
		var intro = item.intro;
		var shortIntro = item.shortIntro;
		var imagePath = item.imagePath;
		var date = "The Date";//item.date;
		var link = item.path + ".html";
		var element = createPressRelease(title, intro, shortIntro, date, imagePath, link, hideMonths, currentGroup, addGroup);
		var componentClass = '#press-office--feed-' + componentID;
		
		$(componentClass).append(element);
	});
	
	setTimeout(function(){
		$(document).foundation('reflow');
		$(document).foundation('equalizer','reflow');
	}, 1000);
	
}

function createPressRelease(title, intro, shortIntro, date, imagePath, url, hideMonths, group, addGroup) {
	//turn this function into function createPressRelease(title, intro, shortIntro, imagePath, url){, there is no need for anything else with tagged Elements
		var element = document.createElement("li");
		
			var listItem = document.createElement("div");
			listItem.className = 'press-office--list-item';

				var contentWrapper = document.createElement("div");
				contentWrapper.className = 'small-12 columns press-office--list-item--content-wrapper';
				contentWrapper.setAttributeNode(document.createAttribute('data-equalizer-watch'));

					var columnsDiv = document.createElement("div");
					columnsDiv.className = 'small-12 columns';

						var link = document.createElement('a');
						link.href = url;
						
							var image = document.createElement('img');
							image.alt = title;
							
							var dataInterchangeAttribute = document.createAttribute('data-interchange');
							dataInterchangeAttribute.value =  "[" + imagePath + ".img.full.medium.jpg, (default)], " +
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
							
//						// Published Date Logic						
//						var dateDiv = document.createElement("div");
//						dateDiv.className = 'small-12 columns press-office--list-item--caption';
//							dateDiv.innerHTML = date;

					columnsDiv.appendChild(link);
					//columnsDiv.appendChild(dateDiv);

					var columnsDiv2 = document.createElement("div");
					columnsDiv2.className = 'small-12 columns';

						var itemContent = document.createElement("div");
						itemContent.className = 'press-office--list-item--content';

							var itemTitle = document.createElement("h4");
							itemTitle.className = 'press-office--list-item--title';

								var link2 = document.createElement("a");
								link2.href = url;
									
									link2.innerHTML = title;

							itemTitle.appendChild(link2);

							var tagLine = document.createElement("p");
							tagLine.className = 'press-office--list-item--tagline';

								tagLine.innerHTML = shortIntro;
							
						itemContent.appendChild(itemTitle);
						itemContent.appendChild(tagLine);

					columnsDiv2.appendChild(itemContent);

				contentWrapper.appendChild(columnsDiv);
				contentWrapper.appendChild(columnsDiv2);

			listItem.appendChild(contentWrapper);

		element.appendChild(listItem);

		return element;
}

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
	document.getElementById("pressreleaselistfeed_wrapper").appendChild(moreElementsDiv);
	
	$('.pressreleaselistfeed .more-results').click(function(){
		var rootPath = $('.pressreleaselistfeed-wrapper').data('rootpath');
		var pageSize = $('.pressreleaselistfeed-wrapper').data('pagesize');
		removeMoreResultsButton();
		var elementsShowed = $('.press-office--list-item').length;
		var elementsToAdd = pageSize;
		currentPage = elementsShowed / pageSize;
		showPressReleases(rootPath, currentPage+1, pageSize);
	});
}

function removeMoreResultsButton() {
	 var wrapperDiv = document.getElementById('pressreleaselistfeed_wrapper');
	 var divToDelete = document.getElementById("more_results");
	 wrapperDiv.removeChild(divToDelete);
}

function buildNavigators(pageNumber, numberOfPages) {
	
	/*if (numberOfPages > 1) {
		$.each($('.pressreleaselistfeed-wrapper .pagination-centered'), function(index, item) { 
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
	} */
}

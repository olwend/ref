$(document).ready(function() {
	//Use JQuery AJAX request to post data to a Sling Servlet
	$(document).foundation({
	    equalizer: {
	        equalize_on_stack: true
		}
	});
	$('.js-feed-wrapper').each(function (){
		var componentID = $(this).data('componentid');
		var rootPath = $(this).data('rootpath');
		var pageSize = $(this).data('pagesize');
		var isMultilevel = $(this).data('multilevel');
		var resourceType = $(this).data('resourcetype');
		var tags = $(this).data('tags');
		if (rootPath && pageSize) {
			showFeeds(rootPath, 1, pageSize, componentID, tags, isMultilevel, resourceType);
		}
	});
});	

function showFeeds(rootPath, pageNumber, pageSize, componentID, tags, isMultilevel, resourceType ) {
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
			var json = jQuery.parseJSON(data);
			showItems(json.pageJson, componentID);
			if(pageNumber != json.pages){
				addMoreResultsButton(rootPath, pageNumber, pageSize, componentID, tags, isMultilevel, resourceType);
			}
			$(document).foundation('reflow');
			$(document).foundation('interchange', 'reflow');
		},
		error: function (){
		}
	});
}

function showItems(pageJson, componentID) {
	$.each(pageJson, function(index, item) {
		var title = item.title;
		var shortIntro = item.shortIntro;
		var imagePath = item.imagePath;
		//var date = item.date;
		var link = item.path + ".html";
		var element = createFeed(title, shortIntro, imagePath, link);
		var componentClass = '#feed--tiles-' + componentID;
		
		$(componentClass).append(element);
	});
	
	setTimeout(function(){
		$(document).foundation('reflow');
		$(document).foundation('equalizer','reflow');
	}, 1000);
	
}

function createFeed(title, shortIntro, imagePath, url) {
	// Turn this function into createFeed(title, shortIntro, imagePath, url){, there is no need for anything else with tagged Elements
	// only thing that should be needed would possibly be date but you have News for that! 
		var element = document.createElement("li");
		
			var listItem = document.createElement("div");
			listItem.className = 'feed--item';

				var contentWrapper = document.createElement("div");
				contentWrapper.className = 'small-12 columns feed--item--content-wrapper';
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

					columnsDiv.appendChild(link);

					var columnsDiv2 = document.createElement("div");
					columnsDiv2.className = 'small-12 columns';

						var itemContent = document.createElement("div");
						itemContent.className = 'feed--item--content';

							var itemTitle = document.createElement("h4");
							itemTitle.className = 'feed--item--title';

								var link2 = document.createElement("a");
								link2.href = url;
									
									link2.innerHTML = title;

							itemTitle.appendChild(link2);

							var tagLine = document.createElement("p");
							tagLine.className = 'feed--item--tagline';

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

function addMoreResultsButton(rootPath, pageNumber, pageSize, componentID, tags, isMultilevel, resourceType) {
	var moreElementsDiv = document.createElement("div");
	
	moreElementsDiv.className = "row more-results more-results-" + componentID;
	moreElementsDiv.id = "more-results-" + componentID;
	var aTag = document.createElement("a");
	var h5Tag = document.createElement("h5");
	h5Tag.id = "more-results-text";
	h5Tag.className = "more-results-text";
	h5Tag.innerHTML = "More results";
	aTag.appendChild(h5Tag);
	moreElementsDiv.appendChild(aTag);
	document.getElementById("js-feed-wrapper-"+componentID).appendChild(moreElementsDiv);
	
	$('.more-results-'+componentID).click({rootPath:rootPath, pageSize:pageSize, componentID:componentID, tags:tags, isMultilevel:isMultilevel, resourceType:resourceType}, function(event){
		var rootPath = event.data.rootPath;
		var pageSize = event.data.pageSize;
		var componentID = event.data.componentID;
		var tags = event.data.tags;
		var isMultilevel = event.data.isMultilevel;
		var resourceType = event.data.resourceType;
		removeMoreResultsButton(componentID);
		var elementsShowed = $('#feed--tiles-' + componentID + ' .feed--item').length;
		var elementsToAdd = pageSize;
		currentPage = elementsShowed / pageSize;
		showFeeds(rootPath, currentPage+1, pageSize, componentID, tags, isMultilevel, resourceType);
	});
}

function removeMoreResultsButton(componentID) {
	 var wrapperDiv = document.getElementById('js-feed-wrapper-'+componentID);
	 var divToDelete = document.getElementById("more-results-" + componentID);
	 wrapperDiv.removeChild(divToDelete);
}



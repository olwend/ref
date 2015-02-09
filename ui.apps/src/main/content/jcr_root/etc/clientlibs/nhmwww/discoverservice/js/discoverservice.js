$(document).ready(function() {
	
	//Vars needed to create the grid
	var deviceCounter = 4;
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		deviceCounter = 2;
	}

	var nestDisplayCounter 	= 4
	,	numOfNested 		= 0;

	var hideMoreElements = function() {
		$('.discoverlist .more-results').hide();
	};

	//Use JQuery AJAX request to post data to a Sling Servlet
	$.ajax({
		type: 'POST',    
		url: '/bin/discover/grid.json',
		data:{
			startIndex: 0,
			numElements: 4
		},
		success: function(data){
			var json = jQuery.parseJSON(data);

			if (jQuery.isEmptyObject(json.discoverJSON)){
				hideMoreElements();
			}else{
				var elementsAdded = createElements(json, 0);
				if (elementsAdded < 4) {
					hideMoreElements();
				} else {
					$.ajax({
						type: 'POST',    
						url: '/bin/discover/grid.json',
						data:{
							startIndex: 4,
							numElements: 16
						},
						success: function(data){
							json = jQuery.parseJSON(data);
							
							if (jQuery.isEmptyObject(json.discoverJSON)){
								hideMoreElements();
							}else{
								elementsAdded = createElements(json, 4);
								if (elementsAdded < elementsToAdd) {
									hideMoreElements();
								}

							}
						}
					});
				}
			}
			
		},
		error: function (){
			hideMoreElements();
		}
	});
	
	$('.discoverlist .more-results').click(function(){
		var elementsShowed = $('.discover-element').length;
		var elementsToAdd = 12;
		$.ajax({
			type: 'POST',    
			url: '/bin/discover/grid.json',
			data:{
				startIndex: elementsShowed,
				numElements: elementsToAdd
			},
			success: function(data){
				json = jQuery.parseJSON(data);
				
				if (jQuery.isEmptyObject(json.discoverJSON)){
					hideMoreElements();
				}else{
					elementsAdded = createElements(json, elementsShowed);
					if (elementsAdded < elementsToAdd) {
						hideMoreElements();
					}

				}
			}
		});
	});

});
	
function startDiscoverMansonry (){
	var $container = $('#main_row');
	$container.imagesLoaded(function(){
		$container.masonry({
			itemSelector: '.discover-element'
		});
		
		$( $container ).masonry( 'reloadItems' );
		$( $container ).masonry( 'layout' );
	});
}
/*
 * Function which creates the grid
 */
function createElements (json, index){
	var counter 		= index
	,   elementsAdded	= 0
	,	flag    		= false;

	//For all the elements retrieved
	jQuery.each(json.discoverJSON, function(index, item){
		var divToAdd = createDiv(item);

		document.getElementById("main_row").appendChild(divToAdd);
		
		elementsAdded++;
	});

	startDiscoverMansonry();
	return elementsAdded;
}

/*
 * Function to create the div
 */
function createDiv(element, mainDiv){

	var div = document.createElement("div");
	div.className = "discover-element large-3 medium-6 small-12 discover-" + element.tag;
	
	var wrapperDiv = document.createElement("div");
	wrapperDiv.className = "discover-element-wrapper";

	//Main Image with link
	var aImg = document.createElement("a");
	aImg.href = element.href;
	var img = document.createElement("img");
	if (!jQuery.isEmptyObject(element.image)){
		img.src = element.image.fileReference;
		img.alt = element.title;
	}else{
		img.src = $.jYoutube(element.video.youtube);
		img.alt = element.title;
		/*This is prep in order to add an icon to the video features.
		 * var imgSpan = document.createElement("span");
		imgSpan.className = "video-icon";
		img.appendChild(imgSpan);*/
	}
	

	aImg.appendChild(img);
	wrapperDiv.appendChild(aImg);

	//Image footer
	var div2 = document.createElement("div");
	div2.className = "row image-footer";

	var div3 = document.createElement("div");
	div3.className = "columns small-2";

	var h6_1 = document.createElement("p");
	h6_1.className = "discover-element-tag";
	h6_1.innerHTML = element.tag;
	div3.appendChild(h6_1);

	var div4 = document.createElement("div");
	div4.className = "columns small-2";

	if(element.tag == "news"){ 
		var h6_2 = document.createElement("p");
		h6_2.className = "discover-element-date";
		h6_2.innerHTML = formatDate(element.created);
		div4.appendChild(h6_2);
	}

	div2.appendChild(div3);
	div2.appendChild(div4);
	wrapperDiv.appendChild(div2);

	//Main text
	var div5 = document.createElement("div");
	div5.className = "discover-element-text";

	//Title with link
	var a = document.createElement("a");
	a.className = "element-title";
	a.innerHTML = element.title;
	a.href = element.href;

	//Description text
	var p = document.createElement("div");
	p.className = "element-text";
	p.innerHTML = element.text;

	div5.appendChild(a);
	div5.appendChild(p);
	wrapperDiv.appendChild(div5);
	div.appendChild(wrapperDiv);
	if (mainDiv) {
		mainDiv.appendChild(div);
		return mainDiv;
	} else {
		return div;
	}
}

/*
 * Function which formats the date to "Month day, year"
 * e.g. "Mon Oct 27 14:53:30 GMT 2014" to "Oct 27, 2014"
 */
function formatDate(input){
	var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];

	var inputDate = new Date(input);
	var month     = monthNames[inputDate.getMonth()];
	var day       = inputDate.getDate();
	var year      = inputDate.getFullYear();

	var date = month + " " + day + ", " + year;

	return date;
}

/*
 * Function which displays the hidden elements
 */
function displayNested(nestDisplayCounter, numOfNested){
	for (var i=0;i<=3;i++){
		$( "#nested_element_" + (nestDisplayCounter+i)+":hidden").fadeIn( "slow" );
	}
}
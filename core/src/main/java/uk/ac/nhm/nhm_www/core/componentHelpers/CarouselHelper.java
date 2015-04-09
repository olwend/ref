package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Collections;
//import org.apache.commons.validator.routines.UrlValidator;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.CarouselElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.day.cq.wcm.api.PageManager;

public class CarouselHelper extends HelperBase {
	
	private ArrayList<CarouselElement> elements;
	private String componentTitle;
	private String hyperLink;
	private boolean newwindow;
	private int grouping = 1;
	private boolean hasThumbnails;
	private boolean hasAutoscroll;
	private int autoScrollDuration = 4;
	private ValueMap properties;
	private PageManager pageManager;
	private Resource resource;
	private ResourceResolver resourceResolver;
	private String carouselType;
	private boolean heading = false;

	protected static final Logger logger = LoggerFactory.getLogger(CarouselHelper.class);
	
	public CarouselHelper(Resource resource, PageManager pageManager, ResourceResolver resourceResolver) throws JSONException
	{
		this.helperFactory = new HelperFactory(resource);
		this.pageManager = pageManager;
		this.resource = resource;
		this.resourceResolver = resourceResolver;
		properties = helperFactory.getProperties();
		init();
		
	}
	
	public CarouselHelper(ValueMap properties) throws JSONException
	{
		this.properties = properties;
		init();
	}
	
	private void init() throws JSONException
	
	{
		if (this.properties.get("title", String.class) != null) {
			this.componentTitle = this.properties.get("title",String.class);
		}
		if (this.properties.get("hyperlink", String.class) != null) {
			this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}
		if (this.properties.get("newwindow") != null) {
			this.newwindow = this.properties.get("newwindow",false);
		}
		if (this.properties.get("grouping",String.class) != null) {
			this.grouping = Integer.parseInt(this.properties.get("grouping",String.class));
		}
		if (this.properties.get("autoscroll") != null) {
				this.hasAutoscroll = this.properties.get("autoscroll",false);
		}
		if (this.properties.get("autoscrollduration",int.class) != null)
		{
			this.autoScrollDuration = this.properties.get("autoscrollduration",int.class);
		}
		if (this.properties.get("thumbnails") != null) {
				this.hasThumbnails = this.properties.get("thumbnails",false);			
		}

		
		elements = new ArrayList<CarouselElement>();
		String[] items = this.properties.get("carouselItems",String[].class);
		//String headings[] = this.properties.get("hyperlinks",String[].class);
		//String captions[] = this.properties.get("captions",String[].class);
		/*logger.error("number of items: " + items);
		for(String itemTest: items) {
			logger.error("item test: " + itemTest);
		}*/
		if (items != null && items.length > 0)
		{
			
			//for(String item: items)
			for(int i =0; i< items.length; i++) 
			{
				
				CarouselElement element = new CarouselElement(new JSONObject(items[i]), this.pageManager, this.resourceResolver);
				if (this.grouping == 3 && !element.getHeading().equals("") && !element.getCaption().equals("") && !element.getHyperlink().equals("")) {
					element.setIsContentSlide(true);
					this.carouselType = "event-slider";
				} else {
					this.carouselType = "carousel";
				}
				if(element.getHeading() !=null && !element.getHeading().equals("")) {
					this.heading = true;
				}
				/*
				 * Removing as this is the code for phase 2 implementation. Phase one will be built with images, link, heading and captions
				 * if(element.getIsContentSlide()) {
					this.carouselType = "event-slider";
				} else {
					this.carouselType = "carousel";
				}*/
				this.addElement(element);
			}
		}
	}
	
	public CarouselElement getElement(int i) {
		return (CarouselElement) elements.get(i);
	}

	public void swapElements(int i, int j) {
		Collections.swap(elements, i, j);
	}

	public void setComponentTitle(String string) {
		this.componentTitle = string;
	}

	public String getComponentTitle() {
		return this.componentTitle;
	}

	public void setHyperlink(String string) {
		this.hyperLink = string;
	}

	public String getHyperlink() {
		return this.hyperLink;
	}

	public void setGrouping(int i) {
			if(i<1 || i>3)
			{
				throw new IndexOutOfBoundsException("Grouping must be between 1 and 3");
			}
			else
			{
				this.grouping = i;
			}
	}

	public int getGrouping() {
		return this.grouping;
	}

	public boolean validateHyperlink() {
		return LinkUtils.validateUrl(this.hyperLink);
	}

	public boolean getHasThumbnails() {
		return this.hasThumbnails;
	}

	public void setHasThumbnails(boolean b) {
		this.hasThumbnails = b;
		
	}

	public boolean getHasAutoscroll() {
		return this.hasAutoscroll;
	}

	public void setHasAutoscroll(boolean b) {
		this.hasAutoscroll = b;
		
	}

	public int getAutoScrollDuration() {
		return this.autoScrollDuration;
	}

	public void setAutoScrollDuration(int i) {
		this.autoScrollDuration = i;
		
	}

	public boolean hasHeading() {
		return heading;
	}

	public void setHeading(boolean hasHeading) {
		this.heading = hasHeading;
	}

	public ArrayList<CarouselElement> getElements() {
		return elements;
	}

	public void addElement(CarouselElement element1) {
		elements.add(element1);
	}

	public void removeElement(int i) {
		elements.remove(i);
		
	}

	public String getCarouselType() {
		return carouselType;
	}

	public void setCarouselType(String carouselType) {
		this.carouselType = carouselType;
	}
	
	public String getNewWindowHtml() {
		if (this.newwindow)
		{	
			return " target=\"_blank\"";
		}
		else
		{
			return "";
		}
	}
	
	
	


	
}

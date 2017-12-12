package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;
import com.day.cq.wcm.foundation.Placeholder;

import java.io.IOException;
import java.io.StringWriter;;

public class HeroHelper {

	private Image image;
	private String id;
	private String divId;
	private String promoLinkText;
	private String title;
	private String summary;
	private String videoId;
	private boolean componentInitialised;
	private Image mobileimage;

	public Image getImage() {
		this.image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.image.addCssClass("desktop tablet");
		if (this.getAltText() != null)
		{
			this.image.setAlt(this.getAltText());
			this.image.setTitle(this.getAltText());
		}
		return this.image;
	}
	
	public Image getMobileImage()
	{
	    //drop target css class = dd prefix + name of the drop target in the edit config
		this.mobileimage.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.mobileimage.addCssClass("mobile");
		this.mobileimage.setSelector(".img"); // use image script
		if (this.getAltText() != null)
			{
				this.mobileimage.setAlt(this.getAltText());
				this.mobileimage.setTitle(this.getAltText());
			}
		return this.mobileimage;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public String getDivId() {
		return divId;
	}

	public String getPromoLinkText()
	{
		if (this.promoLinkText != null && promoLinkText.trim().length() > 0)
		{
			return promoLinkText;
		}
		else
		{
			return null;
		}
	}
	
	public String getSummary()
	{
		if (this.summary != null && this.summary.trim().length() > 0)
		{
			return this.summary;
		}
		else
		{
			return null;
		}
	}
	
	public String getVideoId()
	{
		if (this.videoId != null && this.videoId.trim().length() > 0 && this.videoId.matches("[a-zA-Z0-9_-]{11}")) //Basic youtube videoid sanity check
		{
			return this.videoId;
		}
		else
		{
			return null;
		}
	}
	
	public String getAltText()
	{
		// If a subheading is set then the alt text will be set to that, if not then the page title is used. 
		if (this.title != null)
		{
				return this.title;
		}
		else
		{
				return this.promoLinkText;
		}
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HeroHelper(SlingHttpServletRequest request, Page page, ValueMap properties) {
		
		//Inject image class from Resource
		this(request,page,properties,new Image(request.getResource()));
	}

	public HeroHelper(SlingHttpServletRequest request, Page page, ValueMap properties, Image image) {
		
		
		this.promoLinkText = properties.get("promoLinkText", String.class);
		this.summary = properties.get("summary", String.class);
		this.title = properties.get("title", PageUtils.getPageTitle(page));
		this.videoId = properties.get("youtube", String.class);

		this.mobileimage = new Image(request.getResource());
		this.mobileimage.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(request));
		this.image = image;
		this.image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(request));

	    //drop target css class = dd prefix + name of the drop target in the edit config
		this.image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.image.addCssClass("staticHero");
		this.image.setSelector(".img"); // use image script
		if (this.getAltText() != null)
			{
				this.image.setAlt(this.getAltText());
				this.image.setTitle(this.getAltText());
			}

		
	    if (request.getResource().getPath() != null) { divId = request.getResource().getPath();}
	}

	public boolean isComponentInitialised() {
		return componentInitialised;
	}

}

package uk.ac.nhm.nhm_www.core.model;

import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.PageUtils;


public class FeedListElement {
    protected String imageResourcePath;
    protected String intro;
    protected String title;
    protected Boolean pinned;
    protected ResourceResolver resourceResolver;
    protected Page page;

    public FeedListElement(final ResourceResolver resourceResolver, final Page page) {
	this.page = page;
	this.title = PageUtils.getPageTitle(page);
	this.intro = page.getProperties().get("summary", String.class);
	this.pinned = page.getProperties().get("pinned", false);
	final Resource resource = page.adaptTo(Resource.class);
	this.imageResourcePath = resource.getPath()+"/jcr:content/image";
    }

    public Page getPage() {
        return this.page;
    }


    public void setPage(Page page) {
        this.page = page;
    }


    public String getImagePath() {
	return this.imageResourcePath;
    }


    public void setImagePath(final String imageResourcePath) {
	this.imageResourcePath = imageResourcePath;
    }


    public String getIntro() {
	return this.intro;
    }


    public void setIntro(final String intro) {
	this.intro = intro;
    }


    public String getTitle() {
	return this.title;
    }


    public void setTitle(final String title) {
	this.title = title;
    }


    public Boolean isPinned() {
	return this.pinned;
    }


    public void setPinned(final Boolean pinned) {
	this.pinned = pinned;
    }






}

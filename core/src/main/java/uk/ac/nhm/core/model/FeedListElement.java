package uk.ac.nhm.core.model;

import com.day.cq.wcm.api.Page;

public interface FeedListElement extends ListElement {

	public abstract boolean isInitialised();

	public abstract Page getPage();

	public abstract void setPage(Page page);

	public abstract String getImagePath();

	public abstract void setImagePath(String imageResourcePath);

	public abstract String getIntro();

	public abstract void setIntro(String intro);

	public abstract Boolean isPinned();

	public abstract void setPinned(Boolean pinned);

}
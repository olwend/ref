package uk.ac.nhm.core.model;

public interface ListElement {

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getElementLink();

	public abstract void setElementLink(String elementLink);

	public abstract boolean isInitialised();

}
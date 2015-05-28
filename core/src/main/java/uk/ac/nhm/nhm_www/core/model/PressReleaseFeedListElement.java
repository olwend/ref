package uk.ac.nhm.nhm_www.core.model;

import java.util.Date;

public interface PressReleaseFeedListElement extends Comparable<PressReleaseFeedListElement>{

	public abstract boolean isInitialised();

	public abstract void setPressReleaseDate(Date pressReleaseDate);

	public abstract Date getPressReleaseDate();

	public abstract int compareTo(PressReleaseFeedListElement o);

}
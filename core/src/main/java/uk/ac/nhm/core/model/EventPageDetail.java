package uk.ac.nhm.core.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class EventPageDetail {
	String eventPagePath = "";
	String eventType = "";
	String title = "";
	String description = "";
	String eventVenue = "";
	String eventGroup = "";
	String eventTileLink = "";
	String keywords = "";
	Boolean hideFromSearch = false;
	String adultPrice  = "";
	String concessionPrice = "";
	String memberPrice = "";
	String familyPrice = "";
	String customPrice = "";
	String eventListingPrice = "";
	String imageLink = "";
	String ctaLink = "";
	String capacity = "";
	String eventDuration = "";
	String speakerDetails = "";
	
	LinkedHashSet<String> dates = new LinkedHashSet<String>();
	ArrayList <String> allDay = new ArrayList<String>();
	ArrayList <String> times = new ArrayList<String>();
	ArrayList <String> durations = new ArrayList<String>();
	ArrayList <String> tags = new ArrayList<String>();
	ArrayList <String> subject = new ArrayList<String>();
	ArrayList <String> scienceSubject = new ArrayList<String>();
	
	public void setEventPagePath(String eventPagePath) {
		this.eventPagePath = eventPagePath;
	}
	
	public String getEventPagePath() {
		return this.eventPagePath;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getEventType() {
		return this.eventType;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}
	
	public String getEventVenue() {
		return this.eventVenue;
	}
	public void setEventGroup(String eventGroup) {
		this.eventGroup = eventGroup;
	}
	
	public String getEventGroup() {
		return this.eventGroup;
	}
	
	public void setEventTileLink(String eventTileLink) {
		this.eventTileLink = eventTileLink;
	}
	
	public String getEventTileLink() {
		return this.eventTileLink;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getKeywords() {
		return this.keywords;
	}
	
	public void setHideFromSearch(Boolean hideFromSearch) {
		this.hideFromSearch = hideFromSearch;
	}
	
	public Boolean getHideFromSearch() {
		return this.hideFromSearch;
	}
	
	public void setAdultPrice(String adultPrice) {
		this.adultPrice = adultPrice;
	}
	
	public String getAdultPrice() {
		return this.adultPrice;
	}
	
	public void setConcessionPrice(String concessionPrice) {
		this.concessionPrice = concessionPrice;
	}
	
	public String getConcessionPrice() {
		return this.concessionPrice;
	}
	
	public void setMemberPrice(String memberPrice) {
		this.memberPrice = memberPrice;
	}
	
	public String getMemberPrice() {
		return this.memberPrice;
	}
	
	public void setFamilyPrice(String familyPrice) {
		this.familyPrice = familyPrice;
	}
	
	public String getFamilyPrice() {
		return this.familyPrice;
	}
	
	public void setCustomPrice(String customPrice) {
		this.customPrice = customPrice;
	}
	
	public String getCustomPrice() {
		return this.customPrice;
	}
	
	public void setEventListingPrice(String eventListingPrice) {
		this.eventListingPrice = eventListingPrice;
	}
	
	public String getEventListingPrice() {
		return this.eventListingPrice;
	}
	
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	
	public String getImageLink() {
		return this.imageLink;
	}
	
	public void setCtaLink(String ctaLink) {
		this.ctaLink = ctaLink;
	}
	
	public String getCtaLink() {
		return this.ctaLink;
	}
	
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	public String getCapacity() {
		return this.capacity;
	}
	
	public void setSpeakerDetails(String speakerDetails) {
		this.speakerDetails = speakerDetails;
	}
	
	public String getSpeakerDetails() {
		return this.speakerDetails;
	}
	
	public void setEventDuration(String eventDuration) {
		this.eventDuration = eventDuration;
	}
	
	public String getEventDuration() {
		return this.eventDuration;
	}
	
	public void setDates(LinkedHashSet<String> hashSet) {
		this.dates = hashSet;
	}
	
	public LinkedHashSet<String> getDates() {
		return this.dates;
	}
	
	public void setAllDay(ArrayList<String> allDay) {
		this.allDay = allDay;
	}
	
	public ArrayList<String> getAllDay() {
		return this.allDay;
	}
	
	public void setTimes(ArrayList<String> times) {
		this.times = times;
	}
	
	public ArrayList<String> getTimes() {
		return this.times;
	}
	
	public void setDurations(ArrayList<String> durations) {
		this.durations = durations;
	}
	
	public ArrayList<String> getDurations() {
		return this.durations;
	}
	
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	public ArrayList<String> getTags() {
		return this.tags;
	}
	
	public void setSubject(ArrayList<String> subject) {
		this.subject = subject;
	}
	
	public ArrayList<String> getSubject() {
		return this.subject;
	}
	
	public void setScienceSubject(ArrayList<String> scienceSubject) {
		this.scienceSubject = scienceSubject;
	}
	
	public ArrayList<String> getScienceSubject() {
		return this.scienceSubject;
	}
}

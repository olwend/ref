package uk.ac.nhm.nhm_www.core.model;

public class SVGImage {

	private String cssClass;
	private String svgSrc;
	private String title;
	private String altText;
	private String strokeWidth;
	private String fallback;
	private String baseColour;
	private String hoverColour;
	private String hoverState;
	private String mobileBaseColour;
	private String id;
	private String width;
	private String height;
	private String viewBox;
	private String enableBackground;
	private String path;
	private String strokeColour;
	
	public SVGImage(){}
	
	public SVGImage(String cssClass, String svgSrc, String title, String alt,
			String strokeWidth, String fallback, String baseColour,
			String hoverColour, String hoverState, String mobileBaseColour, String id,
			String width, String height, String viewBox, String enableBackground, String path, String strokeColour) {
		super();
		this.cssClass = cssClass;
		this.svgSrc = svgSrc;
		this.title = title;
		this.altText = alt;
		this.strokeWidth = strokeWidth;
		this.fallback = fallback;
		this.baseColour = baseColour;
		this.hoverColour = hoverColour;
		this.hoverState = hoverState;
		this.mobileBaseColour = mobileBaseColour;
		this.id = id;
		this.width = width;
		this.height = height;
		this.viewBox = viewBox;
		this.enableBackground = enableBackground;
		this.path = path;
		this.strokeColour = strokeColour;
	}
	
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getSvgSrc() {
		return svgSrc;
	}
	public void setSvgSrc(String svgSrc) {
		this.svgSrc = svgSrc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStrokeWidth() {
		return strokeWidth;
	}
	public void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	public String getFallback() {
		return fallback;
	}
	public void setFallback(String fallback) {
		this.fallback = fallback;
	}
	public String getBaseColour() {
		return baseColour;
	}
	public void setBaseColour(String baseColour) {
		this.baseColour = baseColour;
	}
	public String getHoverColour() {
		return hoverColour;
	}
	public void setHoverColour(String hoverColour) {
		this.hoverColour = hoverColour;
	}
	public String getMobileBaseColour() {
		return mobileBaseColour;
	}
	public void setMobileBaseColour(String mobileBaseColour) {
		this.mobileBaseColour = mobileBaseColour;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getViewBox() {
		return viewBox;
	}

	public void setViewBox(String viewBox) {
		this.viewBox = viewBox;
	}

	public String getEnableBackground() {
		return enableBackground;
	}
	public void setEnableBackground(String enableBackground) {
		this.enableBackground = enableBackground;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStrokeColour() {
		return strokeColour;
	}

	public void setStrokeColour(String strokeColour) {
		this.strokeColour = strokeColour;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public String getHoverState() {
		return hoverState;
	}

	public void setHoverState(String hoverState) {
		this.hoverState = hoverState;
	}

	@Override
	public String toString() {
		return "SVGImage [cssClass=" + cssClass + ", svgSrc=" + svgSrc
				+ ", title=" + title + ", alt=" + altText + ", strokeWidth="
				+ strokeWidth + ", fallback=" + fallback + ", baseColour="
				+ baseColour + ", hoverColour=" + hoverColour
				+ ", mobileBaseColour=" + mobileBaseColour + ", id=" + id
				+ ", width=" + width + ", height=" + height
				+ ", enableBackground=" + enableBackground + ", path=" + path
				+ "]";
	}

	public String toHtml(String designPath) {
				
		//TODO: tona: Image paths shouldn't be hardcoded here - need moving to JSPs
		return "<i class=\"" + this.cssClass
				+ "\" data-svg-src=\"" + designPath + "img/svg-icons/" + this.svgSrc 
				+ "\" data-svg-title=\"" + this.title 
				+ "\" alt=\"" + this.altText
				+ "\" data-alt=\"" + this.altText
				+ "\" data-stroke-width=\"" + this.strokeWidth
				+ "\" data-fallback=\"" + designPath + "img/icons/" + this.fallback 
				+ "\" data-base-color=\"" + this.baseColour
				+ "\" data-hover-color=\"" + this.hoverColour 
				+ "\" data-hover-state=\"" + this.hoverState
//				+ "\" data-mobile-base=\"" + this.baseColour
//				+ "\" id=\"" + this.id
//				+ "\" style=\"stroke-width: " + this.strokeWidth + "px;
				+ "\"></i>";
//				+ "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + this.width
//				+ "\" height=\"" + this.height
//				+ "\" viewBox=\"" + this.viewBox
//				+ "\" enable-background=\"" + this.enableBackground
//				+ "\"><path d=\"" + this.path
//				+ "\" stroke=\"" + this.strokeColour
//				+ "\"></path><desc>Created with Snap</desc><defs></defs></svg></i>";
		
	}
}

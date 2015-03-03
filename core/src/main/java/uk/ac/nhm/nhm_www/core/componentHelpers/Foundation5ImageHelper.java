package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.dam.api.Asset;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;
import uk.ac.nhm.nhm_www.core.utils.NodeUtils;

public class Foundation5ImageHelper {
	private String path;
	private String originalImagePath;
	private String extension;
	private String suffix;
	private String alt;
	private String caption;
	private String imageLinkURL;
	private Boolean activated;
	private Resource rs;
	private Node parentNode;
	private Asset asset;
	private ResourceResolver resourceResolver;
	public enum ImageInterchangeSize {DEFAULT, SMALL, MEDIUM, LARGE, RETINA}
	

	public Foundation5ImageHelper(ValueMap properties, Resource resource, ResourceResolver resourceResolver, HttpServletRequest request, XSSAPI xssAPI) {
		
		this.activated = false;
		//this.rs = resource;
		this.resourceResolver = resourceResolver;
		
		String fileReference = properties.get("fileReference", "");
		this.originalImagePath = fileReference;
		if (fileReference.length() != 0 || resource.getChild("file") != null) {
			ImageResource image = new ImageResource(resource);
			String tempPath = request.getContextPath() + resource.getPath();
			String tempAlt = xssAPI.encodeForHTMLAttr(properties.get("alt", image.getTitle()));
			
			// Handle extensions on both fileReference and file type images
			String tempExtension = "jpg";
			String tempSuffix = "";
			if (fileReference.length() != 0) {
				tempExtension = fileReference.substring(fileReference.lastIndexOf(".") + 1);
				tempSuffix = image.getSuffix();
				tempSuffix = tempSuffix.substring(0, tempSuffix.indexOf('.') + 1) + tempExtension;
			} else {
				Resource fileJcrContent = resource.getChild("file").getChild("jcr:content");
				if (fileJcrContent != null) {
					ValueMap fileProperties = fileJcrContent.adaptTo(ValueMap.class);
					String mimeType = fileProperties.get("jcr:mimeType", "jpg");
					tempExtension = mimeType.substring(mimeType.lastIndexOf("/") + 1);
				}
			}
			tempExtension = xssAPI.encodeForHTMLAttr(tempExtension);
			this.caption = properties.get("caption","");
			this.imageLinkURL = properties.get("image-path","");
			if(this.imageLinkURL != null && !this.imageLinkURL.equals("")){
				this.imageLinkURL = LinkUtils.getFormattedLink(this.imageLinkURL);
			}
			this.path= tempPath;
			this.extension = tempExtension;
			this.suffix = tempSuffix;
			this.alt = tempAlt;
			this.activated = true;
		}
	}
	
	private int getImageHeight()
	{
		return Integer.parseInt(this.getAsset().getMetadataValue("tiff:imageLength"));
	}
	
	private int getImageWidth()
	{
		String damAssetPath = this.getAsset().getPath();
		Resource  imageMedataData = this.resourceResolver.getResource(damAssetPath+"/jcr:content/metadata");
		ValueMap imageProperies = imageMedataData.getValueMap();
		String imageWidth = imageProperies.get("tiff:ImageWidth", "0");
		return Integer.parseInt(imageWidth);
	}
	
	private Asset getAsset()
	{
		if (this.asset == null)
		{
			String path = this.getPath();
			String extension = this.getExtension();
			String suffix = this.getSuffix();

			this.rs = this.resourceResolver.getResource(this.originalImagePath);
			String imagePath = path + extension + suffix;
			this.asset = rs.adaptTo(Asset.class);
		}
		return this.asset;
	}


	public String getPath() {
		return path;
	}
	
	//If image is placed in a row that contains multiple cells then the maximum image width can be a maximum of approximately 768px in width
	//If an image is in a row by itself then the image could be anything up to 1920 depending on screen resolution.
	public boolean IsImageInCell() throws AccessDeniedException, ItemNotFoundException, RepositoryException
	{
		this.parentNode = this.rs.getParent().adaptTo(Node.class); // 1 = parent node
		
		NodeUtils.RowType rowType = NodeUtils.getRowType(parentNode);
		
		if (rowType == NodeUtils.RowType.ROWFULLWIDTH || rowType == NodeUtils.RowType.HEROSECTION)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getPath(ImageInterchangeSize imageSize) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		
/*		if (this.IsImageInCell())
		{*/
		switch (imageSize) {
            case DEFAULT:
            	if (this.getImageWidth() > 1920) {
            		return this.getPath() + ".img.1920.high." + this.getExtension() + this.getSuffix();
            	}
            	else
            	{
            		//Maybe use the following instead?
            		//return this.getPath() + ".img.full.high." + this.getExtension() + this.getSuffix();
            		return this.getOriginalImagePath();
            	}
                    
            case SMALL:
            	if (this.getImageWidth() > 768) {
            		return this.getPath() + ".img.768.medium." + this.getExtension() + this.getSuffix();
            	}
            	else
            	{
            		return this.getOriginalImagePath();
            	}

            case MEDIUM:
            	if (this.getImageWidth() > 1024) {
            		return this.getPath() + ".img.1024.high." + this.getExtension() + this.getSuffix();
            	}
            	else
            	{
            		return this.getOriginalImagePath();
            	}
            	
            case LARGE: case RETINA:
                return this.getOriginalImagePath();
                
            default:
            	throw new UnsupportedOperationException("Image Interchange size not supported");}
		/*}
		else
		{
			switch (imageSize) {
	            case DEFAULT: case MEDIUM:
	            	if (this.getImageWidth() > 768) {
	            		return this.getPath() + ".img.768.high." + this.getExtension() + this.getSuffix();
	            	}
	            	else
	            	{
	            		//Maybe use the following instead?
	            		//return this.getPath() + ".img.full.high." + this.getExtension() + this.getSuffix();
	            		return this.getOriginalImagePath();
	            	}
	                    
	            case SMALL:
	            	if (this.getImageWidth() > 768) {
	            		return this.getPath() + ".img.768.medium." + this.getExtension() + this.getSuffix();
	            	}
	            	else
	            	{
	            		return this.getOriginalImagePath();
	            	}
	            	
	            case LARGE: case RETINA:
	                return this.getOriginalImagePath();
	                
	            default:
	            	throw new UnsupportedOperationException("Image Interchange size not supported");}
		}*/
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	
	public String getOriginalImagePath() {
		return originalImagePath;
	}

	public void setOriginalImagePath(String originalImagePath) {
		this.originalImagePath = originalImagePath;
	}

	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getAlt() {
		return alt;
	}


	public void setAlt(String alt) {
		this.alt = alt;
	}


	public Boolean isActivated() {
		return activated;
	}


	public void setActivated(Boolean activated) {
		this.activated = activated;
	}


	public String getCaption() {
		return caption;
	}


	public void setCaption(String caption) {
		this.caption = caption;
	}


	public String getImageLinkURL() {
		return imageLinkURL;
	}


	public void setImageLinkURL(String imageLinkURL) {
		this.imageLinkURL = imageLinkURL;
	}	
	
		
}

package uk.ac.nhm.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;

import javax.servlet.http.HttpServletRequest;

public class FileReference
{
	
	private final String fileReference; //Required
	//private final ImageResource image; // Required
	private final Resource resource;// Required
	private final HttpServletRequest request;// Required
	private final ValueMap properties;// Required
	private final XSSAPI xssAPI; // Optional
	private String mimeType; // Optional
	private ImageResource image; // Optional
	
	private String path;
	private String nodePath;
	private String extension;
	private String suffix;
	private String alt;
	private Boolean hasImage = false;
	
    private FileReference(FileReferenceBuilder builder) {
		this.fileReference = builder.fileReference;
		//this.image = builder.image;
		this.resource = builder.resource;
		this.request = builder.request;
		this.properties = builder.properties;
		this.image = builder.image;
		this.xssAPI = builder.xssApi;
		this.mimeType = builder.mimeType;

		if (fileReference.length() != 0 || resource.getChild("file") != null)
		{
			
			// Image is an optional parameter
			if (this.image == null)
			{
				this.image = new ImageResource(resource);
			}			
				
			
			String tempPath = request.getContextPath() + resource.getPath();
			String tempNodePath = fileReference;
			String tempAlt = htmlEncode(properties.get("alt", ""));

			// Handle extensions on both fileReference and file type images
			String tempExtension = "jpg";
			String tempSuffix = "";
			
			if (fileReference.length() != 0)
			{
				tempExtension = fileReference.substring(fileReference.lastIndexOf(".") + 1);
				tempSuffix = image.getSuffix();
				tempSuffix = tempSuffix.substring(0, tempSuffix.indexOf('.') + 1) + tempExtension;
			}
			else
			{
				Resource fileJcrContent = resource.getChild("file").getChild("jcr:content");
				if (fileJcrContent != null)
				{
					ValueMap fileProperties = fileJcrContent.adaptTo(ValueMap.class);
					if (this.mimeType == null)
					{
						this.mimeType = fileProperties.get("jcr:mimeType", "jpg");
					}
					tempExtension = mimeType.substring(mimeType.lastIndexOf("/") + 1);
				}
			}
				
			tempExtension = htmlEncode(tempExtension);
			
			this.path= tempPath;
			this.nodePath = tempNodePath;
			this.extension = tempExtension;
			this.suffix = tempSuffix;
			this.alt = tempAlt;
			this.hasImage = true;
		}

	}

	private String htmlEncode(String string)
	{
		// HtmlEncode the return values if an XssApi is passed.
		// This shouldn't be done in business logic but I'm leaving it in for backwards compatibility.
		
		if (this.xssAPI != null)
		{
			return xssAPI.encodeForHTMLAttr(string);
		}
		else
		{
			return string;
		}
		
	}
	
	public String getPath() {
		return path;
	}

	public String getNodePath() {
		return nodePath;
	}
	
	public String getExtension() {
		return extension;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getAlt() {
		return alt;
	}

	public Boolean getHasImage() {
		return hasImage;
	}

	public static class FileReferenceBuilder
	{
		private final String fileReference;
		private final Resource resource;
		private final HttpServletRequest request;
		private final ValueMap properties;
		private ImageResource image;
		private XSSAPI xssApi;
		private String mimeType;

		public FileReferenceBuilder(String fileReference, ValueMap properties, Resource resource, HttpServletRequest request)
		{
			this.fileReference = fileReference;
			//this.image = image;
			this.resource = resource;
			this.request = request;
			this.properties = properties;
		}
		
		public FileReferenceBuilder image(ImageResource image) {
			this.image = image;
			return this;
		}

		public FileReferenceBuilder xssApi(XSSAPI xssAPI) {
			this.xssApi = xssAPI;
			return this;
		}
		
		public FileReferenceBuilder mimeType(String mimeType) {
			this.mimeType = mimeType;
			return this;
		}


		public FileReference build()
		{
			return new FileReference(this);
		}
	}

}

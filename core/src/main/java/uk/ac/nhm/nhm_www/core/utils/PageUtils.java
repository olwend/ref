package uk.ac.nhm.nhm_www.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.model.AEMImage;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.wcm.api.Page;

public class PageUtils {

	public static String getPageTitle(final Page page) {
		String title = page.getNavigationTitle();

		if (title != null) {
			return title;
		}

		title = page.getTitle();

		if (title != null) {
			return title;
		}

		return page.getName();

	}

	public static String getPageParentTitle(final Page page) {
		String title = page.getNavigationTitle();

		if (title != null) {
			return title;
		}

		title = page.getTitle();

		if (title != null) {
			return title;
		}

		return page.getName();

	}

	public static Page getPageByPath(SlingHttpServletRequest request,
			String path) {
		ResourceResolver resolver = request.getResourceResolver();
		Resource r = resolver.getResource(path);
		return r.adaptTo(Page.class);
	}

	public static Resource getResourceByPath(SlingHttpServletRequest request,
			String path) {
		ResourceResolver resolver = request.getResourceResolver();
		return resolver.getResource(path);

	}
	
	public static String EncodeMetaDescription(String str)
	{
		//XssApi.EncodeHtmlAttr isn't appropriate for meta descriptions, so this returns a more simplified encoded string which is adequate for purpose (no need to protect against XSS in this case)
	    return StringUtils.replaceEach(str, new String[]{"&", "\"", "<", ">"}, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});		
	}
	
	public static AEMImage getImage(String imageVar, ValueMap properties,Resource resource, HttpServletRequest request, XSSAPI xssAPI) {
		AEMImage imageToReturn = new AEMImage();
		String fileReference = properties.get(imageVar, "");
		if (fileReference.length() != 0 || resource.getChild("file") != null) {
			String tempPath = request.getContextPath() + resource.getPath();
			String tempAlt = xssAPI
					.encodeForHTMLAttr(properties.get("alt", ""));
			ImageResource image = new ImageResource(resource);

			// Handle extensions on both fileReference and file type images
			String tempExtension = "jpg";
			String tempSuffix = "";
			if (fileReference.length() != 0) {
				tempExtension = fileReference.substring(fileReference
						.lastIndexOf(".") + 1);
				tempSuffix = image.getSuffix();
				tempSuffix = tempSuffix.substring(0,
						tempSuffix.indexOf('.') + 1) + tempExtension;
			} else {
				Resource fileJcrContent = resource.getChild("file").getChild(
						"jcr:content");
				if (fileJcrContent != null) {
					ValueMap fileProperties = fileJcrContent
							.adaptTo(ValueMap.class);
					String mimeType = fileProperties.get("jcr:mimeType", "jpg");
					tempExtension = mimeType.substring(mimeType
							.lastIndexOf("/") + 1);
				}
			}
			tempExtension = xssAPI.encodeForHTMLAttr(tempExtension);
			imageToReturn.setPath(tempPath);
			imageToReturn.setExtension(tempExtension);
			imageToReturn.setSuffix(tempSuffix);
			
			
		}
		return imageToReturn;
	
	}

	public static String getPageDescription(Page page) {
		return page.getDescription() != null ? page.getDescription() : "";
	}
	
	
	
	public static String getFormattedPublishDate(Date date)
	{
		return new SimpleDateFormat("d MMMM YYYY ").format(date);
	}
	
	public static String getPageSection(Page page) {
		String cssClassSection = "";
		
		if(page != null && page.getDepth() > 5) {
			Page landingPage = page.getAbsoluteParent(4);
			cssClassSection = landingPage.getName();
		} else if(page != null && (page.getDepth() == 5 || page.getDepth() == 4)) {
			cssClassSection = page.getName();
		}
		return cssClassSection;
		
	}


	public static String getPageKeywords(Page page) {
		String tags = "";
		String[] pageTags = page.getProperties().get("cq:tags", String[].class);
		if(pageTags != null && pageTags.length >0) {
			for(String tag: pageTags) {
				tags += tag + ", ";
			}
			tags = tags.substring(0, tags.length()-1);
		}
		return tags;
	}
	
}

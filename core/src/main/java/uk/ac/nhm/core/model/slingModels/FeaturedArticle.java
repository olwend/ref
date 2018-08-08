package uk.ac.nhm.core.model.slingModels;

import java.text.DateFormatSymbols;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.utils.TextUtils;

@Model(adaptables = SlingHttpServletRequest.class)
public class FeaturedArticle {

	private final static Logger LOG = LoggerFactory.getLogger(FeaturedArticle.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	private final TextUtils textUtils = new TextUtils();

	private String articlePath = null;
	private String title = null;
	private String description = null;
	private String tagName = null;
	private String date = null;

	@PostConstruct
	protected void init() {
		String resourcePath = properties.get("articlepath", String.class);

		this.setArticlePath(resourcePath);

		ResourceResolver resolver = request.getResourceResolver();
		Resource resource = resolver.getResource(resourcePath);
		ValueMap properties = resource.adaptTo(ValueMap.class);

		this.setTitle(properties.get("jcr:content/article/jcr:title", String.class));
		this.setDescription(properties.get("jcr:content/article/snippet", String.class));

		if(properties.containsKey("jcr:content/article/hubTag")) {
			String tagPath = "/etc/tags/nhm/" + properties.get("jcr:content/article/hubTag", String.class).split(":")[1];
			Resource tagsResource = resolver.getResource(tagPath);
			ValueMap tagsProperties = tagsResource.adaptTo(ValueMap.class);
			this.setTagName(tagsProperties.get("jcr:title", String.class).toUpperCase());
		}

		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yy/MM/dd");

		if(properties.containsKey("jcr:content/article/datepublished")) {
			DateTime dt = dateFormatter.parseDateTime(properties.get("jcr:content/article/datepublished", String.class));
			MutableDateTime mdt = dt.toMutableDateTime();
			String datePublished = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
			this.setDate(datePublished);
		}
	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticlePath() {
		return articlePath;
	}

	public void setArticlePath(String resourcePath) {
		if(resourcePath != null) {
			if(!resourcePath.isEmpty()) {
				if(!resourcePath.contains(".html")) {
					this.articlePath = resourcePath + ".html";
				} else {
					this.articlePath = resourcePath;
				}
			}
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

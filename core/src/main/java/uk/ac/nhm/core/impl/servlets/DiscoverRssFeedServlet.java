/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2014 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.jcr.api.SlingRepository;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.NameConstants;

import uk.ac.nhm.core.utils.TextUtils;

@Component(label="Natural History Museum Discover RSS Feed Servlet", 
	description="Page and Asset Site Map Servlet",
	metatype=true, 
	immediate=true)
@Service(value=Servlet.class)
@Properties({
	@Property(name="sling.servlet.paths", value={"/bin/discover-rss-feed"}, propertyPrivate = true),
	@Property(name="sling.servlet.methods", value={"GET"}, propertyPrivate = true)
})
public final class DiscoverRssFeedServlet extends SlingSafeMethodsServlet {

	private static final Logger LOG = LoggerFactory.getLogger(DiscoverRssFeedServlet.class); 
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private QueryBuilder builder;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	private static final long serialVersionUID = 1L;
	
	private static final String XML_CHARACTER_ENCODING = "UTF-8";
	private static final String XML_CONTENT_TYPE       = "text/xml";
	
	//Limit for number of articles to be returned in query results, minimum=20 
	private static final long QUERY_LIMIT = 20L;
    
    private TextUtils textUtils;

    @Activate
    protected void activate(Map<String, Object> properties) {
        this.textUtils = new TextUtils();
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType(XML_CONTENT_TYPE);
        response.setCharacterEncoding(XML_CHARACTER_ENCODING);

        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        try {
        	final Map<String, Object> param = new HashMap<String, Object>();
			param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
			final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        	
        	//Get recently published Discover articles
        	final Session session = repository.loginService("searchService", null);

		    List<Hit> nodes = getArticleNodes(session);
        	
        	//Write to XML stream
            XMLStreamWriter stream = outputFactory.createXMLStreamWriter(response.getWriter());
            stream.writeStartDocument(XML_CHARACTER_ENCODING, "1.0");
	            stream.writeStartElement("rss");
		            stream.writeAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
		            stream.writeAttribute("version", "2.0");
		            
		            stream.writeStartElement("channel");
		            
			            writeElement(stream, "title", "Discover | Natural History Museum");
			            writeElement(stream, "link", "http://www.nhm.ac.uk/discover.html");
			            writeElement(stream, "description", "Find answers to your big nature questions. Delve into stories about the Museum's collections, scientists and research. Uncover the history of life on Earth, from the smallest insects to the largest mammals.");
			            writeElement(stream, "language", "en-gb");
			            
			            writeArticleElements(stream, nodes, tagManager);
			            
		            stream.writeEndElement(); //</channel>
	            stream.writeEndElement(); //</rss>
            stream.writeEndDocument();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
    
    protected void writeArticleElements(final XMLStreamWriter stream, List<Hit> nodes, TagManager tagManager) 
    		throws XMLStreamException {
    	//Iterate through article nodes and create <item> element for each
        //Generate elements based on this spec: https://about.flipboard.com/rss-spec/
        for(Hit hits : nodes) {
			try {
				Node node = hits.getNode();
				stream.writeStartElement("item");
				
				if(node.hasProperty("jcr:content/jcr:title")) {
		    		writeElement(stream, "title", node.getProperty("jcr:content/jcr:title").getString());
		    	}
				
				writeElement(stream, "link", "http://www.nhm.ac.uk" + node.getPath().replaceAll("/content/nhmwww/en/home", "") + ".html");
				
				if(node.hasProperty("jcr:content/article/snippet")) {
		    		writeElement(stream, "description", textUtils.stripHtmlTags(node.getProperty("jcr:content/article/snippet").getString()));
		    	}
				
				//Get publish date
		    	DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yy/MM/dd");
		    	
		    	if(node.hasProperty("jcr:content/article/datepublished")) {
		    		DateTime dt = dateFormatter.parseDateTime(node.getProperty("jcr:content/article/datepublished").getString());
					MutableDateTime mdt = dt.toMutableDateTime();
					String date = getDayOfWeek(mdt.getDayOfWeek()) 
							+ ", " + getDayOfMonth(mdt.getDayOfMonth()) 
							+ " " + getMonth(mdt.getMonthOfYear()) 
							+ " " + mdt.getYear()
							+ " 00:00:00 GMT";
					writeElement(stream, "pubDate", date);
		    	}
		    	
		    	writeElement(stream, "guid", "http://www.nhm.ac.uk" + node.getPath().replaceAll("/content/nhmwww/en/home", "") + ".html");
		    	
		    	if(node.hasProperty("jcr:content/article/author")) {
		    		writeElement(stream, "dc:creator", node.getProperty("jcr:content/article/author").getString());
		    	}
		    	
		    	//Get file reference for lead image/video
		    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/articlepage")
		    			|| node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/imagepage")) {
			    	if(node.hasProperty("jcr:content/article/headType")) {
			    		if(node.getProperty("jcr:content/article/headType").getString().equals("image")) {
			    			if(node.hasProperty("jcr:content/article/altFileReference")) {
			    				String fileReference = node.getProperty("jcr:content/article/altFileReference").getString();
			    				fileReference = "http://www.nhm.ac.uk" + fileReference;
			    				writeElement(stream, "enclosure", fileReference);
			    			} else if(node.hasProperty("jcr:content/article/image/fileReference")) {
			    				String fileReference = node.getProperty("jcr:content/article/image/fileReference").getString();
			    				fileReference = "http://www.nhm.ac.uk" + fileReference;
			    				writeElement(stream, "enclosure", fileReference);
							}
			    		} else if(node.getProperty("jcr:content/article/headType").getString().equals("video")) {
			    			if(node.hasProperty("jcr:content/article/video/youtube")) {
			    				String youtubeImagePath = "http://img.youtube.com/vi/" + node.getProperty("jcr:content/article/video/youtube").getString() + "/mqdefault.jpg";
			    				writeElement(stream, "enclosure", youtubeImagePath);
					    	}
			    		}
			    	}
		    	}
		    	
		    	//Get tags
		    	if(node.hasProperty("jcr:content/article/cq:tags")) {
		    		javax.jcr.Property tagsProperty = node.getProperty("jcr:content/article/cq:tags");
		    		if(tagsProperty.isMultiple()) {
    		    		Value[] tags = tagsProperty.getValues();
    		    		List<String> tagList = new ArrayList<>();
    					if(tags.length > 0) {
    						for(int i=0; i<tags.length; i++) {
	    						Tag tag = tagManager.resolve(tags[i].getString());
	    						if(!tagList.contains(tag.getTitle())) {
		    						writeElement(stream, "category", tag.getTitle());
		    						tagList.add(tag.getTitle());
	    						}
    						}
    					}
		    		} else if(!tagsProperty.isMultiple()) {
		    			Value tags = tagsProperty.getValue();
						Tag tag = tagManager.resolve(tags.getString());
						writeElement(stream, "category", tag.getTitle());
		    		}
		    	}
		    	
				stream.writeEndElement();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }
    
    protected List<Hit> getArticleNodes(Session session) {
    	LOG.error(session.getUserID() + " running query for Discover RSS feed");
		
		Map<String, String> queryMap = new HashMap<String, String>();

		queryMap.put("path", "/content/nhmwww/en/home/discover");
	    queryMap.put("type", "cq:Page");
	    queryMap.put("orderby", "@jcr:content/article/datepublished");
		queryMap.put("orderby.sort", "desc");
		
		Query query = builder.createQuery(PredicateGroup.create(queryMap), session);
	    
	    query.setStart(0);
	    //Set limit for number of articles
	    query.setHitsPerPage(QUERY_LIMIT);

	    SearchResult result = query.getResult();
	    LOG.info(result.getQueryStatement());
	    
	    return result.getHits();
    }
    
    private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
            throws XMLStreamException {
        stream.writeStartElement(elementName);
        stream.writeCharacters(text);
        stream.writeEndElement();
    }
    
    public String getDayOfWeek(int day) {
    	String dayOfWeek = DayOfWeek.of(day).toString();
    	dayOfWeek = dayOfWeek.substring(0,3);
    	dayOfWeek = dayOfWeek.substring(0,1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();
    	return dayOfWeek;
    }
    
    public String getDayOfMonth(int day) {
    	String dayOfMonth = String.valueOf(day);
    	if(dayOfMonth.length() == 1) {
    		dayOfMonth = "0" + dayOfMonth; 
    	}
    	return dayOfMonth;
    }
    
    public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1].substring(0,3);
	}
    
}
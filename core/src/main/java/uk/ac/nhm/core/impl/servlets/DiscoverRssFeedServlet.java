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
	
	private static final long serialVersionUID = 1L;

	private static final boolean DEFAULT_INCLUDE_LAST_MODIFIED = false;

    private static final boolean DEFAULT_INCLUDE_INHERITANCE_VALUE = false;

    private static final String DEFAULT_EXTERNALIZER_DOMAIN = "publish";

    @Property(value = DEFAULT_EXTERNALIZER_DOMAIN, label = "Externalizer Domain", description = "Must correspond to a configuration of the Externalizer component.")
    private static final String PROP_EXTERNALIZER_DOMAIN = "externalizer.domain";

    @Property(boolValue = DEFAULT_INCLUDE_LAST_MODIFIED, label = "Include Last Modified", description = "If true, the last modified value will be included in the sitemap.")
    private static final String PROP_INCLUDE_LAST_MODIFIED = "include.lastmod";

    @Property(label = "Change Frequency Properties", unbounded = PropertyUnbounded.ARRAY, description = "The set of JCR property names which will contain the change frequency value.")
    private static final String PROP_CHANGE_FREQUENCY_PROPERTIES = "changefreq.properties";

    @Property(label = "Priority Properties", unbounded = PropertyUnbounded.ARRAY, description = "The set of JCR property names which will contain the priority value.")
    private static final String PROP_PRIORITY_PROPERTIES = "priority.properties";

    @Property(label = "DAM Folder Property", description = "The JCR property name which will contain DAM folders to include in the sitemap.")
    private static final String PROP_DAM_ASSETS_PROPERTY = "damassets.property";

    @Property(label = "DAM Asset MIME Types", unbounded = PropertyUnbounded.ARRAY, description = "MIME types allowed for DAM assets.")
    private static final String PROP_DAM_ASSETS_TYPES = "damassets.types";

    @Property(label = "Exclude from Sitemap Property", description = "The boolean [cq:Page]/jcr:content property name which indicates if the Page should be hidden from the Sitemap. Default value: hideInNav")
    private static final String PROP_EXCLUDE_FROM_SITEMAP_PROPERTY = "exclude.property";

    @Property(boolValue = DEFAULT_INCLUDE_INHERITANCE_VALUE, label = "Include Inherit Value", description = "If true searches for the frequency and priority attribute in the current page if null looks in the parent.")
    private static final String PROP_INCLUDE_INHERITANCE_VALUE = "include.inherit";
    
    @Property(label = "Character Encoding", description = "If not set, the container's default is used (ISO-8859-1 for Jetty)")
    private static final String PROP_CHARACTER_ENCODING_PROPERTY = "character.encoding";

    private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";

    @Reference
    private Externalizer externalizer;

    private String externalizerDomain;

    private boolean includeInheritValue;

    private boolean includeLastModified;

    private String[] changefreqProperties;

    private String[] priorityProperties;

    private String damAssetProperty;

    private List<String> damAssetTypes;

    private String excludeFromSiteMapProperty;
    
    private TextUtils textUtils;

    @Activate
    protected void activate(Map<String, Object> properties) {
        this.externalizerDomain = PropertiesUtil.toString(properties.get(PROP_EXTERNALIZER_DOMAIN),
                DEFAULT_EXTERNALIZER_DOMAIN);
        this.includeLastModified = PropertiesUtil.toBoolean(properties.get(PROP_INCLUDE_LAST_MODIFIED),
                DEFAULT_INCLUDE_LAST_MODIFIED);
        this.includeInheritValue = PropertiesUtil.toBoolean(properties.get(PROP_INCLUDE_INHERITANCE_VALUE),
                DEFAULT_INCLUDE_INHERITANCE_VALUE);
        this.changefreqProperties = PropertiesUtil.toStringArray(properties.get(PROP_CHANGE_FREQUENCY_PROPERTIES),
                new String[0]);
        this.priorityProperties = PropertiesUtil.toStringArray(properties.get(PROP_PRIORITY_PROPERTIES), new String[0]);
        this.damAssetProperty = PropertiesUtil.toString(properties.get(PROP_DAM_ASSETS_PROPERTY), "");
        this.damAssetTypes = Arrays
                .asList(PropertiesUtil.toStringArray(properties.get(PROP_DAM_ASSETS_TYPES), new String[0]));
        this.excludeFromSiteMapProperty = PropertiesUtil.toString(properties.get(PROP_EXCLUDE_FROM_SITEMAP_PROPERTY),
                NameConstants.PN_HIDE_IN_NAV);
        this.textUtils = new TextUtils();
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        try {
        	//Get 20 most recently published Discover articles
        	final Session session = repository.loginService("searchService", null);

			LOG.error(session.getUserID());
			
			Map<String, String> queryMap = new HashMap<String, String>();

			queryMap.put("path", "/content/nhmwww/en/home/discover");
		    queryMap.put("type", "cq:Page");
		    queryMap.put("orderby", "@jcr:content/article/datepublished");
			queryMap.put("orderby.sort", "desc");
			
			Query query = builder.createQuery(PredicateGroup.create(queryMap), session);
		    
		    query.setStart(0);
		    query.setHitsPerPage(20L);

		    SearchResult result = query.getResult();
		    LOG.info(result.getQueryStatement());
		    List<Hit> nodes = result.getHits();
        	
        	//Write to XML stream
            XMLStreamWriter stream = outputFactory.createXMLStreamWriter(response.getWriter());
            stream.writeStartDocument("UTF-8", "1.0");
            stream.writeStartElement("rss");
            stream.writeAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
            stream.writeAttribute("version", "2.0");
            
            stream.writeStartElement("channel");
            
            writeElement(stream, "title", "Discover | Natural History Museum");
            writeElement(stream, "link", "http://www.nhm.ac.uk/discover.html");
            writeElement(stream, "description", "Find answers to your big nature questions. Delve into stories about the Museum's collections, scientists and research. Uncover the history of life on Earth, from the smallest insects to the largest mammals.");
            writeElement(stream, "language", "en-gb");
            
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
    				
    				stream.writeEndElement();
    			} catch (RepositoryException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    }
            
            stream.writeEndElement();
            
            stream.writeEndElement();
            stream.writeEndDocument();
        } catch (Exception e) {
            throw new IOException(e);
        }
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
    	dayOfWeek = dayOfWeek.substring(0,1).toLowerCase() + dayOfWeek.substring(1);
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
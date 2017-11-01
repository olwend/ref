package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.impl.services.*;
import uk.ac.nhm.core.model.youtube.*;

/**
 * Servlet to return the videos in the channels configured in the server.
 */
@Component(metatype = false)
@Service(value = Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = {"/bin/wcm/contentfinder/youtube/view.json"}),
        @Property(name = "sling.servlet.methods", value = {"GET"}),
        @Property(name = "service.description", value = "Get YouTube Videos to show on the Content Finder")
})
public class YouTubeContentFinderServlet extends SlingSafeMethodsServlet{

	private static final long serialVersionUID = 1L;
	protected static final int firstVideoRequestLimit = 20;
		
	protected static final Logger LOG = LoggerFactory.getLogger(YouTubeContentFinderServlet.class);
	
	@Reference
	protected YouTubeCollectorService youTube;

	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
		
        /* Get the parameters. */
        final String query = request.getParameter("query");
        final String limit = request.getParameter("limit");
        
        int firstVideoIndex;
        int lastVideoIndex;
                
        if (limit == null || !limit.contains("..")) {
        	firstVideoIndex = 0;
        	lastVideoIndex = firstVideoRequestLimit;
        } else {
        	try {		
	        	firstVideoIndex = Integer.parseInt(limit.substring(0, limit.indexOf("..")));
	        	lastVideoIndex = Integer.parseInt(limit.substring(limit.indexOf("..") + 2));
        	} catch (final NumberFormatException e) {
        		firstVideoIndex = 0;
            	lastVideoIndex = firstVideoRequestLimit;
        	}
        }
        
		final JSONObject result = new JSONObject();
		
		final JSONArray hitsJSONArray = new JSONArray();
		
		/* Get Videos using the YouTube Service. */
		final List<Video> youtubeVideos = youTube.getYoutubeVideos(query, firstVideoIndex, lastVideoIndex);
		
		try {
			for (final Video video : youtubeVideos) {
				final String title = video.getTitle();
				final String thumbnail = video.getThumbnailURL();
				final String videoId = video.getId();
				final String description = video.getDescription();
				
				if (query == null || title.toLowerCase().contains(query.toLowerCase()) || description.toLowerCase().contains(query.toLowerCase())) {
					final JSONObject hit = new JSONObject();
					
					hit.put("title", title);
					hit.put("thumbnail", thumbnail);
					hit.put("excerpt", "<div><span>" + description + "</span></div>");
					hit.put("name", title);
					hit.put("videoId", videoId);
					hit.put("path", videoId);
					
					hitsJSONArray.put(hit);
				}
			}
	
			result.put("hits", hitsJSONArray);
		} catch (JSONException e) {
			LOG.error("JSON EXCEPTION", e);
		}
		
		response.getWriter().write(result.toString());
	}
}

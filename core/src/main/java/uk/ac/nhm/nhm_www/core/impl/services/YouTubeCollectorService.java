package uk.ac.nhm.nhm_www.core.impl.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.youtube.Video;
import uk.ac.nhm.nhm_www.core.model.youtube.YouTubeVideosPage;

/**
 * Service used to get the YouTube Videos list uploaded on the Channel configured on the Service from the /system/console site.
 */
@Component (label = "Youtube Collector Service", description = "Service to get information of the Videos on the Channels configured.", metatype = true, immediate = true)
@Service (value = YouTubeCollectorService.class)
@Properties(value = {
		@Property(name = "youtubeCollectorService.apiKey", label = "Youtube Public API KEY", description = "API KEY for public access, configured on &quot;https://console.developers.google.com/project&quot; inside de Youtube Project API &amp; auth, Credentials."),
        @Property(name = "youtubeCollectorService.channelIds", cardinality = 20, label = "Channel IDs", description = "List of Channels accessible by the Author Content Finder of Youtube Videos."),
        @Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.impl.services.YouTubeCollectorService", propertyPrivate = false),
        @Property(name = "service.description", value = "Service to get information of the Videos on the Channels configured.", propertyPrivate = false),
        @Property(name = "service.vendor", value = "Ensemble Systems Ltd.", propertyPrivate = false)
})
public class YouTubeCollectorService {
	
	/**
	 * Default Logger.
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(YouTubeCollectorService.class);
	
	//private static final String APIKEY = "AIzaSyD8fVb6kmRKsv6iYwuV-r6qvClEVimiOu4";
	//private static final String CHANNELID = "UCoPA-P0vEsNignppfOLNj8g";
	
	/**
	 * Request to get the information from a Channel, on the request we have to provide the Channel ID and the API Key.
	 */
	private static final String YOUTUBE_CHANNEL_LIST_REQUEST = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails&id=${CHANNELID}&key=${APIKEY}";
		
	/**
	 * Request to get the Videos on a PlayList, this request will give the first page of result, on default configuration the result returns 5 videos as maximum.
	 */
	private static final String YOUTUBE_PLAYLISTITEMS_REQUEST = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&playlistId=${PLAYLISTID}&key=${APIKEY}";
	/**
	 * Request to get the videos on a PlayList, with the information of the page result to show.
	 */
	private static final String YOUTUBE_PLAYLISTITEMS_LIST_PAGE_TOKEN_REQUEST = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,contentDetails&playlistId=${PLAYLISTID}&key=${APIKEY}&pageToken=${PAGETOKEN}";
	
	/**
	 * Videos per page on the Play List Items request.
	 */
	private static final int YOUTUBE_VIDEOS_PER_PAGE = 5;
	
	/* API Key to request for Videos and PlayLists. */
	private String apiKey;
	/* Channel Ids of Channel where we have to get their videos. */
	private List<String> channelIds;
	
	/*
	 * Get the information configured for the Service on the Configuration section in /system/console.
	 */
	@Activate
    @Modified
    protected void activate(final ComponentContext context) {
		final Object apiKeyProperty = context.getProperties().get("youtubeCollectorService.apiKey");
        final Object channelIdsProperty = context.getProperties().get("youtubeCollectorService.channelIds");
        
        this.channelIds = new ArrayList<String>();
        if (channelIdsProperty instanceof String[]) {
            for (final String channelId : (String[])channelIdsProperty) {
                channelIds.add(channelId);
            }
        }
        
        if (apiKeyProperty instanceof String) {
        	this.apiKey = (String)apiKeyProperty;
        }
	}
	
	/**
	 * Gets a List of Videos order by Upload Date, from the channels configured in the Service.
	 * 	
	 * @param query String to filter the request, the &quot;Query&quot; is checked in the Video Titles.
	 * @param startIndex First Video Index in the Search to return.
	 * @param endIndex Last Video Index in the Search to return.
	 * @return <code>List{@literal <}String{@literal >}</code> with the Videos in between the given indexes and filter.
	 */
	public List<Video> getYoutubeVideos(final String query, final int startIndex, final int endIndex) {
		final List<Video> result = new ArrayList<Video>();
		
		/* If there is not a API Key configured return null. */
		if (this.apiKey == null) {
			return result;
		}
		
		/* if there is not Channel IDs configured return null. */
		if (this.channelIds == null || this.channelIds.isEmpty()) {
			return result;
		}
		
		final int channelsCount = this.channelIds.size();
		
		/* Page YouTube Videos to get the videos from the different Channels. */
		final YouTubeVideosPage[] videosChannelPages = new YouTubeVideosPage[channelsCount];
		/* Video Index on every Channel. */
		final int[] channelIndexes = new int[channelsCount];
		for (int i = 0; i < channelsCount; i++) {
			channelIndexes[i] = 0;
		}
		
		/* Get the first page of videos from every Channel. */
		for (int i = 0; i < channelsCount; i++) {
			videosChannelPages[i] = getVideosFromChannelPage(channelIds.get(i), apiKey,null);
		}
		
		int foundVideosCount = 0;
		
		while (foundVideosCount < endIndex) {
			int videoToAddIndex = -1;
			Video videoToAdd = null;
			Date videoToAddDate = null;
			
			/* Look for the first Channel with a video already to add. */
			boolean notFoundChannelActive = true;
			for (int i = 0; i < channelsCount && notFoundChannelActive; i++) {
				if (videosChannelPages[0] != null) {
					final List<Video> videos = videosChannelPages[i].getVideos();
					if (videos != null && videos.size() > channelIndexes[i]) {
						notFoundChannelActive = false;
						videoToAddIndex = i;
						videoToAdd = videos.get(channelIndexes[i]);
						videoToAddDate = videoToAdd.getUploadDate();
						break;
					}
				}
			}
			
			/* If any channel has a video to add finish the search of videos. */
			if (videoToAddIndex == -1) {
				break;
			}
			
			/* Look for the video with the latest Upload Date, which will be the next video to add in the list. */
			for (int i = videoToAddIndex + 1; i < channelsCount; i++) {
				if (videosChannelPages[i] != null) {
					final List<Video> videos = videosChannelPages[i].getVideos();
					if (videos != null && videos.size() > channelIndexes[i]) {
						final Video videoToCompare = videos.get(channelIndexes[i]);
						final Date videoToCompareDate = videoToCompare.getUploadDate();
						if (videoToCompareDate.after(videoToAddDate)) {
							videoToAddIndex = i;
							videoToAdd = videoToCompare;
							videoToAddDate = videoToCompareDate;
						}
					}
				}
			}
			
			/* Check if the video has to be filter or not and it is in between the indexes, then added to the result list. */
			final boolean filterVideo = query == null || videoToAdd.getTitle().toLowerCase().contains(query.toLowerCase());
			if (filterVideo && foundVideosCount >= startIndex) {
				result.add(videoToAdd);
			}
			
			if (filterVideo) {
				foundVideosCount++;
			}
			
			channelIndexes[videoToAddIndex]++;
			/* Get next result Page of the channel videos result of the video founded, if the video is the last one on the Result Page. */
			if (channelIndexes[videoToAddIndex] >= YOUTUBE_VIDEOS_PER_PAGE) {
				channelIndexes[videoToAddIndex] = 0;
				final String nextPageToken = videosChannelPages[videoToAddIndex].getNextPage();
				if (nextPageToken == null) {
					videosChannelPages[videoToAddIndex] = null;
				} else {
					videosChannelPages[videoToAddIndex] = getVideosFromChannelPage(channelIds.get(videoToAddIndex), apiKey, nextPageToken);
				}
			}

		}
				
		return result;
	}
	
	/*private List<Video> getVideosFromChannel(final String channelId, final String apiKey, final String query, final int startIndex, final int endIndex){
		final List<Video> videos = new ArrayList<Video>();
		
		final String channelListUrl = YOUTUBE_CHANNEL_LIST_REQUEST.replace("${CHANNELID}", channelId).replace("${APIKEY}", apiKey);
		
		try {
			final JSONObject channelsJSONObject = getJSONResponse(channelListUrl);
			
			if (channelsJSONObject == null || !channelsJSONObject.has("items")) {
				return new ArrayList<Video>();
			}
			
			int resultIndex = 0;
			
			final JSONArray channelsItems = channelsJSONObject.getJSONArray("items");
			
			for (int i = 0; i < channelsItems.length(); i++) {
				final JSONObject channelJSONObject = channelsItems.getJSONObject(i);
				
				final String playlistID = channelJSONObject.getJSONObject("contentDetails").getJSONObject("relatedPlaylists").getString("uploads");
				
				String playlistItemsUrl = YOUTUBE_PLAYLISTITEMS_REQUEST.replace("${PLAYLISTID}", playlistID).replace("${APIKEY}", apiKey);
				
				String nextPageToken = null;
				
				do {
					final JSONObject playlistItemsJSONObject = getJSONResponse(playlistItemsUrl);
					
					if (!playlistItemsJSONObject.has("items")) {
						continue;
					}
					
					final JSONArray playlistItemsArray = playlistItemsJSONObject.getJSONArray("items");
					
					for (int j = 0; j < playlistItemsArray.length() && resultIndex < endIndex; j++) {
						final JSONObject playlistItemJSONObject = playlistItemsArray.getJSONObject(j);
						
						final String videoId = playlistItemJSONObject.getJSONObject("contentDetails").getString("videoId");
						
						final JSONObject snippetJSONObject = playlistItemJSONObject.getJSONObject("snippet");
						
						final String thumbnail   = snippetJSONObject.getJSONObject("thumbnails").getJSONObject("default").getString("url");
						final String title 		 = snippetJSONObject.getString("title");
						final String description = snippetJSONObject.getString("description");
						
						final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
						
						Date uploadDate;
						try {
							uploadDate = dateFormat.parse(snippetJSONObject.getString("publishedAt"));
						} catch (ParseException e) {
							uploadDate = null;
						}
						
						final Video video = new Video(videoId, thumbnail, title, description, uploadDate);
						
						if (resultIndex >= startIndex && resultIndex < endIndex) {
							videos.add(video);
						}
						
						resultIndex++;
					} 
					
					if (resultIndex >= endIndex) {
						nextPageToken = null;
					} else {
						if (playlistItemsJSONObject.has("nextPageToken")) {
							nextPageToken = playlistItemsJSONObject.getString("nextPageToken");
							playlistItemsUrl = YOUTUBE_PLAYLISTITEMS_LIST_PAGE_TOKEN_REQUEST.replace("${PLAYLISTID}", playlistID).replace("${APIKEY}", apiKey).replace("${PAGETOKEN}", nextPageToken);
						} else {
							nextPageToken = null;
						}
					}					
				} while (nextPageToken != null);
			}
		} catch (HttpException e) {
			LOG.error("HTTP ERROR", e);
		} catch (IOException e) {
			LOG.error("IO Exception", e);
		} catch (JSONException e) {
			LOG.error("JSON Error", e);
		} 
		
		return videos;
	}*/
	
	/**
	 * Get Page List Videos of a Channel.
	 * 
	 * @param channelId ID of the Channel to get its videos.
	 * @param apiKey API Key to make the requests.
	 * @param pageToken ID of the Page to get the videos list in the page indicated.
	 * @return <code>List{@literal <}String{@literal >}</code> with the Videos in the Channel indicated with the Page indicated, 
	 * if the pageToken is <code>null</code>, the result will be the first Page.
	 */
	private YouTubeVideosPage getVideosFromChannelPage(final String channelId, final String apiKey, final String pageToken){
		final YouTubeVideosPage result = new YouTubeVideosPage();
		
		final List<Video> videos = new ArrayList<Video>();
		
		/* Get Channel information. */
		final String channelListUrl = YOUTUBE_CHANNEL_LIST_REQUEST.replace("${CHANNELID}", channelId).replace("${APIKEY}", apiKey);
		
		try {
			/* Get JSON response. */
			final JSONObject channelsJSONObject = getJSONResponse(channelListUrl);
			
			/* If the response does not have items element, return null. */
			if (channelsJSONObject == null || !channelsJSONObject.has("items")) {
				return result;
			}
						
			final JSONArray channelsItems = channelsJSONObject.getJSONArray("items");
			
			if (channelsItems.length() > 0) {
				final JSONObject channelJSONObject = channelsItems.getJSONObject(0);
				
				/* Get the PlayList ID for the Uploads PlayList to get the Uploaded Videos. */
				final String playlistID = channelJSONObject.getJSONObject("contentDetails").getJSONObject("relatedPlaylists").getString("uploads");
				
				final String playlistItemsUrl;
				if (pageToken == null) {
					/* Get the First Page of the videos List. */
					playlistItemsUrl = YOUTUBE_PLAYLISTITEMS_REQUEST.replace("${PLAYLISTID}", playlistID).replace("${APIKEY}", apiKey);
				} else {
					/* Get a specific Page of the videos List. */
					playlistItemsUrl = YOUTUBE_PLAYLISTITEMS_LIST_PAGE_TOKEN_REQUEST.replace("${PLAYLISTID}", playlistID).replace("${APIKEY}", apiKey).replace("${PAGETOKEN}", pageToken);
				}
				
				final JSONObject playlistItemsJSONObject = getJSONResponse(playlistItemsUrl);
				
				/* If there is not Items element finish. */
				if (!playlistItemsJSONObject.has("items")) {
					return result;
				}
					
				final JSONArray playlistItemsArray = playlistItemsJSONObject.getJSONArray("items");
				
				/* Gets the videos from the JSON Response */
				for (int j = 0; j < playlistItemsArray.length(); j++) {
					final JSONObject playlistItemJSONObject = playlistItemsArray.getJSONObject(j);
						
					final String videoId = playlistItemJSONObject.getJSONObject("contentDetails").getString("videoId");
						
					final JSONObject snippetJSONObject = playlistItemJSONObject.getJSONObject("snippet");
						
					final String thumbnail   = snippetJSONObject.getJSONObject("thumbnails").getJSONObject("default").getString("url");
					final String title 		 = snippetJSONObject.getString("title");
					final String description = snippetJSONObject.getString("description");
						
					final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
						
					Date uploadDate;
					try {
						uploadDate = dateFormat.parse(snippetJSONObject.getString("publishedAt"));
					} catch (ParseException e) {
						uploadDate = null;
					}
						
					final Video video = new Video(videoId, thumbnail, title, description, uploadDate);
						
					videos.add(video);		
				} 
					
				
				
				if (playlistItemsJSONObject.has("nextPageToken")) {
					result.setNextPage(playlistItemsJSONObject.getString("nextPageToken"));
				} 
				
				result.setVideos(videos);
			}
		} catch (HttpException e) {
			LOG.error("HTTP ERROR", e);
		} catch (IOException e) {
			LOG.error("IO Exception", e);
		} catch (JSONException e) {
			LOG.error("JSON Error", e);
		} 
		
		return result;
	}
	
	/**
	 * Get the JSON Object on a URL Response.
	 * @param URL URL to get its response.
	 * @return {@link JSONObject JSONObject} with the JSON Response on the
	 * @throws HttpException If there is an error in the HTTP Communication.
	 * @throws IOException If there is an error reading the response.
	 * @throws JSONException If there is an error
	 */
	private JSONObject getJSONResponse(final String URL) throws HttpException, IOException, JSONException {
		final HttpMethod httpMethod = new GetMethod(URL);
		httpMethod.addRequestHeader("accept", "application/json");
		
		final HttpClient httpClient = new HttpClient();
		final int code = httpClient.executeMethod(httpMethod);
					
		if (code != 200) {
			LOG.error("Youtube Collector Service: Connection Error. Response Code: " + code + ".");
			return null;
		}
		
		final byte[] responseBody = httpMethod.getResponseBody();
			
		final String responseString = new String(responseBody);
		
		return new JSONObject(responseString);		
	}
}

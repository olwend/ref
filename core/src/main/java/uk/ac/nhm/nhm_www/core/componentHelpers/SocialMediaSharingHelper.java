package uk.ac.nhm.nhm_www.core.componentHelpers;
import org.apache.sling.api.resource.Resource;
import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;

public class SocialMediaSharingHelper extends HelperBase {

	public SocialMediaSharingHelper(HelperFactory factory) {
		factory.getProperties();
	}
	
	public SocialMediaSharingHelper(Page page) {
		
	}
	
	public String replaceUrlParameters(String sourceString, String[] parameters, String[] values)
	{
		String returnValue = sourceString;
		
		if (sourceString != null && parameters != null && values != null && (parameters.length == values.length))
		{
	
			for(int i =0; i < parameters.length; i++)
			  {
					returnValue = returnValue.replace("{"+parameters[i]+"}", values[i]);
			  }

		}
		else
		{
			if (sourceString == null)
			{
				throw new IllegalArgumentException("sourceString must not be null");
			}
			else if (values == null)
			{
				throw new IllegalArgumentException("values must not be null");
			}
			else if (parameters == null)
			{
				throw new IllegalArgumentException("parameters must not be null");
			}
			else if (parameters.length != values.length)
			{
				throw new IllegalArgumentException("parameters and values must contain the same number of items");
			}
		}
		return returnValue;
	}

}
package apps.nhmwww.components.functional.contentmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.resource.collection.ResourceCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.content.FragmentRenderService;
import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.policies.ContentPolicy;
import com.day.cq.wcm.api.policies.ContentPolicyManager;
import com.day.durbo.DurboInput.Node;

import javax.servlet.WriteListener;

public class ContentModelUsePojo extends WCMUsePojo {

    private static final Logger LOG = LoggerFactory.getLogger(ContentModelUsePojo.class);

    private String title;
    private String description;
    private String imagePath;
    private String link;

    @Override
    public void activate() {
        String cfReference = getProperties().get("fileReference", null);
		
        Resource fragmentResource = resourceResolver.getResource(cfReference);
    }


}
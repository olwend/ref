package uk.ac.nhm.core.componentHelpers;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.core.utils.NodeUtils;

public class SocialSignupHelper extends HelperBase {

	/**
	 * Provide a default data protection statement
	 */
	private String dataProtection = "<p>Sign up for our emails and receive regular updates about upcoming events and exhibitions."
			+ " <a href=\"http://www.nhm.ac.uk/about-us/privacy-policy.html\">Privacy notice</a>.</p>";

	private String title = "Don't miss a thing";
	private String description = "Follow us on social media";

	/**
	 * Set a default campaign in case it does not get set
	 */
	private String campaign = "eNewsletters";

	private Resource resource;
	private boolean isFullWidthCTA;
	private Node parentNode;
	private String rowType;
	private String cellType;


	public SocialSignupHelper(ValueMap properties, Resource resource)
		throws AccessDeniedException, ItemNotFoundException, RepositoryException, PersistenceException {
		this.resource = resource;
		this.parentNode = this.resource.getParent().adaptTo(Node.class); // 1 = parent node

		if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROWFULLWIDTH)
		{
			this.rowType = "fullwidth";
		}
		else if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROW2CELLS12)
		{
			this.rowType = "2cells12";
			this.cellType = parentNode.getPath().split(rowType+"/")[1];
			if ( cellType.equals("par") ) {
				this.rowType = "2cells1col";
			} else if ( cellType.equals("par2") ) {
				this.rowType = "2cells2col";
			}
		}
		else if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROW2CELLS21)
		{
			this.rowType = "2cells21";
			this.cellType = parentNode.getPath().split(rowType+"/")[1];
			if ( cellType.equals("par") ) {
				this.rowType = "2cells2col";
			} else if ( cellType.equals("par2") ) {
				this.rowType = "2cells1col";
			}
		}
		else if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROW2CELLSEQUALS)
		{
			this.rowType = "2cellsequal";
		}

		if (properties.get("title") != null) {
			this.title = properties.get("title", String.class);
		}
		if (properties.get("description") != null) {
			this.description = properties.get("description", String.class);
		}
		if (properties.get("campaign") != null) {
			this.campaign = properties.get("campaign", String.class);
		}
		if (properties.get("dataProtection") != null) {
			this.dataProtection = properties.get("dataProtection", String.class);
		} else {
			// Modify the resource in the content JCR tree
			ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
			map.put("dataProtection", dataProtection);
			resource.getResourceResolver().commit();
		}
	}

	public String getRow() {
		return rowType;
	}

	public void setRow(String rowType) {
		this.rowType = rowType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getDataProtection() {
		return dataProtection;
	}

	public void setDataProtection(String dataProtection) {
		this.dataProtection = dataProtection;
	}

}

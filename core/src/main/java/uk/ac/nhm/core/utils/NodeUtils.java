package uk.ac.nhm.core.utils;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;

public class NodeUtils {

	public enum RowType {HEROSECTION,ROW2CELLS12,ROW2CELLS21,ROW2CELLSEQUALS,ROW3CELLS,ROWFULLWIDTH}
	
	public static RowType getRowType(final Node node) throws AccessDeniedException, ItemNotFoundException, RepositoryException  {
		
	
		String nodeName = getNameofContainerRow(node);
		
		if (nodeName.startsWith("herosection")) {
			return RowType.HEROSECTION;
		}
		else if (nodeName.startsWith("row2cells12")) {
			return RowType.ROW2CELLS12;
		}
		else if (nodeName.startsWith("row2cells21")) {
			return RowType.ROW2CELLS21;
		}
		else if (nodeName.startsWith("row2cellsequals")) {
			return RowType.ROW2CELLSEQUALS;
		}
		else if (nodeName.startsWith("row3cells")) {
			return RowType.ROW3CELLS;
		}
		else if (nodeName.startsWith("rowfullwidth")) {
			return RowType.ROWFULLWIDTH;
		}
		else
		{
			return null;
		}

	}
	
	public static String getNameofContainerRow(final Node node) throws AccessDeniedException, ItemNotFoundException, RepositoryException
	{
		String path = node.getPath();
		String pathFromLastRow = StringUtils.EMPTY;
		String nameOfLastRow = StringUtils.EMPTY;
		if(path.contains("/row")){
			pathFromLastRow = path.substring(path.lastIndexOf("/row"));
			nameOfLastRow = pathFromLastRow.split("/")[1];
		} else if (path.contains("herosection")){
			pathFromLastRow = path.substring(path.lastIndexOf("/herosection"));
			nameOfLastRow = pathFromLastRow.split("/")[1];
		}
		return nameOfLastRow;
	}
	
	public static Node getAncestor(Node node, int level) throws AccessDeniedException, ItemNotFoundException, RepositoryException
	{
		Node ancestorNode = node;
		// 0 returns self, 1 returns the parent node, 2 returns the grandparent node, etc.
		for (int i = 0; i<=level; i++)
		{
			ancestorNode = ancestorNode.getParent();
		}
		return ancestorNode;
	}


}
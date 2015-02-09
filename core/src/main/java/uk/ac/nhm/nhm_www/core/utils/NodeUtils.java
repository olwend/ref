package uk.ac.nhm.nhm_www.core.utils;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

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
		String pathFromLastRow = path.substring(path.lastIndexOf("/row"));
		String nameOfLastRow = pathFromLastRow.split("/")[1];
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
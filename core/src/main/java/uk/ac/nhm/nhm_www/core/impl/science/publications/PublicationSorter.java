package uk.ac.nhm.nhm_www.core.impl.science.publications;

import java.util.Comparator;

import uk.ac.nhm.nhm_www.core.model.science.Publication;

public class PublicationSorter implements Comparator<Publication> {

	@Override
	public int compare(Publication o1, Publication o2) {
		return o1.getPublicationYear().compareTo(o2.getPublicationYear());
	}
	
}

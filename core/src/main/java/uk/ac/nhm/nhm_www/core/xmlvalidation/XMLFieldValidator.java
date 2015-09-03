package uk.ac.nhm.nhm_www.core.xmlvalidation;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;

public interface XMLFieldValidator {
	
	public boolean validate(Field field);

}

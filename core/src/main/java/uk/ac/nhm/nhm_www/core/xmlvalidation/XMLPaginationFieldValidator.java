package uk.ac.nhm.nhm_www.core.xmlvalidation;

import java.math.BigInteger;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;

public class XMLPaginationFieldValidator implements XMLFieldValidator {

	@Override
	public boolean validate(Field field) {
		try {
			BigInteger b = field.getPagination().getPageCount();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

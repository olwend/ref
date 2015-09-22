package uk.ac.nhm.nhm_www.core.xmlvalidation;

import java.math.BigInteger;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;

public class XMLDateFieldValidator implements XMLFieldValidator {

	public boolean validate(Field field) {
		try {
			BigInteger b = field.getDate().getYear();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validateMonth(Field field) {
		try {
			BigInteger b = field.getDate().getMonth();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validateDay(Field field) {
		try {
			BigInteger b = field.getDate().getDay();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

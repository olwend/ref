package uk.ac.nhm.nhm_www.core.xmlvalidation;

import java.math.BigInteger;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;

public class XMLMoneyFieldValidator implements XMLFieldValidator {

	@Override
	public boolean validate(Field field) {
		try {
			BigInteger b = field.getMoney().getValue();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

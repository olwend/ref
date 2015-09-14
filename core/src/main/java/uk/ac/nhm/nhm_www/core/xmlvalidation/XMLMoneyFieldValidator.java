package uk.ac.nhm.nhm_www.core.xmlvalidation;

import java.math.BigDecimal;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;

public class XMLMoneyFieldValidator implements XMLFieldValidator {

	@Override
	public boolean validate(Field field) {
		try {
			BigDecimal b = field.getMoney().getValue();
			b.intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

package uk.ac.nhm.nhm_www.core.model.science;

public class PhoneNumber {
	private String phone;
	private String label;
	
	public PhoneNumber(String phone, String label) {
		super();
		this.phone = phone;
		this.label = label;
	}

	public String getPhone() {
		return phone;
	}

	public String getLabel() {
		return label;
	}

	public boolean isValid() {
		return this.phone != null;
	}
	
}

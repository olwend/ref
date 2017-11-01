package uk.ac.nhm.core.model.science;

public class EmailAddress {

	private String emailAddress;
	private String type;
	
	public EmailAddress(String emailAddress, String type) {
		super();
		this.emailAddress = emailAddress;
		this.type = type;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public String getType() {
		return type;
	}
	
	public boolean isValid() {
		return this.emailAddress != null;
	}
	
}

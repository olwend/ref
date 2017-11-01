//package uk.ac.nhm.core.impl;
//
//import org.apache.felix.scr.annotations.Component;
//import org.apache.felix.scr.annotations.Reference;
//import org.apache.felix.scr.annotations.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.day.cq.mailer.MessageGateway;
//import com.day.cq.mailer.MessageGatewayService;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
//
//import uk.ac.nhm.core.EmailThisService;
//
//
//@Service 
//@Component(immediate=true, metatype=true)
//public class EmailThisServiceImpl implements EmailThisService{
//
//	/** Default log. */
//	protected final Logger log = LoggerFactory.getLogger(EmailThisServiceImpl.class);
//	s
//	@Reference
//	private MessageGatewayService messageGatewayService;
//	
//	public void sendEmail(String emailFrom, String emailTo) throws EmailException {
//		log.info("Here in sendEmail(emailFrom,emailTo) method");    //ensure that the execute method is invoked    
//        
//	    //Declare a MessageGateway service
//	    MessageGateway<Email> messageGateway; 
//	          
//	    //Set up the Email message
//	    Email email = new SimpleEmail();
//	          
//	    //Set the mail values
//	    String emailToRecipients = emailTo; 
//	     
//	    email.addTo(emailToRecipients);
//	    email.setSubject("AEM Email page to");
//	    email.setFrom(emailFrom); 
//	    email.setMsg("This message is to inform you that the CQ content has been deleted");
//	      
//	    //Inject a MessageGateway Service and send the message
//	    messageGateway = messageGatewayService.getGateway(Email.class);
//	    
//	    // Check the logs to see that messageGateway is not null
//	    messageGateway.send((Email) email);
//	}
//
//	public void sendEmail() {
//		log.error("Here in sendEmail() method");    
//		
//	}
//}

package uk.ac.nhm.core.impl.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

@Component(service = RegexServlet.class)
@Path("/test")
public class RegexServlet {
 
  @GET
  @Produces({MediaType.TEXT_PLAIN})
  public String getProductDetails() {
	  
	    return "code="; 
    }
  
}

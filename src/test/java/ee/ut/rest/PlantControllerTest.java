package ee.ut.rest;

import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RunWith(JUnit4.class)
public class PlantControllerTest {
	
	private static String DOMAIN_URL = "http://rentit4.herokuapp.com/";
	
    @Test
    public void testGetPlantPrice() {
    	ClientResponse response = createPlantResource();
    	URI location = response.getLocation();
    	assertTrue(getPlantResource(location).getStatus() == ClientResponse.Status.OK.getStatusCode());
    }
    
    private ClientResponse getPlantResource(URI location) {
    	Client client = Client.create();
    	WebResource webResource = client.resource(location);
    	return webResource.type(MediaType.APPLICATION_XML)
												.accept(MediaType.APPLICATION_XML)
												.get(ClientResponse.class);
    }
    
    private ClientResponse createPlantResource() {
    	Client client = Client.create();
    	WebResource webResource = client.resource(DOMAIN_URL + "rest/plant");
    	
		PlantResource plant = new PlantResource();
		plant.setName("Hammer");
		plant.setCostPerDay(44.0);
		plant.setDescription("Its a hammer");

    	return  webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, plant);
    }

}
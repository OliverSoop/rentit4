package ee.ut.rest;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.domain.POstatus;
import ee.ut.model.Plant;
import ee.ut.repository.PlantRepository;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class PurchaseOrderControllerTest {

	@Autowired
	PlantRepository plantRepository;

	private static String DOMAIN_URL = "http://rentit4.heroku.com/";

		
	
}

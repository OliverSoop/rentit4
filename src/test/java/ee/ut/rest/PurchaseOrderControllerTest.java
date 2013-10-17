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

	private static String DOMAIN_URL = "http://rentit4.herokuapp.com/";

	@Test
	public void testSubmitPO() {
		ClientResponse response = createPurchaseOrderResource();
		assertTrue(response.getStatus() == ClientResponse.Status.CREATED
				.getStatusCode());
	}

	@Test
	public void testModifyPO() {
		ClientResponse response = createPurchaseOrderResource();
		if (response.getStatus() == ClientResponse.Status.CREATED
				.getStatusCode()) {

			URI uri = response.getLocation();

			ClientResponse responseModified = modifyPurchaseOrderResource(uri);

			if (responseModified.getStatus() == ClientResponse.Status.CREATED
					.getStatusCode()) {
				assertTrue(getPurchaseOrderResource(
						responseModified.getLocation()).getStatus() == ClientResponse.Status.OK
						.getStatusCode());
			}
		}
	}

	@Test
	public void testCancelPO() {

		ClientResponse response = createPurchaseOrderResource();
		URI uri = response.getLocation();

		Client client = Client.create();
		WebResource webResource = client.resource(uri);
		ClientResponse cancelResponse = webResource
				.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		assertTrue(cancelResponse.getStatus() == ClientResponse.Status.OK
				.getStatusCode());

	}

	private ClientResponse getPurchaseOrderResource(URI location) {
		Client client = Client.create();
		WebResource webResource = client.resource(location);
		return webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
	}

	private ClientResponse createPurchaseOrderResource() {
		Client client = Client.create();
		WebResource webResource = client.resource(DOMAIN_URL
				+ "rest/purchaseorders");

		Plant plant = new Plant();
		PurchaseOrderResource por = new PurchaseOrderResource();
		plant.persist();

		por.setExternalID("1");
		por.setPlantID(por.getPlantID());
		por.setStartDate(new Date());
		por.setEndDate(new Date());
		por.setConstructionSite("Liivi 2");
		por.setSiteEngineer("Toivo");
		por.setTotalCost(por.getTotalCost());
		por.setPOrecievedDate(por.getPOrecievedDate());
		por.setStatus(POstatus.RECIEVED);
		por.setReturnDate(new Date());

		return webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, plant);
	}

	private ClientResponse modifyPurchaseOrderResource(URI uri) {
		Client client = Client.create();
		WebResource webResource = client.resource(uri);

		Plant plant = new Plant();
		PurchaseOrderResource por = new PurchaseOrderResource();
		plant.persist();

		por.setExternalID("2");
		por.setPlantID(por.getPlantID());
		por.setStartDate(new Date());
		por.setEndDate(new Date());
		por.setConstructionSite("Liivi 4");
		por.setSiteEngineer("Maimu");
		por.setTotalCost(por.getTotalCost());
		por.setPOrecievedDate(por.getPOrecievedDate());
		por.setStatus(POstatus.MODIFICATION_REQUESTED);
		por.setReturnDate(new Date());

		return webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, por);
	}
}

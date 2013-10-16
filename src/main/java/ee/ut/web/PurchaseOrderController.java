package ee.ut.web;

import java.net.URI;

import ee.ut.domain.POstatus;
import ee.ut.model.Plant;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.PurchaseOrderResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/rest/purchaseorders")
@Controller
@RooWebScaffold(path = "/rest/purchaseorders", formBackingObject = PurchaseOrder.class)
public class PurchaseOrderController {

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<Void> submitPurchaseOrder(
			@RequestBody PurchaseOrderResource por) {

		PurchaseOrder po = new PurchaseOrder();
		Plant plant = new Plant();
		plant.setDescription(por.getPlantID().getDescription());
		plant.setName(por.getPlantID().getName());
		plant.setCostPerDay(por.getPlantID().getCostPerDay());
		plant.persist();

		po.setExternalID(por.getExternalID());
		po.setPlantID(plant);
		po.setStartDate(por.getStartDate());
		po.setEndDate(por.getEndDate());
		po.setConstructionSite(por.getConstructionSite());
		po.setSiteEngineer(por.getSiteEngineer());
		po.setTotalCost(por.getTotalCost());
		po.setPORecievedDate(por.getPOrecievedDate());
		po.setStatus(POstatus.RECIEVED);
		po.setReturnDate(por.getReturnDate());
		po.persist();

		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.pathSegment(po.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<>(headers,
				HttpStatus.CREATED);
		return response;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/modify")
	public ResponseEntity<Void> modifyPurchaseOrder(@PathVariable Long id,
			@RequestBody PurchaseOrderResource por) {

		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		Plant plant = new Plant();
		plant.setDescription(por.getPlantID().getDescription());
		plant.setName(por.getPlantID().getName());
		plant.setCostPerDay(por.getPlantID().getCostPerDay());
		plant.persist();
		ResponseEntity<Void> response;

		if (po != null) {
			po.setExternalID(por.getExternalID());
			po.setPlantID(plant);
			po.setStartDate(por.getStartDate());
			po.setEndDate(por.getEndDate());
			po.setConstructionSite(por.getConstructionSite());
			po.setSiteEngineer(por.getSiteEngineer());
			po.setTotalCost(por.getTotalCost());
			po.setPORecievedDate(por.getPOrecievedDate());
			po.setStatus(POstatus.MODIFICATION_REQUESTED);
			po.setReturnDate(por.getReturnDate());
			po.persist();

			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/cancel")
	public ResponseEntity<Void> cancelPurchaseOrder(@PathVariable Long id) {

		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		ResponseEntity<Void> response;

		if (po != null) {
			po.setStatus(POstatus.CANCELLATION_REQUESTED);
			po.persist();
			response = new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

}

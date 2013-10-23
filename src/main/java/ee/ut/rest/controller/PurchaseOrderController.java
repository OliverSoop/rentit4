package ee.ut.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.net.URI;

import ee.ut.domain.POstatus;
import ee.ut.model.Plant;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.rest.PurchaseOrderResourceAssembler;
import ee.ut.util.ExtendedLink;

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
		Plant plant = Plant.findPlant(por.getPlantID());

		HttpHeaders headers = new HttpHeaders();

		ResponseEntity<Void> response;
		if (plant == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
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
			URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.pathSegment(po.getId().toString()).build().toUri();
			headers.setLocation(location);
			response = new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/modify")
	public ResponseEntity<Void> updatePO(@PathVariable Long id,
			@RequestBody PurchaseOrderResource por) {

		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);

		ResponseEntity<Void> response;
		Plant plant = Plant.findPlant(por.getPlantID());

		if (po != null && plant != null) {
			po.setExternalID(por.getExternalID());
			po.setPlantID(plant);
			po.setStartDate(por.getStartDate());
			po.setEndDate(por.getEndDate());
			po.setConstructionSite(por.getConstructionSite());
			po.setSiteEngineer(por.getSiteEngineer());
			po.setTotalCost(por.getTotalCost());
			po.setPORecievedDate(por.getPOrecievedDate());
			po.setStatus(POstatus.PENDING_UPDATE);
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

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PurchaseOrderResource> getPO(
			@PathVariable("id") Long id) throws NoSuchMethodException,
			SecurityException {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		PurchaseOrderResourceAssembler assembler = new PurchaseOrderResourceAssembler();
		PurchaseOrderResource resource = assembler.toResource(po);

		switch (po.getStatus()) {
		case PENDING_CONFIRMATION:
			Method _rejectPO = PurchaseOrderController.class.getMethod(
					"rejectPO", Long.class);
			Method _acceptPO = PurchaseOrderController.class.getMethod(
					"acceptPO", Long.class);
			String acceptLink = linkTo(_acceptPO, po.getId()).toUri()
					.toString();
			resource.add(new ExtendedLink(acceptLink, "acceptPO", "POST"));

			String rejectLink = linkTo(_rejectPO, po.getId()).toUri()
					.toString();
			resource.add(new ExtendedLink(rejectLink, "rejectPO", "DELETE"));
		case REJECTED:
		case OPEN:
			Method _closePO = PurchaseOrderController.class.getMethod(
					"closePO", Long.class);
			String closeLink = linkTo(_closePO, po.getId()).toUri().toString();
			resource.add(new ExtendedLink(closeLink, "closePO", "DELETE"));

			Method _updatePO = PurchaseOrderController.class.getMethod(
					"updatePO", Long.class);
			String updateLink = linkTo(_updatePO, po.getId()).toUri()
					.toString();
			resource.add(new ExtendedLink(updateLink, "updatePO", "POST"));
		case PENDING_UPDATE:
			break;
		default:
			break;
		}
		ResponseEntity<PurchaseOrderResource> response = new ResponseEntity<>(
				resource, HttpStatus.OK);
		return response;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/pos/{id}/accept")
	public ResponseEntity<Void> acceptPO(@PathVariable("id") Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		ResponseEntity<Void> response;
		if (po.getStatus().equals(POstatus.PENDING_CONFIRMATION)) {
			po.setStatus(POstatus.OPEN);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pos/{id}/reject")
	public ResponseEntity<Void> rejectPO(@PathVariable Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		if (po != null) {
			if (po.getStatus().equals(POstatus.PENDING_CONFIRMATION)) {
				po.setStatus(POstatus.REJECTED);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/pos/{id}")
	public ResponseEntity<Void> closePO(@PathVariable("id") Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		ResponseEntity<Void> response;
		if (po.getStatus().equals(POstatus.OPEN)) {
			po.setStatus(POstatus.CANCELLED);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}
}

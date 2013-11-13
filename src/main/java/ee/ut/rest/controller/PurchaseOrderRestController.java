package ee.ut.rest.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ee.ut.domain.POstatus;
import ee.ut.model.Plant;
import ee.ut.model.PurchaseOrder;
import ee.ut.rest.PurchaseOrderResource;
import ee.ut.rest.PurchaseOrderResourceAssembler;
import ee.ut.util.ExtendedLink;

@RequestMapping("/rest/purchaseorders")
@Controller
public class PurchaseOrderRestController {

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<PurchaseOrderResource> createPO(@RequestBody PurchaseOrderResource por) {

		PurchaseOrder po = new PurchaseOrder();
		Plant plant = Plant.findPlant(por.getPlantID());

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<PurchaseOrderResource> response;
		if (plant == null) {
			response = new ResponseEntity<PurchaseOrderResource>(HttpStatus.NOT_FOUND);
		} else {
			po.setExternalID(por.getExternalID());
			po.setPlantID(plant);
			po.setStartDate(por.getStartDate());
			po.setEndDate(por.getEndDate());
			po.setConstructionSite(por.getConstructionSite());
			po.setSiteEngineer(por.getSiteEngineer());
			po.setTotalCost(por.getTotalCost());
			po.setPORecievedDate(por.getPOrecievedDate());
			po.setStatus(POstatus.PENDING_CONFIRMATION);
			po.setReturnDate(por.getReturnDate());
			po.persist();
			
			
			PurchaseOrderResourceAssembler assembler = new PurchaseOrderResourceAssembler();
			PurchaseOrderResource resource = assembler.toResource(po);
			
			Method getPO;
			try {
				getPO = PurchaseOrderRestController.class.getMethod(
						"getPO", Long.class);
				String getLink = linkTo(getPO, po.getId()).toUri()
						.toString();
				resource.add(new ExtendedLink(getLink, "self", "GET"));
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.pathSegment(po.getId().toString()).build().toUri();
			headers.setLocation(location);
			response = new ResponseEntity<PurchaseOrderResource>(
					resource, headers, HttpStatus.CREATED);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/modify")
	public ResponseEntity<Void> requestPOUpdate(@PathVariable Long id,
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
		if (po != null) {
			PurchaseOrderResourceAssembler assembler = new PurchaseOrderResourceAssembler();
			PurchaseOrderResource resource = assembler.toResource(po);

			switch (po.getStatus()) {
			case PENDING_CONFIRMATION:
				Method _rejectPO = PurchaseOrderRestController.class.getMethod(
						"rejectPO", Long.class);
				String rejectLink = linkTo(_rejectPO, po.getId()).toUri()
						.toString();
				resource.add(new ExtendedLink(rejectLink, "rejectPO", "DELETE"));

				Method _acceptPO = PurchaseOrderRestController.class.getMethod(
						"acceptPO", Long.class);
				String acceptLink = linkTo(_acceptPO, po.getId()).toUri()
						.toString();
				resource.add(new ExtendedLink(acceptLink, "acceptPO", "POST"));
			case REJECTED:
			case OPEN:
				Method _closePO = PurchaseOrderRestController.class.getMethod(
						"closePO", Long.class);
				String closeLink = linkTo(_closePO, po.getId()).toUri()
						.toString();
				resource.add(new ExtendedLink(closeLink, "closePO", "DELETE"));

				Method _requestPOUpdate = PurchaseOrderRestController.class
						.getMethod("requestPOUpdate", Long.class);
				String updateLink = linkTo(_requestPOUpdate, po.getId())
						.toUri().toString();
				resource.add(new ExtendedLink(updateLink, "requestPOUpdate",
						"POST"));
			case PENDING_UPDATE:
				Method _rejectPOUpdate = PurchaseOrderRestController.class
						.getMethod("rejectPOUpdate", Long.class);
				String rejectPOUpdateLink = linkTo(_rejectPOUpdate, po.getId())
						.toUri().toString();
				resource.add(new ExtendedLink(rejectPOUpdateLink,
						"rejectPOUpdate", "DELETE"));

				Method _acceptPOUpdate = PurchaseOrderRestController.class
						.getMethod("acceptPOUpdate", Long.class);
				String acceptPOUpdateLink = linkTo(_acceptPOUpdate, po.getId())
						.toUri().toString();
				resource.add(new ExtendedLink(acceptPOUpdateLink,
						"acceptPOUpdate", "POST"));
				break;
			default:
				break;
			}
			ResponseEntity<PurchaseOrderResource> response = new ResponseEntity<>(
					resource, HttpStatus.OK);
			return response;
		}
		return new ResponseEntity<PurchaseOrderResource>(HttpStatus.NOT_FOUND);
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/pos/{id}/reject")
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
			po.setStatus(POstatus.CLOSED);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/pos/{id}/updates")
	public ResponseEntity<Void> rejectPOUpdate(@PathVariable("id") Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		ResponseEntity<Void> response;
		if (po.getStatus().equals(POstatus.PENDING_UPDATE)) {
			po.setStatus(POstatus.UPDATE_REJECTED);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pos/{id}/updates/{up.id}/accept")
	public ResponseEntity<Void> acceptPOUpdate(@PathVariable("id") Long id) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(id);
		ResponseEntity<Void> response;
		if (po.getStatus().equals(POstatus.PENDING_UPDATE)) {
			po.setStatus(POstatus.OPEN);
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}
}

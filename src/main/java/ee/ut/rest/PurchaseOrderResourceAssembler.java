package ee.ut.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import ee.ut.model.PurchaseOrder;
import ee.ut.rest.controller.PurchaseOrderRestController;

public class PurchaseOrderResourceAssembler extends
		ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderResource> {

	public PurchaseOrderResourceAssembler() {
		super(PurchaseOrderRestController.class, PurchaseOrderResource.class);
	}

	public static PurchaseOrderResource submit(PurchaseOrder po) {
		return null;
	}

	@Override
	public PurchaseOrderResource toResource(PurchaseOrder po) {
		PurchaseOrderResource por = createResourceWithId(po.getId(), po);
		por.setEndDate(po.getEndDate());
		por.setStartDate(po.getStartDate());
		por.setConstructionSite(po.getConstructionSite());
		por.setExternalID(Long.toString(po.getId()));
		por.setPlantID(po.getPlantID().getId());
		por.setStatus(po.getStatus());
		por.setSiteEngineer(po.getSiteEngineer());
		por.setTotalCost(po.getTotalCost());
		por.setEmail(po.getEmail());
		return por;
	}
}

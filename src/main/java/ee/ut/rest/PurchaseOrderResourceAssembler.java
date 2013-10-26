package ee.ut.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import ee.ut.model.PurchaseOrder;
import ee.ut.rest.controller.PurchaseOrderController;

public class PurchaseOrderResourceAssembler extends
		ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderResource> {

	public PurchaseOrderResourceAssembler() {
		super(PurchaseOrderController.class, PurchaseOrderResource.class);
	}

	public static PurchaseOrderResource submit(PurchaseOrder po) {
		return null;
	}

	@Override
	public PurchaseOrderResource toResource(PurchaseOrder po) {
		PurchaseOrderResource por = createResourceWithId(po.getId(), po);
		por.setEndDate(po.getEndDate());
		por.setStartDate(po.getStartDate());
		por.setTotalCost(por.getTotalCost());
		PlantResource plantResource;
		if (por.getPlantID() != null) {
			PlantResourceAssembler assembler = new PlantResourceAssembler();
			plantResource = assembler.toResource(por.getPlantID());
		//	por.setPlantID(plantResource);
		}
		return por;
	}
}

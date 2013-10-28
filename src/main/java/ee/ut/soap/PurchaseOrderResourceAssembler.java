package ee.ut.soap;

import ee.ut.model.PurchaseOrder;

public class PurchaseOrderResourceAssembler {

	public PurchaseOrderResource create(PurchaseOrder po) {
		PurchaseOrderResource por = new PurchaseOrderResource();
		por.setEndDate(po.getEndDate());
		por.setStartDate(po.getStartDate());
		por.setTotalCost(po.getTotalCost());
		por.setSiteEngineer(po.getSiteEngineer());
		por.setExternalId(Long.toString(po.getId()));
		por.setPoRecievedDate(po.getPORecievedDate());
		por.setPlantId(po.getPlantID().getId());
		por.setStatus(po.getStatus());
		return por;
	}
}

package ee.ut.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.model.Plant;
import ee.ut.model.PurchaseOrder;

@RooJpaRepository(domainType = PurchaseOrder.class)
public interface PurchaseOrderRepository {

	@Query("SELECT po FROM PurchaseOrder AS po WHERE po.Status = 'PENDING_CONFIRMATION'")
	@Transactional(readOnly=true)
	List<Plant> findPOsPendingConfirmation();
}

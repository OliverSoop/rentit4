package ee.ut.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.model.Plant;

@RooJpaRepository(domainType = Plant.class)
public interface PlantRepository {
	
	@Query("SELECT p FROM Plant AS p WHERE NOT EXISTS(SELECT po FROM PurchaseOrder AS po WHERE p.id = po.PlantID AND :startDate BETWEEN po.StartDate AND po.EndDate AND :endDate BETWEEN po.StartDate AND po.EndDate)")
	@Transactional(readOnly=true)
	List<Plant> findAvailablePlants(@Param("startDate") Date startDate,
									  @Param("endDate") Date endDate);
}

package ee.ut.repository;
import ee.ut.model.Plant;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Plant.class)
public interface PlantRepository {
}

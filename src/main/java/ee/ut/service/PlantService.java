package ee.ut.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ee.ut.model.Plant;
import ee.ut.repository.PlantRepository;

@Service
@Qualifier("plantService") 
public class PlantService {

	@Autowired
	private PlantRepository plantRepo;
	
	public List<Plant> getAvailablePlants(Date startDate, Date endDate) {
		return plantRepo.findAvailablePlants(startDate, endDate);
	}
}

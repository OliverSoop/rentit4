package ee.ut.rest;

import ee.ut.model.Plant;

public class PlantResourceAssembler {
	
	public static PlantResource create(Plant phr) {
		PlantResource plant = new PlantResource();
		plant.setName(phr.getName());
		plant.setCostPerDay(phr.getCostPerDay());
		plant.setDescription(phr.getDescription());
		
		return plant;
	}

}
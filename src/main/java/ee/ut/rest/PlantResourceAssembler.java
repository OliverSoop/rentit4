package ee.ut.rest;

import ee.ut.model.Plant;

public class PlantResourceAssembler {
	
	public static PlantResource create(Plant plant) {
		PlantResource plantResource = new PlantResource();
		plantResource.setName(plant.getName());
		plantResource.setCostPerDay(plant.getCostPerDay());
		plantResource.setDescription(plant.getDescription());
		
		return plantResource;
	}

}
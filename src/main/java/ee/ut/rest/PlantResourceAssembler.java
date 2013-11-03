package ee.ut.rest;

import java.util.ArrayList;
import java.util.List;

import ee.ut.model.Plant;

public class PlantResourceAssembler {

	public static PlantResource create(Plant plant) {
		PlantResource plantResource = new PlantResource();
		plantResource.setName(plant.getName());
		plantResource.setCostPerDay(plant.getCostPerDay());
		plantResource.setDescription(plant.getDescription());

		return plantResource;
	}

	public PlantResourceList create(List<Plant> plants) {
		PlantResourceList resources = new PlantResourceList();
		ArrayList<PlantResource> plant_resources = new ArrayList<PlantResource>();
		for (Plant p : plants) {
			plant_resources.add(create(p));
		}
		resources.setPlantResources(plant_resources);
		return resources;

	}
}
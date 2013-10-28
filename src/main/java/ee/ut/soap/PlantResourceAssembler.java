package ee.ut.soap;

import java.util.ArrayList;
import java.util.List;

import ee.ut.model.Plant;

public class PlantResourceAssembler {

	public static PlantResource create(Plant plant) {
		PlantResource plantResource = new PlantResource();
		plantResource.setId(plant.getId());
		plantResource.setName(plant.getName());
		plantResource.setCostPerDay(plant.getCostPerDay());
		plantResource.setDescription(plant.getDescription());
		return plantResource;
	}

	public PlantResourceList create(List<Plant> plants) {
		PlantResourceList resources = new PlantResourceList();
		ArrayList<PlantResource> plantResources = new ArrayList<PlantResource>();
		for (Plant p : plants) {
			plantResources.add(create(p));
		}
		resources.setPlants(plantResources);
		return resources;
	}
}

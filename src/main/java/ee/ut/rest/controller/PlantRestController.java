package ee.ut.rest.controller;

import java.net.URI;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import ee.ut.model.Plant;
import ee.ut.rest.PlantResource;
import ee.ut.rest.PlantResourceAssembler;

@Controller
@RequestMapping("/rest/plant")
public class PlantRestController {
	
	@RequestMapping
	(method = RequestMethod.GET, value ="/{id}")
	public ResponseEntity<PlantResource> getPlantPrice(@PathVariable Long id) {
		Plant plant = Plant.findPlant(id);
		if (plant != null) {
			PlantResource plantRequest = PlantResourceAssembler.create(plant);
			return new ResponseEntity<PlantResource>(plantRequest, HttpStatus.OK);
			
		}
		return new ResponseEntity<PlantResource>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping
	(method = RequestMethod.POST, value ="")
	public ResponseEntity<Void> createPlantRequest(@RequestBody PlantResource phr) {
		Plant plant = new Plant();
		plant.setName(phr.getName());
		plant.setCostPerDay(phr.getCostPerDay());
		plant.setDescription(phr.getDescription());
		
		plant.persist();
		 
		
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().
				 pathSegment(plant.getId().toString()).build().toUri();
		headers.setLocation(location);
		ResponseEntity<Void> response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		return response;
	}

}
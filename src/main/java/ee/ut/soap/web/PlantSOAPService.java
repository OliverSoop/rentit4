package ee.ut.soap.web;

import java.util.Date;
import java.util.List;

import javassist.NotFoundException;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ee.ut.domain.POstatus;
import ee.ut.model.Plant;
import ee.ut.model.PurchaseOrder;
import ee.ut.repository.PlantRepository;
import ee.ut.soap.PlantResource;
import ee.ut.soap.PlantResourceAssembler;
import ee.ut.soap.PlantResourceList;
import ee.ut.soap.PurchaseOrderResource;
import ee.ut.soap.PurchaseOrderResourceAssembler;

@WebService
public class PlantSOAPService extends SpringBeanAutowiringSupport {
	
	@Autowired
	PlantRepository plantRepo;  
	
    @Resource
    WebServiceContext wsContext;

    
	@WebMethod
	public PlantResourceList getAllPlants() {
		PlantResourceAssembler assembler = new PlantResourceAssembler();
		return assembler.create(Plant.findAllPlants());
	}
	
	@WebMethod
	public PlantResourceList getAvailablePlants(@WebParam(targetNamespace = "http://web.soap.ut.ee/", name="startDate") Date startDate, 
												@WebParam(targetNamespace = "http://web.soap.ut.ee/", name="endDate") Date endDate) {
		List<Plant> plants = plantRepo.findAvailablePlants(startDate, endDate);//Currently returns all the plants, if a solution how to inject repository is found will change
		if (plants != null) {
			PlantResourceAssembler assembler = new PlantResourceAssembler();
			return assembler.create(plants);
		}
		return new PlantResourceList();
	}
	
	@WebMethod
	public PurchaseOrderResource createPurchaseOrder(@WebParam(targetNamespace = "http://web.soap.ut.ee/", name = "purchaseOrder") PurchaseOrderResource purchaseOrder) throws NotFoundException {
		Plant plant = Plant.findPlant(purchaseOrder.getPlantId());
		if (plant != null) {
			PurchaseOrder po = new PurchaseOrder();
			po.setConstructionSite(purchaseOrder.getConstructionSite());
			po.setStartDate(purchaseOrder.getStartDate());
			po.setEndDate(purchaseOrder.getEndDate());
			po.setPlantID(plant);
			po.setPORecievedDate(new Date());
			po.setSiteEngineer(purchaseOrder.getSiteEngineer());
			po.setStatus(POstatus.RECIEVED);
			po.setTotalCost(daysBetween(purchaseOrder.getStartDate(), purchaseOrder.getEndDate()) * plant.getCostPerDay());
			po.persist();
			
			PurchaseOrderResourceAssembler assembler = new PurchaseOrderResourceAssembler();
			return assembler.create(po);
		} else {
			throw new NotFoundException("Plant not found");
		}
	}
	
	@WebMethod
	public PlantResource createPlant(@WebParam(targetNamespace = "http://web.soap.ut.ee/", name = "plant") PlantResource plantResource) {
		Plant plant = new Plant();
		plant.setName(plantResource.getName());
		plant.setCostPerDay(plantResource.getCostPerDay());
		plant.setDescription(plantResource.getDescription());
		plant.persist();
		return PlantResourceAssembler.create(plant);
	}
	
	private int daysBetween(Date d1, Date d2) {
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}
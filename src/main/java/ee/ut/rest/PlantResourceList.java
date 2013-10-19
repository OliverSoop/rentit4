package ee.ut.rest;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "plants")
public class PlantResourceList {

	private List<PlantResource> PlantResources;
	
	public PlantResourceList() {
		
		this.PlantResources = new ArrayList<PlantResource>();
	}
	
	public PlantResourceList(ArrayList<PlantResource> plants) {
		
		this.PlantResources = plants;
	}

}


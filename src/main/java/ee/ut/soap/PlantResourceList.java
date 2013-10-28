package ee.ut.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.roo.addon.javabean.RooJavaBean;


@RooJavaBean
@XmlRootElement(name="plants")
public class PlantResourceList {
	List<PlantResource> plants = new ArrayList<PlantResource>();
	
	public PlantResourceList() {
		
		this.plants = new ArrayList<PlantResource>();
	}
	
	public PlantResourceList(ArrayList<PlantResource> plants) {
		
		this.plants = plants;
	}

}

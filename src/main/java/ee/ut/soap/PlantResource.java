package ee.ut.soap;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@XmlRootElement(name="plant")
@RooJavaBean
public class PlantResource {
	private Long id;
	private String name;
	private double costPerDay;
	private String description;
}

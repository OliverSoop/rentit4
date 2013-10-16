package ee.ut.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement(name = "plant")
public class PlantResource {

	private String name;
	private double CostPerDay;
	private String Description;
}

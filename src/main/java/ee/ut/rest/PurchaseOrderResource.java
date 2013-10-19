package ee.ut.rest;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.roo.addon.javabean.RooJavaBean;
import ee.ut.domain.POstatus;

@RooJavaBean
@XmlRootElement(name = "purchaseOrder")

public class PurchaseOrderResource {
	
	private String ExternalID;
	private Long plantID;
	private Date startDate;
    private Date endDate;
	private String ConstructionSite;
	private String SiteEngineer;
	private Float totalCost;
    private Date POrecievedDate;
    private POstatus status;
    private Date returnDate;
      
}

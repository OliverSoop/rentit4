package ee.ut.soap;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.domain.POstatus;


@RooJavaBean
@XmlRootElement(name = "purchaseOrder")
public class PurchaseOrderResource {

	private String externalId;
	private Long plantId;
	private Date startDate;
	private Date endDate;
	private String constructionSite;
	private String siteEngineer;
	private double totalCost;
	private Date poRecievedDate;
	private POstatus status;
	private Date returnDate;
	private String email;
}

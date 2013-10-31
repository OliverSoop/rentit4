package ee.ut.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.ut.domain.POstatus;
import ee.ut.util.DateAdapter;
import ee.ut.util.ResourceSupport;

@XmlRootElement(name = "purchaseOrder")

public class PurchaseOrderResource extends ResourceSupport {
	
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
      

	public String getExternalID() {
        return this.ExternalID;
    }

	public void setExternalID(String ExternalID) {
        this.ExternalID = ExternalID;
    }

	public Long getPlantID() {
        return this.plantID;
    }

	public void setPlantID(Long plantID) {
        this.plantID = plantID;
    }

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartDate() {
        return this.startDate;
    }

	public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getEndDate() {
        return this.endDate;
    }

	public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	public String getConstructionSite() {
        return this.ConstructionSite;
    }

	public void setConstructionSite(String ConstructionSite) {
        this.ConstructionSite = ConstructionSite;
    }

	public String getSiteEngineer() {
        return this.SiteEngineer;
    }

	public void setSiteEngineer(String SiteEngineer) {
        this.SiteEngineer = SiteEngineer;
    }

	public Float getTotalCost() {
        return this.totalCost;
    }

	public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

	public Date getPOrecievedDate() {
        return this.POrecievedDate;
    }

	public void setPOrecievedDate(Date POrecievedDate) {
        this.POrecievedDate = POrecievedDate;
    }

	public POstatus getStatus() {
        return this.status;
    }

	public void setStatus(POstatus status) {
        this.status = status;
    }

	public Date getReturnDate() {
        return this.returnDate;
    }

	public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

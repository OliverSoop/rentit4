// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.rest;

import ee.ut.domain.POstatus;
import ee.ut.rest.PurchaseOrderResource;
import java.util.Date;

privileged aspect PurchaseOrderResource_Roo_JavaBean {
    
    public String PurchaseOrderResource.getExternalID() {
        return this.ExternalID;
    }
    
    public void PurchaseOrderResource.setExternalID(String ExternalID) {
        this.ExternalID = ExternalID;
    }
    
    public Long PurchaseOrderResource.getPlantID() {
        return this.plantID;
    }
    
    public void PurchaseOrderResource.setPlantID(Long plantID) {
        this.plantID = plantID;
    }
    
    public Date PurchaseOrderResource.getStartDate() {
        return this.startDate;
    }
    
    public void PurchaseOrderResource.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date PurchaseOrderResource.getEndDate() {
        return this.endDate;
    }
    
    public void PurchaseOrderResource.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String PurchaseOrderResource.getConstructionSite() {
        return this.ConstructionSite;
    }
    
    public void PurchaseOrderResource.setConstructionSite(String ConstructionSite) {
        this.ConstructionSite = ConstructionSite;
    }
    
    public String PurchaseOrderResource.getSiteEngineer() {
        return this.SiteEngineer;
    }
    
    public void PurchaseOrderResource.setSiteEngineer(String SiteEngineer) {
        this.SiteEngineer = SiteEngineer;
    }
    
    public Float PurchaseOrderResource.getTotalCost() {
        return this.totalCost;
    }
    
    public void PurchaseOrderResource.setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }
    
    public Date PurchaseOrderResource.getPOrecievedDate() {
        return this.POrecievedDate;
    }
    
    public void PurchaseOrderResource.setPOrecievedDate(Date POrecievedDate) {
        this.POrecievedDate = POrecievedDate;
    }
    
    public POstatus PurchaseOrderResource.getStatus() {
        return this.status;
    }
    
    public void PurchaseOrderResource.setStatus(POstatus status) {
        this.status = status;
    }
    
    public Date PurchaseOrderResource.getReturnDate() {
        return this.returnDate;
    }
    
    public void PurchaseOrderResource.setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.soap;

import ee.ut.domain.POstatus;
import ee.ut.soap.PurchaseOrderResource;
import java.util.Date;

privileged aspect PurchaseOrderResource_Roo_JavaBean {
    
    public String PurchaseOrderResource.getExternalId() {
        return this.externalId;
    }
    
    public void PurchaseOrderResource.setExternalId(String externalId) {
        this.externalId = externalId;
    }
    
    public Long PurchaseOrderResource.getPlantId() {
        return this.plantId;
    }
    
    public void PurchaseOrderResource.setPlantId(Long plantId) {
        this.plantId = plantId;
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
        return this.constructionSite;
    }
    
    public void PurchaseOrderResource.setConstructionSite(String constructionSite) {
        this.constructionSite = constructionSite;
    }
    
    public String PurchaseOrderResource.getSiteEngineer() {
        return this.siteEngineer;
    }
    
    public void PurchaseOrderResource.setSiteEngineer(String siteEngineer) {
        this.siteEngineer = siteEngineer;
    }
    
    public double PurchaseOrderResource.getTotalCost() {
        return this.totalCost;
    }
    
    public void PurchaseOrderResource.setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public Date PurchaseOrderResource.getPoRecievedDate() {
        return this.poRecievedDate;
    }
    
    public void PurchaseOrderResource.setPoRecievedDate(Date poRecievedDate) {
        this.poRecievedDate = poRecievedDate;
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
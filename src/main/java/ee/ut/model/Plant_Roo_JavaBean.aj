// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.model;

import ee.ut.model.Plant;

privileged aspect Plant_Roo_JavaBean {
    
    public String Plant.getName() {
        return this.Name;
    }
    
    public void Plant.setName(String Name) {
        this.Name = Name;
    }
    
    public double Plant.getCostPerDay() {
        return this.CostPerDay;
    }
    
    public void Plant.setCostPerDay(double CostPerDay) {
        this.CostPerDay = CostPerDay;
    }
    
    public String Plant.getDescription() {
        return this.Description;
    }
    
    public void Plant.setDescription(String Description) {
        this.Description = Description;
    }
    
}

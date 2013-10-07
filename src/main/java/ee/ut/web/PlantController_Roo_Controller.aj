// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.web;

import ee.ut.model.Plant;
import ee.ut.web.PlantController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PlantController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PlantController.create(@Valid Plant plant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plant);
            return "plants/create";
        }
        uiModel.asMap().clear();
        plant.persist();
        return "redirect:/plants/" + encodeUrlPathSegment(plant.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PlantController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Plant());
        return "plants/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String PlantController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("plant", Plant.findPlant(id));
        uiModel.addAttribute("itemId", id);
        return "plants/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PlantController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("plants", Plant.findPlantEntries(firstResult, sizeNo));
            float nrOfPages = (float) Plant.countPlants() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("plants", Plant.findAllPlants());
        }
        return "plants/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PlantController.update(@Valid Plant plant, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, plant);
            return "plants/update";
        }
        uiModel.asMap().clear();
        plant.merge();
        return "redirect:/plants/" + encodeUrlPathSegment(plant.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String PlantController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Plant.findPlant(id));
        return "plants/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String PlantController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Plant plant = Plant.findPlant(id);
        plant.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/plants";
    }
    
    void PlantController.populateEditForm(Model uiModel, Plant plant) {
        uiModel.addAttribute("plant", plant);
    }
    
    String PlantController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}

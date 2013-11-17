// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ee.ut.security;

import ee.ut.security.Assignments;
import ee.ut.security.AssignmentsController;
import ee.ut.security.Authorities;
import ee.ut.security.Users;
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

privileged aspect AssignmentsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String AssignmentsController.create(@Valid Assignments assignments, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, assignments);
            return "security/assignments/create";
        }
        uiModel.asMap().clear();
        assignments.persist();
        return "redirect:/security/assignments/" + encodeUrlPathSegment(assignments.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String AssignmentsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Assignments());
        return "security/assignments/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String AssignmentsController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("assignments", Assignments.findAssignments(id));
        uiModel.addAttribute("itemId", id);
        return "security/assignments/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String AssignmentsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("assignmentses", Assignments.findAssignmentsEntries(firstResult, sizeNo));
            float nrOfPages = (float) Assignments.countAssignmentses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("assignmentses", Assignments.findAllAssignmentses());
        }
        return "security/assignments/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String AssignmentsController.update(@Valid Assignments assignments, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, assignments);
            return "security/assignments/update";
        }
        uiModel.asMap().clear();
        assignments.merge();
        return "redirect:/security/assignments/" + encodeUrlPathSegment(assignments.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String AssignmentsController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Assignments.findAssignments(id));
        return "security/assignments/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String AssignmentsController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Assignments assignments = Assignments.findAssignments(id);
        assignments.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/security/assignments";
    }
    
    void AssignmentsController.populateEditForm(Model uiModel, Assignments assignments) {
        uiModel.addAttribute("assignments", assignments);
        uiModel.addAttribute("authoritieses", Authorities.findAllAuthoritieses());
        uiModel.addAttribute("userses", Users.findAllUserses());
    }
    
    String AssignmentsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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

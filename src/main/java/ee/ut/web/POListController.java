package ee.ut.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import ee.ut.beans.PurchaseOrderBean;
import ee.ut.domain.POstatus;
import ee.ut.model.PurchaseOrder;
import ee.ut.repository.PurchaseOrderRepository;

@RequestMapping("/purchaseorders/approvelist/**")
@Controller
public class POListController {
	
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    
    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "approval", params="accept")
    public String approve(@ModelAttribute("purchaseOrder") PurchaseOrderBean purchaseOrder, HttpServletRequest request, HttpServletResponse response) {
    	PurchaseOrder po = PurchaseOrder.findPurchaseOrder(purchaseOrder.getSelectedId());
    	po.setStatus(POstatus.OPEN);
    	po.persist();
    	sendApprovalDecision(POstatus.OPEN, po);
    	return "redirect:purchaseorders/approvelist/list";
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "approval", params="reject")
    public String reject(@ModelAttribute("purchaseOrder") PurchaseOrderBean purchaseOrder, HttpServletRequest request, HttpServletResponse response) {
    	PurchaseOrder po = PurchaseOrder.findPurchaseOrder(purchaseOrder.getSelectedId());
    	po.setStatus(POstatus.REJECTED);
    	po.persist();
    	sendApprovalDecision(POstatus.REJECTED, po);
    	return "redirect:purchaseorders/approvelist/list";
    }
    
    private void sendApprovalDecision(POstatus poStatus, PurchaseOrder po) {
    	String DOMAIN_URL = "http://buildit4.herokuapp.com/";
    	Client client = Client.create();
    	if (poStatus == POstatus.REJECTED) {
    		WebResource webResource = client.resource(DOMAIN_URL
    				+ "rest/po/" + po.getExternalID() + "/reject");
    		webResource.type(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.post(ClientResponse.class);
    	} else if (poStatus == POstatus.OPEN) {
    		WebResource webResource = client.resource(DOMAIN_URL
    				+ "rest/po/" + po.getExternalID() + "/accept");
    		webResource.type(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class);
    	}
    	
    	
    }
    
    
    @RequestMapping(produces = "text/html", value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("purchaseorders", purchaseOrderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) purchaseOrderRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("purchaseorders", purchaseOrderRepository.findPOsPendingConfirmation());
            uiModel.addAttribute("purchaseOrder", new PurchaseOrderBean());
        }
        addDateTimeFormatPatterns(uiModel);
        return "purchaseorders/approvelist/list";
    }
    
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("purchaseOrder_startdate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("purchaseOrder_enddate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("purchaseOrder_porecieveddate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("purchaseOrder_returndate_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
}

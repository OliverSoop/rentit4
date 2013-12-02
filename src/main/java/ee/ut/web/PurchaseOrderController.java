package ee.ut.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.domain.POstatus;
import ee.ut.model.PurchaseOrder;

@RequestMapping("/purchaseorders")
@Controller
@RooWebScaffold(path = "purchaseorders", formBackingObject = PurchaseOrder.class)
public class PurchaseOrderController {


	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, purchaseOrder);
            return "purchaseorders/update";
        }
        uiModel.asMap().clear();
        purchaseOrderRepository.save(purchaseOrder);
        
        if(purchaseOrder.getStatus() == POstatus.RETURNED){
        	process(purchaseOrder);
        }
        
        return "redirect:/purchaseorders/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }
	
	@Autowired
    private JavaMailSender mailSender;
	
	@ServiceActivator
	public void process(PurchaseOrder purchaseOrder) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(purchaseOrder.getEmail());
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject("Here is an invoice for returned plant");
		mailMessage.setText("You may start paying. Assosiated PO: " + purchaseOrder.getExternalID());
		mailSender.send(mailMessage);
	}
}

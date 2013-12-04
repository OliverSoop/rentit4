package ee.ut.web;

import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.domain.InvoiceStatus;
import ee.ut.domain.POstatus;
import ee.ut.model.Invoice;
import ee.ut.model.PurchaseOrder;
import ee.ut.util.WriteXMLFile;

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
        	Invoice invoiceNew = new Invoice();
    		invoiceNew.setPurchaseOrderHRef(purchaseOrder.getId().toString());
    		invoiceNew.setReturnEmail(purchaseOrder.getEmail());
    		invoiceNew.setStatus(InvoiceStatus.UNPAID);
    		invoiceNew.setTotal((float) purchaseOrder.getTotalCost());
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.DAY_OF_MONTH, 7);
    		invoiceNew.setDeadline(cal.getTime());
    		invoiceNew.persist();
    		
    		WriteXMLFile.write(purchaseOrder.getExternalId(), ""+((int)purchaseOrder.getTotalCost()), "rentit4app@gmail.com", ""+invoiceNew.getId(), invoiceNew.getDeadline());
        	
        	
        	sendMailInvoice(purchaseOrder.getEmail());
        	
        }
        
        return "redirect:/purchaseorders/" + encodeUrlPathSegment(purchaseOrder.getId().toString(), httpServletRequest);
    }
	
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
 
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
 
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String to) {
	   MimeMessage message = mailSender.createMimeMessage();
 
	   try{
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
 
		helper.setFrom("rentit4@gmail.com");
		helper.setTo(to);
		helper.setSubject("invoice");
		helper.setText("invoice");
 
		FileSystemResource file = new FileSystemResource("invoice.xml");
		helper.addAttachment(file.getFilename(), file, "text/xml");
 
	     }catch (MessagingException e) {
		throw new MailParseException(e);
	     }
	     mailSender.send(message);
         }
	
	public void sendMailInvoice(String to){
		ApplicationContext context = 
	             new ClassPathXmlApplicationContext("META-INF/spring/Spring-Mail.xml");
	 
			PurchaseOrderController mm = (PurchaseOrderController) context.getBean("mailMail");
	        mm.sendMail(to);
	 
	    
	}
}

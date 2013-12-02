package ee.ut.web;
import java.util.Date;

import ee.ut.model.Invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/invoices")
@Controller
@RooWebScaffold(path = "invoices", formBackingObject = Invoice.class)
public class InvoiceController {
	
}

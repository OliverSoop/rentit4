package ee.ut.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

import ee.ut.domain.InvoiceStatus;

@RooJavaBean
@XmlRootElement(name = "invoice")
public class InvoiceResource {
	private Float total;
	private String purchaseOrderHRef;
	private String returnEmail;
	private InvoiceStatus status;
}

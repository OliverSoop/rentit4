package ee.ut.model;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import ee.ut.domain.InvoiceStatus;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(inheritanceType = "SINGLE_TABLE")
public class Invoice {

	private Float total;
	private String purchaseOrderHRef;
	private String returnEmail;
	private InvoiceStatus status;
}
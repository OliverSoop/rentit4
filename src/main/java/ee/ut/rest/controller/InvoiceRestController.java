package ee.ut.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.ut.domain.InvoiceStatus;
import ee.ut.model.Invoice;

@Controller
@RequestMapping("/rest/invoice")
public class InvoiceRestController {
	
	@RequestMapping(method = RequestMethod.POST, value = "/remitance/{id}")
	public ResponseEntity<Void> updateInvoice(@PathVariable("id") Long id) {
		Invoice invoice = Invoice.findInvoice(id);
		ResponseEntity<Void> response;
		if (invoice.getStatus().equals(InvoiceStatus.UNPAID)) {
			invoice.setStatus(InvoiceStatus.PAID);
			invoice.persist();
			response = new ResponseEntity<>(HttpStatus.OK);
		} else
			response = new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		return response;
	}
}

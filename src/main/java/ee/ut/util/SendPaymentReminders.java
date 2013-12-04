package ee.ut.util;

import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import ee.ut.model.Invoice;
import ee.ut.model.PurchaseOrder;
import ee.ut.repository.InvoiceRepository;

public class SendPaymentReminders extends TimerTask {

	private JavaMailSender mailSender;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Override
	public void run() {
		for (Invoice invoice : invoiceRepository.getUnpaidInvoices()) {
			sendMail(invoice);
		}
		
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(Invoice invoice) {
		PurchaseOrder po = PurchaseOrder.findPurchaseOrder(Long.parseLong(invoice.getPurchaseOrderHRef()));
		if (po == null || invoice.getTotal() < 10.0) {
			return;
		}
		MimeMessage message = mailSender.createMimeMessage();

		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("rentit4@gmail.com");
			helper.setTo(po.getEmail());
			helper.setSubject("Deadline for invoice " + invoice.getId() + " has passed");
			helper.setText("Dear Sir, \n\n"
						 + "we would like to inform you that the invoice with id " + invoice.getId() + "\n"
						 + "is overdue. The remainining amount is " + invoice.getTotal() + ".\n"
						 + "The deadline was " + invoice.getDeadline() + "\n\n\n\n"
						 + "Best regards,\n"
						 + "RentIt");

		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
		mailSender.send(message);
	}

}

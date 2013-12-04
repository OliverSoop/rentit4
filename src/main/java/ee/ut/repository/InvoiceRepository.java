package ee.ut.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ee.ut.model.Invoice;

@RooJpaRepository(domainType = Invoice.class)
public interface InvoiceRepository {
	
	@Query("SELECT i FROM Invoice AS i WHERE i.status = 'UNPAID'")
	@Transactional(readOnly=true)
	List<Invoice> getUnpaidInvoices();
}

package ee.ut.model;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import ee.ut.domain.POstatus;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PurchaseOrder {

    /**
     */
    private String externalId;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    /**
     */
    private String constructionSite;

    /**
     */
    private String SiteEngineer;

    /**
     */
    private double totalCost;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date PORecievedDate;

    /**
     */
    @Enumerated(EnumType.STRING)
    private POstatus Status;

    /**
     */
    @ManyToOne
    private Plant PlantID;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date ReturnDate;

	public String getExternalID() {
        return this.externalId;
    }
}

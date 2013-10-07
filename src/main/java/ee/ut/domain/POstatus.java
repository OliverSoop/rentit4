package ee.ut.domain;
import javax.persistence.Enumerated;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

public enum POstatus {

    RECIEVED, ACCEPTED, REJECTED;

    /**
     */
    @Enumerated
    private POstatus Status;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date ReturnDate;
}

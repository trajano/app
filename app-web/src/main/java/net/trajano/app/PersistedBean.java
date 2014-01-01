package net.trajano.app;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Sample persisted bean.
 */
@Entity
public class PersistedBean {
    /**
     * ID.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Message.
     */
    private String message;

    /**
     * Date.
     */
    @Temporal(TemporalType.DATE)
    private Date someDate;

    /**
     * Time.
     */
    @Temporal(TemporalType.TIME)
    private Date someTime;

    /**
     * Timestamp.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date someTimestamp;

    /**
     * This will return the value of {@link #someDate}. The {@link XmlElement}
     * annotation enables it to appear on the returned JSON string.
     * 
     * @return the date
     */
    @XmlElement
    public Date getDate() {
        return new Date(someDate.getTime());
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getSomeDate() {
        return someDate;
    }

    public Date getSomeTime() {
        return someTime;
    }

    public Date getSomeTimestamp() {
        return someTimestamp;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSomeDate(final Date someDate) {
        this.someDate = new Date(someDate.getTime());
    }

    public void setSomeTime(final Date someTime) {
        this.someTime = new Date(someTime.getTime());
    }

    public void setSomeTimestamp(final Date someTimestamp) {
        this.someTimestamp = someTimestamp;
    }
}

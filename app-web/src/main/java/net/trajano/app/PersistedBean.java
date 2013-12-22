package net.trajano.app;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
     * Date.
     */
    @Temporal(TemporalType.TIME)
    private Date someTime;

    /**
     * Date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date someTimestamp;

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
        this.someDate = someDate;
    }

    public void setSomeTime(final Date someTime) {
        this.someTime = someTime;
    }

    public void setSomeTimestamp(final Date someTimestamp) {
        this.someTimestamp = someTimestamp;
    }
}

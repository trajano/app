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
     * Date.
     */
    @Temporal(TemporalType.DATE)
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

package net.trajano.app;

import java.util.Date;

/**
 * Sample bean.
 */
public class Bean {
    /**
     * Date.
     */
    private Date date;

    /**
     * Message.
     */
    private String message;

    public Date getDate() {
        return new Date(date.getTime());
    }

    public String getMessage() {
        return message;
    }

    public void setDate(final Date date) {
        this.date = new Date(date.getTime());
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

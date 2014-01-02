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

    /**
     * Date. Does a copy.
     * 
     * @return date.
     */
    public Date getDate() {
        return new Date(date.getTime());
    }

    /**
     * The message.
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the date. Makes a copy of the input.
     * 
     * @param date
     *            date.
     */
    public void setDate(final Date date) {
        this.date = new Date(date.getTime());
    }

    /**
     * Sets the message.
     * 
     * @param message
     *            message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}

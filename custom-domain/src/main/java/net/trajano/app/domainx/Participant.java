package net.trajano.app.domainx;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
// TODO namedquery getByDate
public class Participant {
	/**
	 * Date.
	 */
	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

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
	 * Lookup key.
	 */
	private UUID uuid;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setEffectiveDate(final Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setUuid(final UUID uuid) {
		this.uuid = uuid;
	}

}

package net.trajano.app.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
// TODO namedquery getByDate
public class TemporalRecord {
	/**
	 * ID.
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * Lookup key.
	 */
	private UUID uuid;

	/**
	 * Message.
	 */
	private String message;

	/**
	 * Date.
	 */
	@Temporal(TemporalType.DATE)
	private Date effectiveDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}

package net.trajano.app.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

// a secondary entity called superceded temporal records stores temporal records that have been superceded.  Only active ones are here.
@Entity
// TODO namedquery getByDate
@NamedQueries({
		@NamedQuery(name = "TemporalRecord.getByUuidAndDate", query = "select r from TemporalRecord r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = (select max(t.effectiveDate) from TemporalRecord t where t.uuidLow = :uuidLow and t.uuidHigh = :uuidHigh and t.effectiveDate <= :date)"),
		@NamedQuery(name = "TemporalRecord.getByDateAndEffectiveDate", query = "select r from TemporalRecord r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = :effectiveDate") })
@Table(uniqueConstraints = { @UniqueConstraint(name = "uuid", columnNames = "uuidLow, uuidHigh, effectivedate") })
public class TemporalRecord {
	/**
	 * ID.
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * Lookup key. Least significant bits of UUID.
	 */
	@Column(nullable = false)
	private long uuidLow;
	/**
	 * Lookup key. Most significant bits of UUID.
	 */
	@Column(nullable = false)
	private long uuidHigh;

	/**
	 * Message.
	 */
	private String message;

	/**
	 * Audit purposes, when the record was last written.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastWrittenOn;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdOn;

	/**
	 * Audit purposes, who wrote the last record.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastWrittenBy;

	/**
	 * Date. TODO this may not work because of timezone issues.
	 */
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date effectiveDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return new UUID(uuidHigh, uuidLow);
	}

	public void setUuid(UUID uuid) {
		this.uuidLow = uuid.getLeastSignificantBits();
		this.uuidHigh = uuid.getMostSignificantBits();
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

	@PrePersist
	@PreUpdate
	protected void updateAuditFields() {
		createdOn = (createdOn == null ? new Date() : createdOn);
		lastWrittenOn = new Date();
		System.out.println("UPDATEING AUDIT TIMESTEMP TO " + lastWrittenOn);
	}

}

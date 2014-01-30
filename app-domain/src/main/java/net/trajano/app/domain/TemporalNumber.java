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

import org.apache.commons.lang3.builder.ToStringBuilder;

// a secondary entity called superceded temporal records stores temporal records that have been superseded.  Only active ones are here.
/**
 * Active temporal string data used to store integer and boolean values.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "TemporalNumber.getByUuid", query = "select r from TemporalNumber r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh "),
		@NamedQuery(name = "TemporalNumber.getByUuidAndDate", query = "select r from TemporalNumber r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = (select max(t.effectiveDate) from TemporalNumber t where t.uuidLow = :uuidLow and t.uuidHigh = :uuidHigh and t.effectiveDate <= :date)"),
		@NamedQuery(name = "TemporalNumber.getByUuidAndEffectiveDate", query = "select r from TemporalNumber r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = :effectiveDate") })
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "uuidLow, uuidHigh, effectivedate") })
public class TemporalNumber {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdOn;

	/**
	 * Date. TODO this may not work because of timezone issues.
	 */
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date effectiveDate;
	/**
	 * ID.
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * Audit purposes, who wrote the last record.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastWrittenBy;

	/**
	 * Audit purposes, when the record was last written. TODO push up.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastWrittenOn;
	/**
	 * Lookup key. Most significant bits of UUID.
	 */
	@Column(nullable = false)
	private long uuidHigh;

	/**
	 * Lookup key. Least significant bits of UUID.
	 */
	@Column(nullable = false)
	private long uuidLow;

	/**
	 * Value. The length may be overriden in persistence.xml and orm.xml. For
	 * theoretical efficiency reasons, a conservative value of 512 was chosen
	 * for the length.
	 */
	@Column(nullable = false)
	private long value;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public long getId() {
		return id;
	}

	public UUID getUuid() {
		return new UUID(uuidHigh, uuidLow);
	}

	public long getValue() {
		return value;
	}

	public boolean getValueAsBoolean() {
		return value != 0;
	}

	public void setValue(boolean b) {
		value = b ? 1 : 0;
	}

	public void setEffectiveDate(final Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setUuid(final UUID uuid) {
		uuidLow = uuid.getLeastSignificantBits();
		uuidHigh = uuid.getMostSignificantBits();
	}

	// TODO URI
	// TODO UUID
	public void setValue(final long value) {
		this.value = value;
	}

	@PrePersist
	@PreUpdate
	protected void updateAuditFields() {
		createdOn = createdOn == null ? new Date() : createdOn;
		lastWrittenOn = new Date();
		System.out.println("UPDATEING AUDIT TIMESTAMP TO " + lastWrittenOn);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

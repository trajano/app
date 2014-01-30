package net.trajano.app.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
 * Base class for temporal records.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({
		@NamedQuery(name = "TemporalRecord.getByUuid", query = "select r from TemporalRecord r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh "),
		@NamedQuery(name = "TemporalRecord.getByUuidAndDate", query = "select r from TemporalRecord r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = (select max(t.effectiveDate) from TemporalRecord t where t.uuidLow = :uuidLow and t.uuidHigh = :uuidHigh and t.effectiveDate <= :date)"),
		@NamedQuery(name = "TemporalRecord.getByUuidAndEffectiveDate", query = "select r from TemporalRecord r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = :effectiveDate") })
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "uuidLow, uuidHigh, effectivedate") })
public abstract class TemporalRecord {
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

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public long getId() {
		return id;
	}

	public UUID getUuid() {
		return new UUID(uuidHigh, uuidLow);
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

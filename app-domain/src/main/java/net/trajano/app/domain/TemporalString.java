package net.trajano.app.domain;

import java.math.BigDecimal;
import java.util.Calendar;
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
import javax.xml.bind.DatatypeConverter;

// a secondary entity called superceded temporal records stores temporal records that have been superseded.  Only active ones are here.
/**
 * Active temporal string data used to store limited string types, arbitrary
 * precision decimal numbers, date, time and dateTime values.
 * <p>
 * Temporal data such as date, time and dateTime are stored as a string to
 * prevent any ambiguity with regards to time zone or lack thereof.
 * </p>
 * <p>
 * Floating point numbers are stored as a string in order to ensure the
 * precision of the data is preserved.
 * </p>
 * 
 * The intent is to ensure the data can be transferred and managed to other
 * systems when needed.
 * 
 * The XML schema being used defines additional basic type extensions:
 * <ul>
 * <li><code>amount</code> which maps to be a big decimal to allow for arbitrary
 * precision</li>
 * <li><code>uuid</code> which maps to a string that follows the UUID convention
 * </li>
 * </ul>
 * 
 * The type is not stored here. The schema will know what type is needed to
 * present to the user.
 * 
 * TODO inheritance of temporal data so we can reuse the same query.
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "TemporalString.getByUuid", query = "select r from TemporalString r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh"),
		@NamedQuery(name = "TemporalString.getByUuidAndDate", query = "select r from TemporalString r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = (select max(t.effectiveDate) from TemporalString t where t.uuidLow = :uuidLow and t.uuidHigh = :uuidHigh and t.effectiveDate <= :date)"),
		@NamedQuery(name = "TemporalString.getByUuidAndEffectiveDate", query = "select r from TemporalString r where r.uuidLow = :uuidLow and r.uuidHigh = :uuidHigh and r.effectiveDate = :effectiveDate") })
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "uuidLow, uuidHigh, effectivedate") })
public class TemporalString {
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
	@Column(nullable = false, length = 512)
	private String value;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public long getId() {
		return id;
	}

	public UUID getUuid() {
		return new UUID(uuidHigh, uuidLow);
	}

	public String getValue() {
		return value;
	}

	public BigDecimal getValueAsBigDecimal() {
		return new BigDecimal(value);
	}

	public Date getValueAsDate() {
		return DatatypeConverter.parseDate(value).getTime();
	}

	public Date getValueAsDateTime() {
		return DatatypeConverter.parseDateTime(value).getTime();
	}

	public double getValueAsDouble() {
		return Double.parseDouble(value);
	}

	public Date getValueAsTime() {
		return DatatypeConverter.parseTime(value).getTime();
	}

	public void setDateTimeValue(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		value = DatatypeConverter.printDateTime(c);
	}

	public void setDateValue(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		value = DatatypeConverter.printDate(c);
	}

	public void setEffectiveDate(final Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setTimeValue(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		value = DatatypeConverter.printTime(c);
	}

	public void setUuid(final UUID uuid) {
		uuidLow = uuid.getLeastSignificantBits();
		uuidHigh = uuid.getMostSignificantBits();
	}

	// TODO URI
	// TODO UUID
	public void setValue(final String value) {
		this.value = value;
	}

	@PrePersist
	@PreUpdate
	protected void updateAuditFields() {
		createdOn = createdOn == null ? new Date() : createdOn;
		lastWrittenOn = new Date();
		System.out.println("UPDATEING AUDIT TIMESTAMP TO " + lastWrittenOn);
	}

}

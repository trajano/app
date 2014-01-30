package net.trajano.app.domain;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.DatatypeConverter;

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
 */
@Entity
public class TemporalString extends TemporalRecord {

	/**
	 * Value. The length may be overridden in persistence.xml and orm.xml. For
	 * theoretical efficiency reasons, a conservative value of 512 was chosen
	 * for the length.
	 */
	@Column(nullable = false, length = 512)
	private String value;

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

	public URI getValueAsUri() {
		return URI.create(value);
	}

	public UUID getValueAsUuid() {
		return UUID.fromString(value);
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

	public void setTimeValue(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		value = DatatypeConverter.printTime(c);
	}

	// TODO URI
	// TODO UUID
	public void setValue(final String value) {
		this.value = value;
	}

	public void setValue(final URI uri) {
		value = uri.toASCIIString();
	}

	public void setValue(final UUID uuid) {
		value = uuid.toString();
	}
}

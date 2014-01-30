package net.trajano.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

// a secondary entity called superceded temporal records stores temporal records that have been superseded.  Only active ones are here.
/**
 * Active temporal string data used to store integer and boolean values.
 */
@Entity
public class TemporalNumber extends TemporalRecord {

	/**
	 * Value. The length may be overriden in persistence.xml and orm.xml. For
	 * theoretical efficiency reasons, a conservative value of 512 was chosen
	 * for the length.
	 */
	@Column(nullable = false)
	private long value;

	public long getValue() {
		return value;
	}

	public boolean getValueAsBoolean() {
		return value != 0;
	}

	public void setValue(final boolean b) {
		value = b ? 1 : 0;
	}

	public void setValue(final long value) {
		this.value = value;
	}

}

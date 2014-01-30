package net.trajano.app.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * A simplified date class that can be persisted as a integer. This is meant for
 * storing business dates with no notion of timezone. The date is stored as
 * yyyyMMdd.
 * 
 * @author Archimedes
 * 
 */
public class LocalDate implements Comparable<LocalDate> {
	private static LocalDate overrideCurrentDate = null;

	/**
	 * Get current local date.
	 * 
	 * @return
	 */
	public static LocalDate current() {
		if (overrideCurrentDate == null) {
			return new LocalDate(Calendar.getInstance());
		} else {
			return overrideCurrentDate;
		}
	}

	public static void overrideCurrentDate(final LocalDate localDate) {
		overrideCurrentDate = localDate;
	}

	private final int date;

	public LocalDate(final Calendar c) {
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		date = year * 10000 + month * 100 + day;
	}

	public LocalDate(final Date javaDate) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(javaDate.getTime());
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		date = year * 10000 + month * 100 + day;
	}

	public LocalDate(final int year, final int month, final int day) {
		date = year * 10000 + month * 100 + day;
	}

	@Override
	public int compareTo(final LocalDate o) {
		return date - o.date;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LocalDate other = (LocalDate) obj;
		if (date != other.date) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return date;
	}

}

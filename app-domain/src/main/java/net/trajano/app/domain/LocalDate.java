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
	private final int date;

	public LocalDate(int year, int month, int day) {
		date = year * 10000 + month * 100 + day;
	}

	public LocalDate(Date javaDate) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(javaDate.getTime());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		date = year * 10000 + month * 100 + day;
	}

	public LocalDate(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		date = year * 10000 + month * 100 + day;
	}

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

	public static void overrideCurrentDate(LocalDate localDate) {
		overrideCurrentDate = localDate;
	}

	private static LocalDate overrideCurrentDate = null;

	@Override
	public int hashCode() {
		return date;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalDate other = (LocalDate) obj;
		if (date != other.date)
			return false;
		return true;
	}

	@Override
	public int compareTo(LocalDate o) {
		return date - o.date;
	}

}

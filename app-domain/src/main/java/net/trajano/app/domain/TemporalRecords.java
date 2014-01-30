package net.trajano.app.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * SLSB to manage persisted classes. This would be the table model.
 * 
 * TODO take in a column that represents a table name? NO, just use actual
 * database tables. That means somehow we have to make this generic so we can
 * add tables easily.
 * 
 * @author Archimedes Trajano
 */
@Stateless
public class TemporalRecords {
	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * We don't provide access to the actual record to clients, they only deal
	 * with the UUID? How do we handle other records? Perhaps we should consider
	 * embedding?
	 * 
	 * It is okay to have technical fields on JPA for our app, because the
	 * business fields are actually stored in the XML Clob anyway.
	 * 
	 * Do we store mapping data here or denormalize to separate tables? We need
	 * to denormalize an ID data from the XML lob anyway.
	 * 
	 * How do I separate personally identifiable data from the transactional
	 * data? I know I separate it into two XML lobs that's for sure.
	 * 
	 * Something like Personal info (with expiration data) and (non-personal
	 * information) there's a link between them somehow.
	 * 
	 * There is temporal data for a person, namely address and phone number that
	 * can change over time. Do I store that as part of the data of the person?
	 * If it changes then the entire rules against the person needs to be
	 * recalculated. The thing is the personal data is not used as part of the
	 * calculation (Date of birth is not personally identifiable data)
	 * 
	 * Personally identifiable data: Name and associated aliases Street Address
	 * and postal code. City, province is not PID Telephone numbers Email
	 * addresses.
	 * 
	 * Certifications are also personally identifiable if they are stored (as
	 * images). Actually any image related to the person is personally
	 * identifiable.
	 * 
	 * Every temporal data should have some form of certification with it? The
	 * certifications should be kept in a different table though. One
	 * certification can be used on multiple temporalrecords. and a single
	 * temporal record can also have many certifications.
	 * 
	 * There are data that does not change over time, tombstone data, that is
	 * not personally identifiable.
	 * <ul>
	 * <li>Date of birth
	 * <li>Date of death
	 * </ul>
	 * Technically the above can be temporal. alive indicator.
	 * 
	 * It does not make sense to store stuff in the different tables. Does it
	 * make sense to store it in different rows? If we want additional business
	 * tables then we need to change the schema? Not if we have another key
	 * field for the data itself.
	 * 
	 * So no more XML? How do we archive stuff then? Well we don't. The data we
	 * have is transactional and should be used for marketing metrics. The PI
	 * data must be archived or destroyed over time
	 * 
	 * Data can go to the person or the case. Actually there is no such thing as
	 * the above. Personally identifiable data is never used for calculations
	 * all subject related data is stored?
	 * 
	 * Or perhaps not, some things are case related and only valid for the
	 * duration of a specific case rather than long term. For example, the
	 * objective of a patient is to get out of a hospital. Once they are out
	 * they are no longer relevant to the hospital. The records are kept as part
	 * of the case. But hold on... they can still come back and those records
	 * are still relevant. So the patient case is not a good exmaple to dismiss
	 * things.
	 * 
	 * What about relationships between persons? Is that personally
	 * identifiable? As long as UUIDs are used to relate them, it should be
	 * fine.
	 * 
	 * How do we prevent exposure of a UUID? For every restart a new unique AES
	 * key is generated when sent to the user's machine. Do we need a UUID then,
	 * wouldn't a long be sufficient? Yes in fact a long would be sufficient,
	 * but UUIDs are wider (122-bits vs 64-bits). However, may result in slower
	 * lookups if we store it as a string.　　So we can store it as two longs.
	 * 
	 * Business Intelligence reports should not need to look up by UUID as that
	 * provides association with personal information.
	 * 
	 * Do we store temporal records one type per table? For storage efficiency.
	 * Perhaps, we can return a map of different data types?
	 * 
	 * Data migration? When there is an update to the model we have XSLT
	 * translations? The data we extract is serializable to XML with a well
	 * defined schema.
	 * 
	 * Types:
	 * <ul>
	 * <li>string (bounded so maxLength has to be specified and should be within
	 * the capacity of a database, may use attributes if it is too big it goes
	 * to clob)
	 * <li>number (long)
	 * <li>rate (double) stored as a string?
	 * <li>amount (bigdecimal, arbitrary precision) stored as a string
	 * <li>(would i have a business number that is a really long integer?
	 * probably not we can add it later if the need arises) boolean
	 * <li>byte[] Blob? (can by any binary data)
	 * <li>Clob? (can be arbitrary XML)
	 * <li>dates/times are stored as a string
	 * </ul>
	 * 
	 * Storing as a string ensures precision of data at the expense of
	 * performance. However, this is primarily for storage. Data can be mapped
	 * for more efficient searches anyway.
	 * 
	 * JPA does not have streams yet I think, so I would likely need to do it
	 * through JDBC directly. Perhaps I would add explicit tables for this one
	 * that are not part of the JPA?
	 * 
	 * Also we have to consider moving data to a graveyard store. The graveyard
	 * can be cleared off as needed.
	 * 
	 * There should be no concept of in-edit, but there can be concept of a user
	 * workspace where the user can modify their data before commiting it to the
	 * temporal data store.
	 * 
	 * @param uuid
	 * @param date
	 * @return
	 */
	public String getByUuidAndDate(final UUID uuid, final Date date) {
		final Date theDate = stripTime(date);
		final TypedQuery<TemporalString> existingRecordQuery = em
				.createNamedQuery("TemporalRecord.getByDateAndDate",
						TemporalString.class);
		existingRecordQuery.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		existingRecordQuery.setParameter("uuidHigh",
				uuid.getMostSignificantBits());
		existingRecordQuery.setParameter("date", theDate);
		return existingRecordQuery.getSingleResult().getValue();

	}

	/**
	 * Store a number into a field. Effectively this will be stored in its own
	 * table. There is a table for each basic type and one for a complex type.
	 * The table model will redirect accordingly.
	 * 
	 * I should provide some callback handlers that would be executed to do
	 * mapping of data to a tabular structure. This can probably by done through
	 * interceptors or just a preUpdate postUpdate prePersist, etc.?
	 * 
	 * @param uuid
	 *            uuid associated to the object this temporal is referring to.
	 * @param name
	 *            name of the field
	 * @param value
	 *            value to store
	 * @param effectiveDate
	 *            effective date
	 */
	public void put(final UUID uuid, final String name, final long value,
			final Date effectiveDate) {
		final Date theDate = stripTime(effectiveDate);
		final TypedQuery<TemporalNumber> existingRecordQuery = em
				.createNamedQuery("TemporalRecord.getByUuidAndEffectiveDate",
						TemporalNumber.class);
		existingRecordQuery.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		existingRecordQuery.setParameter("uuidHigh",
				uuid.getMostSignificantBits());
		existingRecordQuery.setParameter("effectiveDate", theDate);
		TemporalNumber bean;
		try {
			bean = existingRecordQuery.getSingleResult();
			// result was found, copy existing record data to graveyard.
		} catch (final NoResultException e) {
			System.out.println("NOT FOUND?" + uuid + " " + theDate);
			bean = new TemporalNumber();
			bean.setUuid(uuid);
			bean.setEffectiveDate(theDate);
		}
		bean.setValue(value);
		System.out.println("saving " + bean);
		em.persist(bean);
		em.flush();
		em.refresh(bean);
	}

	public void put(final UUID uuid, final String name, final String value,
			final Date effectiveDate) {
		final Date theDate = stripTime(effectiveDate);
		final TypedQuery<TemporalString> existingRecordQuery = em
				.createNamedQuery("TemporalRecord.getByUuidAndEffectiveDate",
						TemporalString.class);
		existingRecordQuery.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		existingRecordQuery.setParameter("uuidHigh",
				uuid.getMostSignificantBits());
		existingRecordQuery.setParameter("effectiveDate", theDate);
		TemporalString bean;
		try {
			bean = existingRecordQuery.getSingleResult();
			// result was found, copy existing record data to graveyard.
		} catch (final NoResultException e) {
			System.out.println("NOT FOUND?" + uuid + " " + theDate);
			bean = new TemporalString();
			bean.setUuid(uuid);
			bean.setEffectiveDate(theDate);
		}
		bean.setValue(value);
		System.out.println("saving " + bean);
		em.persist(bean);
		em.flush();
		em.refresh(bean);
	}

	/**
	 * Removes temporal data by UUID. All data in the temporal tables whose UUID
	 * matches will be deleted. The data will be moved to the graveyard tables.
	 * 
	 * @param uuid
	 *            UUID of temporal data to delete.
	 */
	public void remove(final UUID uuid) {
		final TypedQuery<TemporalString> strings = em
				.createNamedQuery("TemporalRecord.getByUuid",
						TemporalString.class);
		strings.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		strings.setParameter("uuidHigh",
				uuid.getMostSignificantBits());

		for (final TemporalString temporalString : strings
				.getResultList()) {
			// TODO copy record to graveyard
			em.remove(temporalString);
		}

		final TypedQuery<TemporalNumber> numbers = em
.createNamedQuery(
				"TemporalRecord.getByUuid",
						TemporalNumber.class);
		numbers.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		numbers.setParameter("uuidHigh",
				uuid.getMostSignificantBits());
		for (final TemporalNumber temporalNumber : numbers
				.getResultList()) {
			// TODO copy record to graveyard
			em.remove(temporalNumber);
		}
	}

	@Deprecated
	public void save(final String string) {
		save(string, new Date());
	}

	@Deprecated
	public void save(final String message, final Date effectiveDate) {
		final Date theDate = stripTime(effectiveDate);
		final UUID uuid = UUID
				.fromString("550e8400-e29b-41d4-a716-446655440000");
		final TypedQuery<TemporalString> existingRecordQuery = em
				.createNamedQuery("TemporalRecord.getByDateAndEffectiveDate",
						TemporalString.class);
		existingRecordQuery.setParameter("uuidLow",
				uuid.getLeastSignificantBits());
		existingRecordQuery.setParameter("uuidHigh",
				uuid.getMostSignificantBits());
		existingRecordQuery.setParameter("effectiveDate", theDate);
		TemporalString bean;
		try {
			bean = existingRecordQuery.getSingleResult();
		} catch (final NoResultException e) {
			bean = new TemporalString();
			bean.setUuid(uuid);
			bean.setEffectiveDate(theDate);
		}
		bean.setValue(message);
		// probably use this to determine if the string is enough.
		// em.getMetamodel().entity(String.class).getAttribute(name)
		em.persist(bean);
		em.flush();
		em.refresh(bean);
	}

	/**
	 * Injects the entity manager for testing.
	 * 
	 * @param entityManager
	 *            entity manager.
	 */
	public void setEntityManager(final EntityManager entityManager) {
		em = entityManager;
	}

	private Date stripTime(final Date effectiveDate) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(effectiveDate.getTime());
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c.getTime();
	}
}

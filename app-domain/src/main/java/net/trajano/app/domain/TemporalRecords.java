package net.trajano.app.domain;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SLSB to manage persisted classes. This would be the table model.
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
	 * Save, flush and refresh a persisted bean. The flush and refreshing is
	 * needed to ensure that the temporal data contains the proper values.
	 * 
	 * @param bean
	 *            bean to persist.
	 */
	public void save(final TemporalRecord bean) {
		em.persist(bean);
		em.flush();
		em.refresh(bean);
	}

	public void save(final String message, Date effectiveDate) {
		TemporalRecord bean = new TemporalRecord();
		bean.setMessage("Hello world");
		bean.setEffectiveDate(effectiveDate);
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

	public void save(String string) {

	}
}

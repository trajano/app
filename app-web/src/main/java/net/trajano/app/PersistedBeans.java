package net.trajano.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.trajano.app.domain.TemporalRecord;

/**
 * SLSB to manage persisted classes. This would be the table model.
 * 
 * @author Archimedes Trajano
 */
@Stateless
@BusinessMethod
public class PersistedBeans {
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

	/**
	 * Injects the entity manager for testing.
	 * 
	 * @param entityManager
	 *            entity manager.
	 */
	public void setEntityManager(final EntityManager entityManager) {
		em = entityManager;
	}
}

package net.trajano.app;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

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
	 * Gets a specific record.
	 * 
	 * @param id
	 *            primary key
	 * @return persisted bean
	 */
	public PersistedBean get(final long id) {
		return em.find(PersistedBean.class, id);
	}

	/**
	 * Gets the latest record. It returns null if there are no records.
	 * 
	 * @return latest record.
	 */
	public PersistedBean getLatest() {
		final List<PersistedBean> resultList = em
				.createQuery(
						"select p from PersistedBean p order by p.someTimestamp desc, p.id desc",
						PersistedBean.class).setFlushMode(FlushModeType.AUTO)
				.getResultList();
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	/**
	 * Save, flush and refresh a persisted bean. The flush and refreshing is
	 * needed to ensure that the temporal data contains the proper values.
	 * 
	 * @param bean
	 *            bean to persist.
	 */
	public void save(final PersistedBean bean) {
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

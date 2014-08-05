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
     * Gets all the records.
     *
     * @return the contents of the table.
     */
    public List<PersistedBean> getAll() {
        return em
                .createNamedQuery("PersistedBean.getAllReverseOrder",
                        PersistedBean.class).setFlushMode(FlushModeType.AUTO)
                .getResultList();
    }

    /**
     * Gets a message.
     *
     * @param message
     *            message to search for
     * @return the contents of the table.
     */
    public List<PersistedBean> getByMessage(final String message) {
        return em
                .createNamedQuery("PersistedBean.getAllByMessage",
                        PersistedBean.class).setFlushMode(FlushModeType.AUTO)
                .setParameter("message", message).getResultList();
    }

    /**
     * Gets the latest record. It returns null if there are no records.
     *
     * @return latest record.
     */
    public PersistedBean getLatest() {
        final List<PersistedBean> resultList = getAll();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    /**
     * Removes persisted data.
     *
     * @param bean
     *            bean to remove
     */
    public void remove(final PersistedBean bean) {
        em.remove(bean);
        em.flush();
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

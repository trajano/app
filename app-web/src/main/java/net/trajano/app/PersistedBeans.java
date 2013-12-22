package net.trajano.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

/**
 * SLSB to manage persisted classes. This would be the table model.
 * 
 * @author Archimedes
 */
@Stateless
// @Interceptors(LoggingInterceptor.class)
// @BusinessMethod
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

    // @RequestScoped
    public PersistedBean getLatest() {
        final PersistedBean bean = em
                .createQuery(
                        "select p from PersistedBean p order by p.someDate desc, p.id desc",
                        PersistedBean.class).setFlushMode(FlushModeType.AUTO)
                .getResultList().get(0);
        // em.refresh(bean);
        return bean;
    }

    // @RequestScoped
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
}

package net.trajano.app;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersistedBeans {
    /**
     * Entity manager.
     */
    @PersistenceContext
    private EntityManager em;

    public PersistedBean getLatest() {
        return em
                .createQuery("select p from PersistedBean p order by p.date",
                        PersistedBean.class).getResultList().get(0);
    }

    public void save(final PersistedBean bean) {
        em.persist(bean);
    }

}

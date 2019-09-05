package de.home.media.api.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class BaseService<E extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<E> entityClass;

    protected BaseService(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public Optional<E> find(Integer id) {
        return Optional.ofNullable(this.entityManager.find(this.entityClass, id));
    }

    public List<E> find() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(this.entityClass);
        Root<E> qr = cq.from(this.entityClass);
        return this.entityManager.createQuery(cq)
                .getResultList();
    }

    public List<E> find(Parameter param) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(this.entityClass);
        Root<E> qr = cq.from(this.entityClass);
        return this.entityManager.createQuery(cq)
                .setFirstResult(param.getFirstResult())
                .setMaxResults(param.getRpp())
                .getResultList();
    }

    public void persist(E entity) {
        this.entityManager.persist(entity);
    }

    public Long count() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        return this.entityManager.createQuery(cq).getSingleResult();
    }
}

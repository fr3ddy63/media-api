package de.home.media.api.user;

import de.home.media.api.common.BaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class UserService extends BaseService<User> {

    public UserService() {
        super(User.class);
    }

    public Optional<User> find(String name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> qr = cq.from(User.class);
        cq.where(cb.equal(qr.get(User_.name), name));
        TypedQuery<User> tq = this.entityManager.createQuery(cq);
        return tq.getResultStream().findFirst();
    }
}

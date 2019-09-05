package de.home.media.api.group;

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
public class GroupService extends BaseService<Group> {

    public GroupService() {
        super(Group.class);
    }

    public Optional<Group> find(String name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        Root<Group> qr = cq.from(Group.class);
        cq.where(cb.equal(qr.get(Group_.name), name));
        TypedQuery<Group> tq = this.entityManager.createQuery(cq);
        return tq.getResultStream().findFirst();
    }
}

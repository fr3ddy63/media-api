package de.home.media.api.location;

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
public class LocationService extends BaseService<Location> {

    public LocationService() {
        super(Location.class);
    }

    public Optional<Location> find(String name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);
        cq.where(cb.equal(root.get(Location_.name), name));
        TypedQuery<Location> tq = this.entityManager.createQuery(cq);
        return tq.getResultStream().findFirst();
    }
}
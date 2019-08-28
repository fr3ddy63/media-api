package de.home.media.api.mediumlocation;

import de.home.media.api.common.BaseService;
import de.home.media.api.location.Location;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class MediumLocationService extends BaseService<MediumLocation> {

    public MediumLocationService() {
        super(MediumLocation.class);
    }

    public Optional<MediumLocation> find(Integer index, Location location) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MediumLocation> cq = cb.createQuery(MediumLocation.class);
        Root<MediumLocation> root = cq.from(MediumLocation.class);
        cq.where(cb.and(
                cb.equal(root.get(MediumLocation_.index), index),
                cb.equal(root.get(MediumLocation_.location), location)));
        TypedQuery<MediumLocation> tq = this.entityManager.createQuery(cq);
        return tq.getResultStream().findFirst();
    }

    public List<MediumLocation> find(Location location) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MediumLocation> cq = cb.createQuery(MediumLocation.class);
        Root<MediumLocation> root = cq.from(MediumLocation.class);
        cq.where(cb.equal(root.get(MediumLocation_.location), location));
        TypedQuery<MediumLocation> tq = this.entityManager.createQuery(cq);
        return tq.getResultList();
    }
}

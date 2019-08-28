package de.home.media.api.medium;

import de.home.media.api.artist.Artist;
import de.home.media.api.artist.Artist_;
import de.home.media.api.common.BaseService;
import de.home.media.api.common.Parameter;
import de.home.media.api.location.Location;
import de.home.media.api.mediumlocation.MediumLocation_;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class MediumService extends BaseService<Medium> {

    public MediumService() {
        super(Medium.class);
    }

    public List<Medium> find(Location location, Parameter param) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Medium> cq = cb.createQuery(Medium.class);
        Root<Medium> root = cq.from(Medium.class);
        cq.where(
                cb.equal(
                        root.get(Medium_.location).get(MediumLocation_.location),
                        location));
        TypedQuery<Medium> tq = this.entityManager.createQuery(cq);
        return tq.setFirstResult(param
                .getFirstResult())
                .setMaxResults(param.getRpp())
                .getResultList();
    }

    public List<Medium> find(Artist artist, Parameter param) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Medium> cq = cb.createQuery(Medium.class);
        Root<Medium> qr = cq.from(Medium.class);
        cq.where(cb.equal(qr.get(Medium_.artist), artist));
        TypedQuery<Medium> tq = this.entityManager.createQuery(cq);
        return tq.setFirstResult(param
                .getFirstResult())
                .setMaxResults(param.getRpp())
                .getResultList();
    }

    public List<Medium> find(Location location, Artist artist, Parameter param) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Medium> cq = cb.createQuery(Medium.class);
        Root<Medium> root = cq.from(Medium.class);
        cq.where(cb.and(
                cb.equal(root.get(Medium_.location).get(MediumLocation_.location), location),
                cb.equal(root.get(Medium_.artist), artist)));
        TypedQuery<Medium> tq = this.entityManager.createQuery(cq);
        return tq.setFirstResult(param
                .getFirstResult())
                .setMaxResults(param.getRpp())
                .getResultList();
    }

    Long count(Artist artist) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Medium> qr = cq.from(Medium.class);
        cq.select(cb.count(qr));
        cq.where(cb.equal(qr.get(Medium_.artist), artist));
        return this.entityManager.createQuery(cq).getSingleResult();
    }

    Long count(Location location) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Medium> qr = cq.from(Medium.class);
        cq.select(cb.count(qr));
        cq.where(cb.equal(qr.get(Medium_.location), location));
        return this.entityManager.createQuery(cq).getSingleResult();
    }

    Long count(Location location, Artist artist) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Medium> qr = cq.from(Medium.class);
        cq.select(cb.count(qr));
        cq.where(cb.and(
                cb.equal(qr.get(Medium_.location).get(MediumLocation_.location), location),
                cb.equal(qr.get(Medium_.artist), artist)));
        return this.entityManager.createQuery(cq).getSingleResult();
    }
}

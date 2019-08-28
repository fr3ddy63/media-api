package de.home.media.api.artist;

import de.home.media.api.common.BaseService;
import de.home.media.api.location.Location;
import de.home.media.api.medium.Medium_;
import de.home.media.api.mediumlocation.MediumLocation;
import de.home.media.api.mediumlocation.MediumLocation_;

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
public class ArtistService extends BaseService<Artist> {

    public ArtistService() {
        super(Artist.class);
    }

    public Optional<Artist> find(String name) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);
        Root<Artist> root = cq.from(Artist.class);
        cq.where(cb.equal(root.get(Artist_.name), name));
        TypedQuery<Artist> tq = this.entityManager.createQuery(cq);
        return tq.getResultStream().findFirst();
    }

    /*public List<Artist> find(Location location) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);
        Root<Artist> root = cq.from(Artist.class);
        cq.where(cb.equal(root.get(Artist_.media).get()));
        cq.from(MediumLocation.class).get(MediumLocation_.location).get(Medium_.location)
        cq.where(
                cb.isMember(root.get())
        )
        TypedQuery<Artist> tq = this.entityManager.createQuery(cq);
    }*/
}

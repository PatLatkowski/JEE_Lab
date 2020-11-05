package com.jeelab.artist.repository;

import com.jeelab.artist.entity.Artist;
import com.jeelab.datastore.DataStore;
import com.jeelab.repository.Repository;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ArtistRepository implements Repository<Artist, Long> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public Optional<Artist> find(Long id) { return Optional.ofNullable(entityManager.find(Artist.class, id)); }

    @Override
    public List<Artist> findAll() { return entityManager.createQuery("select artist from Artist artist", Artist.class).getResultList(); }

    @Override
    public void create(Artist artist) { entityManager.persist(artist); }

    @Override
    public void delete(Artist artist) { entityManager.remove(entityManager.find(Artist.class, artist.getId())); }

    @Override
    public void update(Artist artist) { entityManager.merge(artist); }

    @Override
    public void detach(Artist artist) { entityManager.detach(artist); }

}

package com.jeelab.album.repository;

import com.jeelab.album.entity.Album;
import com.jeelab.artist.entity.Artist;
import com.jeelab.datastore.DataStore;
import com.jeelab.repository.Repository;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AlbumRepository implements Repository<Album, Long> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public Optional<Album> find(Long id) { return Optional.ofNullable(entityManager.find(Album.class, id)); }

    @Override
    public List<Album> findAll() { return entityManager.createQuery("select album from Album album", Album.class).getResultList(); }

    public List<Album> findAllArtistAlbum(Artist artist) {
        return entityManager.createQuery("select album from Album album where album.artist = :artist", Album.class)
                .setParameter("artist", artist)
                .getResultList();
    }

    @Override
    public void create(Album album) { entityManager.persist(album); }

    @Override
    public void delete(Album album) { entityManager.remove(entityManager.find(Album.class, album.getId())); }

    @Override
    public void update(Album album) { entityManager.merge(album); }

    @Override
    public void detach(Album album) { entityManager.detach(album); }
}

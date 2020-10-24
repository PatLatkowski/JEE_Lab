package com.jeelab.album.repository;

import com.jeelab.album.entity.Album;
import com.jeelab.datastore.DataStore;
import com.jeelab.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class AlbumRepository implements Repository<Album, Long> {

    private DataStore store;

    @Inject
    public AlbumRepository(DataStore store) { this.store = store; }

    @Override
    public Optional<Album> find(Long id) { return store.findAlbum(id); }

    @Override
    public List<Album> findAll() { throw new UnsupportedOperationException("Not implemented."); }

    public List<Album> findAllArtistAlbum(Long id) { return store.findAllArtistAlbum(id); }

    @Override
    public Album create(Album album) { return store.createAlbum(album); }

    @Override
    public void delete(Album album) { store.deleteAlbum(album.getId()); }

    @Override
    public void update(Album album) { store.updateAlbum(album); }
}

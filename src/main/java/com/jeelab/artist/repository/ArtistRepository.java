package com.jeelab.artist.repository;

import com.jeelab.artist.entity.Artist;
import com.jeelab.datastore.DataStore;
import com.jeelab.recordcompany.entity.RecordCompany;
import com.jeelab.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class ArtistRepository implements Repository<Artist, Long> {

    private DataStore store;

    @Inject
    public ArtistRepository(DataStore store) { this.store = store; }

    @Override
    public Optional<Artist> find(Long id) { return store.findArtist(id); }

    @Override
    public List<Artist> findAll() { return store.findAllArtists(); }

    @Override
    public void create(Artist artist) { store.createArtist(artist); }

    @Override
    public void delete(Artist artist) { store.deleteArtist(artist.getId()); }

    @Override
    public void update(Artist artist) { store.updateArtist(artist); }

}

package com.jeelab.artist.service;

import com.jeelab.artist.entity.Artist;
import com.jeelab.artist.repository.ArtistRepository;
import lombok.NoArgsConstructor;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class ArtistService {

    private ArtistRepository artistRepository;

    @Inject
    public ArtistService(ArtistRepository artistRepository) { this.artistRepository = artistRepository; }

    public Optional<Artist> find(Long id) { return artistRepository.find(id); }

    public List<Artist> findAll() { return artistRepository.findAll(); }

    @Transactional
    public void create(Artist artist) { artistRepository.create(artist); }

    @Transactional
    public void update(Artist artist) {
        Artist originalArtist = artistRepository.find(artist.getId()).orElseThrow();
        artistRepository.detach(originalArtist);
        artistRepository.update(artist);
    }

    @Transactional
    public void delete(Long artist) { artistRepository.delete(artistRepository.find(artist).orElseThrow()); }

}

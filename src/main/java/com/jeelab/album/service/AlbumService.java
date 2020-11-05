package com.jeelab.album.service;

import com.jeelab.album.entity.Album;
import com.jeelab.album.repository.AlbumRepository;
import com.jeelab.artist.repository.ArtistRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AlbumService {

    private AlbumRepository albumRepository;

    private ArtistRepository artistRepository;

    @Inject
    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    public Optional<Album> find(Long id) { return albumRepository.find(id); }

    public List<Album> findAllArtistAlbum(Long id) { return albumRepository.findAllArtistAlbum(artistRepository.find(id).orElseThrow()); }

    @Transactional
    public void create(Album album) {
        albumRepository.create(album);
        artistRepository.find(album.getArtist().getId()).ifPresent(artist -> artist.getAlbums().add(album));
    }

    @Transactional
    public void update(Album album) {
        Album originalAlbum = albumRepository.find(album.getId()).orElseThrow();
        albumRepository.detach(originalAlbum);
        if(!originalAlbum.getArtist().getId().equals(album.getArtist().getId())) {
            originalAlbum.getArtist().getAlbums().removeIf(albumToRemove -> albumToRemove.getId().equals(album.getId()));
            artistRepository.find(album.getArtist().getId()).ifPresent(artist -> artist.getAlbums().add(album));
        }
        albumRepository.update(album);
    }

    @Transactional
    public void delete(Long albumId) {
        Album album = albumRepository.find(albumId).orElseThrow();
        album.getArtist().getAlbums().remove(album);
        albumRepository.delete(album);
    }
}

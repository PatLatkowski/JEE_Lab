package com.jeelab.album.service;

import com.jeelab.album.entity.Album;
import com.jeelab.album.repository.AlbumRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AlbumService {

    private AlbumRepository albumRepository;

    @Inject
    public AlbumService(AlbumRepository albumRepository) { this.albumRepository = albumRepository; }

    public Optional<Album> find(Long id) { return albumRepository.find(id); }

    public List<Album> findAllArtistAlbum(Long id) { return albumRepository.findAllArtistAlbum(id); }

    public Album create(Album album) { return albumRepository.create(album); }

    public void update(Album album) { albumRepository.update(album); }

    public void delete(Long album) { albumRepository.delete(albumRepository.find(album).orElseThrow()); }
}

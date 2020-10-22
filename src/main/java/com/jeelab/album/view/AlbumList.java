package com.jeelab.album.view;

import com.jeelab.album.model.AlbumsModel;
import com.jeelab.album.service.AlbumService;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class AlbumList implements Serializable {

    private final AlbumService albumService;

    private AlbumsModel albums;

    @Inject
    public AlbumList(AlbumService albumService) { this.albumService = albumService; }

    public AlbumsModel getAlbums(Long id) {
        if(albums == null) {
            this.albums = AlbumsModel.entityToModelMapper().apply(albumService.findAllArtistAlbum(id));
        }
        return albums;
    }

    public String deleteAction(AlbumsModel.ShortenedAlbum album) {
        albumService.delete(album.getId());
        return "artist_list?faces-redirect=true";
    }
}

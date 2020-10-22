package com.jeelab.artist.view;

import com.jeelab.artist.model.ArtistsModel;
import com.jeelab.artist.service.ArtistService;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class ArtistList implements Serializable {

    private final ArtistService artistService;

    private ArtistsModel artists;

    @Inject
    public ArtistList(ArtistService artistService) { this.artistService = artistService; }

    public ArtistsModel getArtists() {
        if(artists == null) {
            artists = ArtistsModel.entityToModelMapper().apply(artistService.findAll());
        }
        return artists;
    }

    public String deleteAction(ArtistsModel.ShortenedArtist artist) {
        artistService.delete(artist.getId());
        return "artist_list?faces-redirect=true";
    }

}

package com.jeelab.artist.view;

import com.jeelab.artist.entity.Artist;
import com.jeelab.artist.model.ArtistModel;
import com.jeelab.artist.service.ArtistService;
import lombok.Getter;
import lombok.Setter;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class ArtistView implements Serializable {

    private final ArtistService artistService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private ArtistModel artist;

    @Inject
    public ArtistView(ArtistService artistService) { this.artistService = artistService; }

    public void init() throws IOException {
        Optional<Artist> artist = artistService.find(id);
        if(artist.isPresent()) {
            this.artist = ArtistModel.entityToModelMapper().apply(artist.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Artist not found");
        }
    }
}

package com.jeelab.album.view;

import com.jeelab.album.entity.Album;
import com.jeelab.album.model.AlbumEditModel;
import com.jeelab.album.service.AlbumService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class AlbumEdit implements Serializable {

    private final AlbumService albumService;
    private final ArtistService artistService;

    @Getter
    @Setter
    private Long id;

    @Getter
    private AlbumEditModel albumEditModel;

    @Getter
    private List<ArtistModel> artistModelList;

    @Inject
    public AlbumEdit(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    public void init() throws IOException {
        artistModelList = artistService.findAll().stream()
                .map(ArtistModel.entityToModelMapper())
                .collect(Collectors.toList());
        Optional<Album> album = albumService.find(id);
        if(album.isPresent()) {
            this.albumEditModel = AlbumEditModel.entityToModelMapper().apply(album.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Album not found");
        }
    }

    public String saveAction() {
        albumService.update(AlbumEditModel.modelToEntityUpdater(
                artist -> artistService.find(artist).orElseThrow())
                .apply(albumService.find(id).orElseThrow(), albumEditModel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}

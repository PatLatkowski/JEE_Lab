package com.jeelab.album.view;

import com.jeelab.album.entity.Album;
import com.jeelab.album.model.AlbumModel;
import com.jeelab.album.service.AlbumService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
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
public class AlbumView implements Serializable {

    private final AlbumService albumService;

    @Setter
    @Getter
    private Long id;

    @Getter
    private AlbumModel albumModel;

    @Inject
    public AlbumView(AlbumService albumService) { this.albumService = albumService; }

    public void init() throws IOException {
        Optional<Album> album = albumService.find(id);
        if(album.isPresent()) {
            this.albumModel = albumModel.entityToModelMapper().apply(album.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Album not found");
        }
    }
}

package com.jeelab.album.view;

import com.jeelab.album.model.AlbumCreateModel;
import com.jeelab.album.service.AlbumService;
import com.jeelab.artist.model.ArtistModel;
import com.jeelab.artist.service.ArtistService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
@NoArgsConstructor
public class AlbumCreate implements Serializable {

    private AlbumService albumService;
    private ArtistService artistService;

    @Getter
    @Setter
    private Long id;

    @Getter
    private AlbumCreateModel albumCreateModel;

    @Getter
    private List<ArtistModel> artistModelList;

    @Inject
    public AlbumCreate(AlbumService albumService, ArtistService artistService) {
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @PostConstruct
    public void init() {
        artistModelList = artistService.findAll().stream()
                .map(ArtistModel.entityToModelMapper())
                .collect(Collectors.toList());
        albumCreateModel = AlbumCreateModel.builder().build();
    }

    public String saveAction() {
        System.out.println(albumCreateModel);
        albumService.find(albumCreateModel.getId()).ifPresentOrElse(
            original -> {
                throw new IllegalArgumentException(
                        String.format("The artist with id \"%d\" already exist", albumCreateModel.getId()));
            },
            () -> {
                albumService.create(AlbumCreateModel.modelToEntityMapper(
                        artist -> artistService.find(artist).orElseThrow())
                        .apply(albumCreateModel));
            }
        );
        return "/artist/artist_list?faces-redirect=true";
    }


}

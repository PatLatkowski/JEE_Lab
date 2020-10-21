package com.jeelab.album.model;

import com.jeelab.album.entity.Album;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AlbumsModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ShortenedAlbum {

        private Long id;
        private String name;

    }

    @Singular
    private List<ShortenedAlbum> albums;

    public static Function<Collection<Album>, AlbumsModel> entityToModelMapper() {
        return albums -> {
            AlbumsModel.AlbumsModelBuilder model = AlbumsModel.builder();
            albums.stream()
                    .map(album -> ShortenedAlbum.builder()
                        .id(album.getId())
                        .name(album.getName())
                        .build())
                    .forEach(model::album);
            return model.build();
        };
    }
}

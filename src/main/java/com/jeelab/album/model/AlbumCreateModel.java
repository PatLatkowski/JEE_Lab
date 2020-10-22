package com.jeelab.album.model;

import com.jeelab.album.entity.Album;
import com.jeelab.artist.entity.Artist;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AlbumCreateModel {

    private Long id;
    private String name;
    private String releaseDate;
    private Long artist;

    public static Function<AlbumCreateModel, Album> modelToEntityMapper(
            Function<Long, Artist> artistFunction
    ) {
        return model -> Album.builder()
                .id(model.getId())
                .name(model.getName())
                .releaseDate(LocalDate.parse(model.getReleaseDate()))
                .artist(artistFunction.apply(model.getArtist()))
                .build();
    }
}

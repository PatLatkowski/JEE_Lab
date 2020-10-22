package com.jeelab.album.model;

import com.jeelab.album.entity.Album;
import com.jeelab.artist.entity.Artist;
import lombok.*;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AlbumEditModel {

    private String name;
    private String releaseDate;
    private Long artist;

    public static Function<Album, AlbumEditModel> entityToModelMapper() {
        return album -> AlbumEditModel.builder()
                .name(album.getName())
                .releaseDate(album.getReleaseDate().toString())
                .artist(album.getArtist().getId())
                .build();
    }

    public static BiFunction<Album, AlbumEditModel, Album> modelToEntityUpdater(
            Function<Long, Artist> artistFunction
    ) {
        return (album, request) -> {
            album.setName(request.getName());
            album.setReleaseDate(LocalDate.parse(request.getReleaseDate()));
            album.setArtist(artistFunction.apply(request.getArtist()));
            return album;
        };
    }
}

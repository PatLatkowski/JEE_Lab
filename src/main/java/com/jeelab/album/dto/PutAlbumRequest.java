package com.jeelab.album.dto;

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
public class PutAlbumRequest {
    private String name;
    private String releaseDate;

    public static BiFunction<Album, PutAlbumRequest, Album> dtoToEntityUpdater()
    {
        return ((album, putAlbumRequest) -> {
            album.setName(putAlbumRequest.getName());
            album.setReleaseDate(LocalDate.parse(putAlbumRequest.getReleaseDate()));
            return album;
        });
    }
}

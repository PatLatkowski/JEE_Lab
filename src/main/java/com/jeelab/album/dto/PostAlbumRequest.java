package com.jeelab.album.dto;

import com.jeelab.album.entity.Album;
import com.jeelab.artist.entity.Artist;
import com.jeelab.recordcompany.entity.RecordCompany;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostAlbumRequest {
    private String name;
    private String releaseDate;

    public static Function<PostAlbumRequest, Album> dtoToEntityMapper(
            Optional<Artist> artist
    ) {
        return postAlbumRequest -> Album.builder()
                .name(postAlbumRequest.getName())
                .releaseDate(LocalDate.parse(postAlbumRequest.getReleaseDate()))
                .artist(artist.get())
                .build();
    }
}

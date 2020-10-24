package com.jeelab.artist.dto;

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
public class PostArtistRequest {
    private String name;
    private String birthday;
    private String genre;
    private boolean isContracted;

    public static Function<PostArtistRequest, Artist> dtoToEntityMapper() {
        return postArtistRequest -> Artist.builder()
                .name(postArtistRequest.getName())
                .birthday(LocalDate.parse(postArtistRequest.getBirthday()))
                .genre(postArtistRequest.getGenre())
                .isContracted(postArtistRequest.isContracted())
                .build();
    }
}

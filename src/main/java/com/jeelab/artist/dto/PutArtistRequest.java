package com.jeelab.artist.dto;

import com.jeelab.artist.entity.Artist;
import lombok.*;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutArtistRequest {
    private String name;
    private String birthday;
    private String genre;
    private boolean isContracted;

    public static BiFunction<Artist, PutArtistRequest, Artist> dtoToEntityUpdater() {
        return ((artist, putArtistRequest) -> {
            artist.setName(putArtistRequest.getName());
            artist.setBirthday(LocalDate.parse(putArtistRequest.getBirthday()));
            artist.setGenre(putArtistRequest.getGenre());
            artist.setContracted(putArtistRequest.isContracted());
            return artist;
        });
    }
}

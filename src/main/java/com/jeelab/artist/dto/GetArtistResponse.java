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
public class GetArtistResponse {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String genre;
    private boolean isContracted;

    public static Function<Artist, GetArtistResponse> entityToDtoMapper() {
        return artist -> GetArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .birthday(artist.getBirthday())
                .genre(artist.getGenre())
                .isContracted(artist.isContracted())
                .build();
    }
}

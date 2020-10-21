package com.jeelab.artist.model;

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
public class ArtistModel {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String genre;
    private boolean isContracted;

    public static Function<Artist, ArtistModel> entityToModelMapper() {
        return artist -> ArtistModel.builder()
                .id(artist.getId())
                .name(artist.getName())
                .birthday(artist.getBirthday())
                .genre(artist.getGenre())
                .isContracted(artist.isContracted())
                .build();
    }
}

package com.jeelab.artist.dto;

import com.jeelab.artist.entity.Artist;
import lombok.*;

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
public class GetArtistsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ShortenedArtist {
        private Long id;
        private String name;
    }

    @Singular
    private List<ShortenedArtist> artists;

    public static Function<Collection<Artist>, GetArtistsResponse> entityToDtoMapper() {
        return artists -> {
            GetArtistsResponseBuilder response = GetArtistsResponse.builder();
            artists.stream()
                    .map(artist -> ShortenedArtist.builder()
                        .id(artist.getId())
                        .name(artist.getName())
                        .build())
                    .forEach(response::artist);
            return response.build();
        };
    }
}

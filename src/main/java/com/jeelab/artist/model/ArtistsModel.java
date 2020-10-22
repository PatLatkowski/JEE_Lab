package com.jeelab.artist.model;

import com.jeelab.artist.entity.Artist;
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
public class ArtistsModel implements Serializable {

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

    public static Function<Collection<Artist>, ArtistsModel> entityToModelMapper() {
        return artists -> {
            ArtistsModel.ArtistsModelBuilder model = ArtistsModel.builder();
            artists.stream()
                    .map(artist -> ShortenedArtist.builder()
                    .id(artist.getId())
                    .name(artist.getName())
                    .build())
                    .forEach(model::artist);
            return model.build();
        };
    }
}

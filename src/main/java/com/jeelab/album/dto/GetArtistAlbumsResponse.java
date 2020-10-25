package com.jeelab.album.dto;

import com.jeelab.album.entity.Album;
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
public class GetArtistAlbumsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ShortenedAlbum {
        private Long id;
        private String name;
    }

    @Singular
    private List<ShortenedAlbum> albums;

    public static Function<Collection<Album>, GetArtistAlbumsResponse> entityToDtoMapper() {
        return albums -> {
            GetArtistAlbumsResponseBuilder response = GetArtistAlbumsResponse.builder();
            albums.stream()
                    .map(album -> ShortenedAlbum.builder()
                        .id(album.getId())
                        .name(album.getName())
                        .build())
                    .forEach(response::album);
            return response.build();
        };
    }
}

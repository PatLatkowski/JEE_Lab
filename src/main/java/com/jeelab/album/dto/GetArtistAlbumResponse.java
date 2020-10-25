package com.jeelab.album.dto;

import com.jeelab.album.entity.Album;
import com.jeelab.recordcompany.entity.RecordCompany;
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
public class GetArtistAlbumResponse {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private RecordCompany recordCompany;
    private Long artist;

    public static Function<Album, GetArtistAlbumResponse> entityToDtoMapper(){
        return album -> GetArtistAlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .releaseDate(album.getReleaseDate())
                .artist(album.getArtist().getId())
                .build();
    }
}

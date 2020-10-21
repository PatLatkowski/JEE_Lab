package com.jeelab.album.model;

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
public class AlbumModel {
    private Long id;
    private String name;
    private LocalDate releasedate;
    private RecordCompany recordCompany;
    private String artist;

    public static Function<Album, AlbumModel> entityToModelMapper() {
        return album -> AlbumModel.builder()
                .id(album.getId())
                .name(album.getName())
                .releasedate(album.getReleaseDate())
                .artist(album.getArtist().getName())
                .build();
    }
}

package com.jeelab.album.entity;

import com.jeelab.artist.entity.Artist;
import com.jeelab.recordcompany.entity.RecordCompany;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Album implements Serializable {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private RecordCompany recordCompany;
    private Artist artist;
}

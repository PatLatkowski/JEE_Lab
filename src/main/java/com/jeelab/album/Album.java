package com.jeelab.album;

import com.jeelab.artist.Artist;
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
    private String name;
    private LocalDate releasedate;
    private RecordCompany recordCompany;
    private Artist artist;
}

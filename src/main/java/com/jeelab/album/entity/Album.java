package com.jeelab.album.entity;

import com.jeelab.artist.entity.Artist;
import com.jeelab.recordcompany.entity.RecordCompany;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "albums")
public class Album implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private RecordCompany recordCompany;

    @ManyToOne
    @JoinColumn(name = "artist")
    private Artist artist;
}

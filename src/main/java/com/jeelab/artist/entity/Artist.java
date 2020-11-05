package com.jeelab.artist.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.jeelab.album.entity.Album;
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
@Table(name = "artists")
public class Artist implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDate birthday;

    private String genre;

    @Column(name = "is_contracted")
    private boolean isContracted;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private List<Album> albums;
}

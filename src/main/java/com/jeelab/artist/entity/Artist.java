package com.jeelab.artist.entity;

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
public class Artist implements Serializable {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String genre;
    private boolean isContracted;
}

package com.jeelab.recordcompany.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class RecordCompany implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate foundationDate;
    private byte[] avatar;
}

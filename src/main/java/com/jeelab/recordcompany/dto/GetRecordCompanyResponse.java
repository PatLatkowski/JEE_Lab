package com.jeelab.recordcompany.dto;

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
public class GetRecordCompanyResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate foundationDate;

    public static Function<RecordCompany, GetRecordCompanyResponse> entityToDtoMapper() {
        return recordcompany -> GetRecordCompanyResponse.builder()
                .id(recordcompany.getId())
                .username(recordcompany.getUsername())
                .password(recordcompany.getPassword())
                .email(recordcompany.getEmail())
                .foundationDate(recordcompany.getFoundationDate())
                .build();
    }
}

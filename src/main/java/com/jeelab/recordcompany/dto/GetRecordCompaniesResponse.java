package com.jeelab.recordcompany.dto;

import lombok.*;
import java.time.LocalDate;
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
public class GetRecordCompaniesResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class RecordCompany {
        private Long id;
        private String username;
        private String password;
        private String email;
        private LocalDate foundationDate;
    }

    @Singular
    private List<RecordCompany> recordCompanies;

    public static Function<Collection<com.jeelab.recordcompany.entity.RecordCompany>, GetRecordCompaniesResponse> entityToDtoMapper() {
        return recordCompanies -> {
            GetRecordCompaniesResponse.GetRecordCompaniesResponseBuilder response = GetRecordCompaniesResponse.builder();
            recordCompanies.stream()
                    .map(recordCompany -> RecordCompany.builder()
                            .id(recordCompany.getId())
                            .username(recordCompany.getUsername())
                            .password(recordCompany.getPassword())
                            .email(recordCompany.getEmail())
                            .foundationDate(recordCompany.getFoundationDate())
                            .build())
                    .forEach(response::recordCompany);
            return response.build();
        };
    }
}

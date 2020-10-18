package com.jeelab.configuration;
import com.jeelab.recordcompany.service.RecordCompanyService;
import com.jeelab.recordcompany.entity.RecordCompany;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

@ApplicationScoped
public class InitializedData {

    private final RecordCompanyService recordCompanyService;

    @Inject
    public InitializedData(RecordCompanyService recordCompanyService) {
        this.recordCompanyService = recordCompanyService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) { init(); }

    private synchronized void init() {
        RecordCompany recordCompany = RecordCompany.builder()
                .id(1L)
                .username("test")
                .password("123456")
                .email("email@gmail.com")
                .foundationDate(LocalDate.of(2005,1,1))
                .build();
        RecordCompany recordCompany2 = RecordCompany.builder()
                .id(2L)
                .username("Warner Music Group")
                .password("123456")
                .email("WarnerMusicGroup@gmail.com")
                .foundationDate(LocalDate.of(2010,2,2))
                .build();
        RecordCompany recordCompany3 = RecordCompany.builder()
                .id(3L)
                .username("IslandRecords")
                .password("123456")
                .email("IslandRecordsl@gmail.com")
                .foundationDate(LocalDate.of(2012,3,3))
                .build();
        RecordCompany recordCompany4 = RecordCompany.builder()
                .id(4L)
                .username("Virgin Records")
                .password("123456")
                .email("Virgin Records@gmail.com")
                .foundationDate(LocalDate.of(2011,4,4))
                .build();
        recordCompanyService.create(recordCompany);
        recordCompanyService.create(recordCompany2);
        recordCompanyService.create(recordCompany3);
        recordCompanyService.create(recordCompany4);
    }

}

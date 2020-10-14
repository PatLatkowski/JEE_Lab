package com.jeelab.configuration;
import com.jeelab.recordcompany.service.RecordCompanyService;
import com.jeelab.recordcompany.entity.RecordCompany;
import lombok.SneakyThrows;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
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
                .avatar(null)
                .build();
        RecordCompany recordCompany2 = RecordCompany.builder()
                .id(2L)
                .username("Warner Music Group")
                .password("123456")
                .email("WarnerMusicGroup@gmail.com")
                .foundationDate(LocalDate.of(2010,2,2))
                .avatar(getResourceAsByteArray("avatar/2.png"))
                .build();
        RecordCompany recordCompany3 = RecordCompany.builder()
                .id(3L)
                .username("IslandRecords")
                .password("123456")
                .email("IslandRecordsl@gmail.com")
                .foundationDate(LocalDate.of(2012,3,3))
                .avatar(getResourceAsByteArray("avatar/3.png"))
                .build();
        recordCompanyService.create(recordCompany);
        recordCompanyService.create(recordCompany2);
        recordCompanyService.create(recordCompany3);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

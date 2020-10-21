package com.jeelab.configuration;
import com.jeelab.album.entity.Album;
import com.jeelab.album.service.AlbumService;
import com.jeelab.artist.entity.Artist;
import com.jeelab.artist.service.ArtistService;
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
    private final ArtistService artistService;
    private final AlbumService albumService;

    @Inject
    public InitializedData(RecordCompanyService recordCompanyService, ArtistService artistService, AlbumService albumService) {
        this.recordCompanyService = recordCompanyService;
        this.artistService = artistService;
        this.albumService = albumService;
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

        Artist artist1 = Artist.builder()
                .id(1L)
                .name("Kanye West")
                .birthday(LocalDate.of(1970,4,4))
                .genre("Music?")
                .isContracted(true)
                .build();
        Artist artist2 = Artist.builder()
                .id(2L)
                .name("Kanye West2")
                .birthday(LocalDate.of(1970,4,4))
                .genre("Music?")
                .isContracted(true)
                .build();
        Artist artist3 = Artist.builder()
                .id(3L)
                .name("Kanye West3")
                .birthday(LocalDate.of(1970,4,4))
                .genre("Music?")
                .isContracted(true)
                .build();

        artistService.create(artist1);
        artistService.create(artist2);
        artistService.create(artist3);

        Album album1 = Album.builder()
                .id(1L)
                .name("First Tape")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist1)
                .build();

        Album album2 = Album.builder()
                .id(2L)
                .name("Second Tape")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist1)
                .build();

        Album album3 = Album.builder()
                .id(3L)
                .name("Tralala")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist2)
                .build();

        Album album4 = Album.builder()
                .id(4L)
                .name("Selero la la la la la")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist2)
                .build();

        Album album5 = Album.builder()
                .id(5L)
                .name("Wub wub")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist3)
                .build();

        Album album6 = Album.builder()
                .id(6L)
                .name("Kanye West")
                .releaseDate(LocalDate.of(2000,1,1))
                .recordCompany(null)
                .artist(artist3)
                .build();

        albumService.create(album1);
        albumService.create(album2);
        albumService.create(album3);
        albumService.create(album4);
        albumService.create(album5);
        albumService.create(album6);

    }

}

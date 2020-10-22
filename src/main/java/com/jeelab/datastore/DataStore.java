package com.jeelab.datastore;

import com.jeelab.album.entity.Album;
import com.jeelab.artist.entity.Artist;
import com.jeelab.recordcompany.entity.RecordCompany;
import lombok.extern.java.Log;
import com.jeelab.serialization.CloningUtility;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {

    private Set<RecordCompany> recordCompanies = new HashSet<>();
    private Set<Artist> artists = new HashSet<>();
    private Set<Album> albums = new HashSet<>();

    /**
     * RecordCompany entity functions
     */

    public synchronized List<RecordCompany> findAllRecordCompanies() {
        return recordCompanies.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<RecordCompany> findRecordCompany(Long id) {
        return recordCompanies.stream()
                .filter(recordCompany -> recordCompany.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createRecordCompany(RecordCompany recordCompany) throws IllegalArgumentException {
        findRecordCompany(recordCompany.getId()).ifPresentOrElse(original -> {
            throw new IllegalArgumentException(
                    String.format("The user login \"%s\" is not unique", recordCompany.getId()));
        },
        () -> recordCompanies.add(recordCompany));
    }

    public synchronized void updateRecordCompany(RecordCompany recordCompany) throws IllegalArgumentException {
        findRecordCompany(recordCompany.getId()).ifPresentOrElse(
                original -> {
                    recordCompanies.remove(original);
                    recordCompanies.add(recordCompany);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The record company with id \"%d\" does not exist", recordCompany.getId()));
                }
        );
    }

    /**
     * Artist entity functions
     */

    public synchronized List<Artist> findAllArtists() {
        return artists.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<Artist> findArtist(Long id) {
        return artists.stream()
                .filter(artist -> artist.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createArtist(Artist artist) throws IllegalArgumentException {
        findArtist(artist.getId()).ifPresentOrElse(original -> {
                    throw new IllegalArgumentException(
                            String.format("The artist id \"%s\" is not unique", artist.getId()));
                },
                () -> artists.add(artist));
    }

    public synchronized void updateArtist(Artist artist) throws IllegalArgumentException {
        findArtist(artist.getId()).ifPresentOrElse(
                original -> {
                    artists.remove(original);
                    artists.add(artist);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The artist with id \"%d\" does not exist", artist.getId()));
                }
        );
    }

    public synchronized void deleteArtist(Long id) throws IllegalArgumentException {
        findArtist(id).ifPresentOrElse(
                original -> {
                    deleteAllArtistsAlbums(id);
                    artists.remove(original);
                    },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The artist with id \"%d\" does not exist", id));
                });
    }

    /**
     * Album entity functions
     */

    public synchronized List<Album> findAllArtistAlbum(Long id) {
        return albums.stream()
                .filter(album -> album.getArtist().getId().equals(id))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Album> findAlbum(Long id) {
        return albums.stream()
                .filter(album -> album.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createAlbum(Album album) throws IllegalArgumentException {
        findAlbum(album.getId()).ifPresentOrElse(original -> {
                    throw new IllegalArgumentException(
                            String.format("The album id \"%s\" is not unique", album.getId()));
                },
                () -> albums.add(album));
    }

    public synchronized void updateAlbum(Album album) throws IllegalArgumentException {
        findAlbum(album.getId()).ifPresentOrElse(
                original -> {
                    albums.remove(original);
                    albums.add(album);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The album with id \"%d\" does not exist", album.getId()));
                }
        );
    }

    public synchronized void deleteAlbum(Long id) throws IllegalArgumentException {
        findAlbum(id).ifPresentOrElse(
                original -> albums.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The album with id \"%d\" does not exist", id));
                });
    }

    public synchronized void deleteAllArtistsAlbums(Long id) {
        List<Album> albumsToDelete = findAllArtistAlbum(id);
        for(Album album: albumsToDelete) {
            albums.remove(album);
        }
    }
}

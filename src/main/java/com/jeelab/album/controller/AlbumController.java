package com.jeelab.album.controller;

import com.jeelab.album.dto.GetArtistAlbumResponse;
import com.jeelab.album.dto.GetArtistAlbumsResponse;
import com.jeelab.album.dto.PostAlbumRequest;
import com.jeelab.album.dto.PutAlbumRequest;
import com.jeelab.album.entity.Album;
import com.jeelab.album.service.AlbumService;
import com.jeelab.artist.dto.GetArtistResponse;
import com.jeelab.artist.dto.PutArtistRequest;
import com.jeelab.artist.entity.Artist;
import com.jeelab.artist.service.ArtistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("artists")
public class AlbumController {

    private ArtistService artistService;
    private AlbumService albumService;

    public AlbumController() {}

    @Inject
    public void setArtistService(ArtistService service) { this.artistService = service; }

    @Inject
    public void setAlbumService(AlbumService service) { this.albumService = service; }

    @GET
    @Path("{artistId}/albums")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtistAlbums(@PathParam("artistId") Long artistId) {
        Optional<Artist> artist = artistService.find(artistId);
        if(artist.isPresent()) {
            return Response.ok(GetArtistAlbumsResponse.entityToDtoMapper()
                    .apply(albumService.findAllArtistAlbum(artistId))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Artist with id \"%d\" not found", artistId)).build();
        }
    }

    @GET
    @Path("{artistId}/albums/{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtistAlbum(@PathParam("artistId") Long artistId, @PathParam("albumId") Long albumId) {
        Response response = validateArtistAlbum(artistId, albumId);
        if(response != null)
            return response;
        Optional<Album> album = albumService.find(albumId);
        return Response.ok(GetArtistAlbumResponse.entityToDtoMapper()
                .apply(album.get())).build();
    }

    @POST
    @Path("{artistId}/albums")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postAlbum(@PathParam("artistId") Long artistId, PostAlbumRequest request) {
        Optional<Artist> artist = artistService.find(artistId);
        if(artist.isEmpty())
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Artist with id \"%d\" not found", artistId)).build();
        Long createdAlbumId = albumService.create(PostAlbumRequest
                .dtoToEntityMapper(artist)
                .apply(request)).getId();
        return Response.created(UriBuilder.fromMethod(AlbumController.class, "getArtistAlbums")
                .build(artistId, createdAlbumId)).build();
    }

    @DELETE
    @Path("{artistId}/albums/{albumId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlbum(@PathParam("artistId") Long artistId, @PathParam("albumId") Long albumId) {
        Response response = validateArtistAlbum(artistId, albumId);
        if(response != null)
            return response;
        albumService.delete(albumId);
        return Response.ok().build();
    }

    @PUT
    @Path("{artistId}/albums/{albumId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putAlbum(@PathParam("artistId") Long artistId, @PathParam("albumId") Long albumId, PutAlbumRequest putAlbumRequest) {
        Response response = validateArtistAlbum(artistId, albumId);
        if(response != null)
            return response;
        Optional<Album> album = albumService.find(albumId);
        PutAlbumRequest.dtoToEntityUpdater()
                .apply(album.get(), putAlbumRequest);
        albumService.update(album.get());
        return Response.ok().build();
    }

    private Response validateArtistAlbum(Long artistId, Long albumId) {
        Optional<Artist> artist = artistService.find(artistId);
        if(artist.isEmpty())
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Artist with id \"%d\" not found", artistId)).build();
        Optional<Album> album = albumService.find(albumId);
        if(album.isEmpty())
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Album with id \"%d\" not found", albumId)).build();
        if(!artistId.equals(album.get().getArtist().getId()))
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(String.format("Artist (id: %d) does not own this Album (id: %d)", artistId, albumId)).build();
        return null;
    }

}



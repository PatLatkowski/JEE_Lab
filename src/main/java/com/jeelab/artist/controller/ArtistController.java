package com.jeelab.artist.controller;

import com.jeelab.artist.dto.GetArtistResponse;
import com.jeelab.artist.dto.GetArtistsResponse;
import com.jeelab.artist.dto.PostArtistRequest;
import com.jeelab.artist.dto.PutArtistRequest;
import com.jeelab.artist.entity.Artist;
import com.jeelab.artist.service.ArtistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Path("/artists")
public class ArtistController {
    private ArtistService artistService;

    public ArtistController() {
    }

    @Inject
    public void setArtistService(ArtistService artistService) { this.artistService = artistService; }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtists() {
        return Response.ok(GetArtistsResponse.entityToDtoMapper().apply(artistService.findAll())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtist(@PathParam("id") Long id) {
        Optional<Artist> artist = artistService.find(id);
        if(artist.isPresent()) {
            return Response.ok(GetArtistResponse.entityToDtoMapper()
                    .apply(artist.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postArtist(PostArtistRequest request) {
        Long createdArtistId = artistService.create(PostArtistRequest.dtoToEntityMapper().apply(request)).getId();
        return Response.created(UriBuilder.fromMethod(ArtistController.class, "getArtist")
            .build(createdArtistId)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteArtist(@PathParam("id") Long id) {
        Optional<Artist> artist = artistService.find(id);
        if(artist.isPresent()) {
            artistService.delete(id);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putArtist(@PathParam("id") Long id, PutArtistRequest putArtistRequest) {
        Optional<Artist> artist = artistService.find(id);
        if(artist.isPresent()) {
            PutArtistRequest.dtoToEntityUpdater().apply(artist.get(), putArtistRequest);
            artistService.update(artist.get());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

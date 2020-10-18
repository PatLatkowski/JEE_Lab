package com.jeelab.recordcompany.servlet;

import com.jeelab.recordcompany.entity.RecordCompany;
import com.jeelab.recordcompany.service.RecordCompanyService;
import com.jeelab.servlet.ServletUtility;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@WebServlet(urlPatterns = RecordCompanyAvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class RecordCompanyAvatarServlet extends HttpServlet {

    private RecordCompanyService service;
    private String directory;

    @Inject
    public RecordCompanyAvatarServlet(RecordCompanyService service) {
        this.service = service;
        this.directory = "D:/_Projekty/JEELabImages";
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9]+/?$";
    }

    public static class Param {
        public static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.AVATARS.equals(servletPath)) {
            if(path.matches(Patterns.AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);
        Path path = java.nio.file.Paths.get(directory + "/" + id + ".png");
        if(recordCompany.isPresent() && Files.exists(path)) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            response.setContentLength(Files.readAllBytes(path).length);
            response.getOutputStream().write(Files.readAllBytes(path));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);
        Path path = java.nio.file.Paths.get(directory + "/" + id + ".png");
        if(recordCompany.isPresent()){
            Part avatar = request.getPart(Param.AVATAR);
            if(avatar != null) {
                if(!Files.exists(path) && avatar.getInputStream().readAllBytes().length > 0) {
                    service.postAvatar(path, avatar.getInputStream());
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);
        Path path = java.nio.file.Paths.get(directory + "/" + id + ".png");
        if(recordCompany.isPresent()){
            Part avatar = request.getPart(Param.AVATAR);
            if(avatar != null) {
                if(Files.exists(path) && avatar.getInputStream().readAllBytes().length > 0) {
                    service.updateAvatar(path, avatar.getInputStream());
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);
        Path path = java.nio.file.Paths.get(directory + "/" + id + ".png");
        if(recordCompany.isPresent() && Files.exists(path)){
            service.deleteAvatar(path);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

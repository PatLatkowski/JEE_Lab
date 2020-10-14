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
import java.util.Optional;

@WebServlet(urlPatterns = RecordCompanyAvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class RecordCompanyAvatarServlet extends HttpServlet {

    private RecordCompanyService service;

    @Inject
    public RecordCompanyAvatarServlet(RecordCompanyService service) { this.service = service; }

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
        if(recordCompany.isPresent() && recordCompany.get().getAvatar() != null) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            response.setContentLength(recordCompany.get().getAvatar().length);
            response.getOutputStream().write(recordCompany.get().getAvatar());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);
        if(recordCompany.isPresent()){
            Part avatar = request.getPart(Param.AVATAR);
            if(avatar != null) {
                if(recordCompany.get().getAvatar() == null) {
                    service.updateAvatar(id, avatar.getInputStream());
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
        if(recordCompany.isPresent()){
            Part avatar = request.getPart(Param.AVATAR);
            if(avatar != null) {
                service.updateAvatar(id, avatar.getInputStream());
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
        if(recordCompany.isPresent() && recordCompany.get().getAvatar() != null){
            service.deleteAvatar(id);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

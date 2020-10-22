package com.jeelab.recordcompany.servlet;

import com.jeelab.recordcompany.dto.GetRecordCompaniesResponse;

import com.jeelab.recordcompany.dto.GetRecordCompanyResponse;
import com.jeelab.recordcompany.entity.RecordCompany;
import com.jeelab.recordcompany.service.RecordCompanyService;
import com.jeelab.servlet.ServletUtility;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = RecordCompanyServlet.Paths.RECORD_COMPANY + "/*")
public class RecordCompanyServlet extends HttpServlet {

    private final RecordCompanyService service;

    @Inject
    public RecordCompanyServlet(RecordCompanyService service) {
        this.service = service;
    }

    public static class Paths {
        public static final String RECORD_COMPANY = "/api/recordCompany";
    }

    public static class Patterns {

        public static final String COMPANIES = "^/?$";

        public static final String COMPANY = "^/[0-9]+/?$";

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (path.matches(Patterns.COMPANY)) {
            getCompany(request, response);
            return;
        } else if (path.matches(Patterns.COMPANIES)) {
            getCompanies(response);
            return;
        }
        response.getWriter().write("{}");
    }

    private void getCompanies(HttpServletResponse response) throws IOException {
        response.getWriter().write(jsonb.toJson(GetRecordCompaniesResponse.entityToDtoMapper().apply(service.findAll())));
    }

    private void getCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<RecordCompany> recordCompany = service.find(id);

        if (recordCompany.isPresent()) {
            response.getWriter()
                    .write(jsonb.toJson(GetRecordCompanyResponse.entityToDtoMapper().apply(recordCompany.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

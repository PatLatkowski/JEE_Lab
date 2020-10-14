package com.jeelab.recordcompany.service;

import com.jeelab.recordcompany.entity.RecordCompany;
import lombok.NoArgsConstructor;
import com.jeelab.recordcompany.repository.RecordCompanyRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class RecordCompanyService {

    private RecordCompanyRepository repository;

    @Inject
    public RecordCompanyService(RecordCompanyRepository repository) { this.repository = repository; }

    public Optional<RecordCompany> find(Long id) { return repository.find(id); }

    public List<RecordCompany> findAll() { return repository.findAll(); }

    public void create(RecordCompany recordCompany) { repository.create(recordCompany); }

    public void postAvatar(Path path, InputStream inputStream) throws IOException {
        Files.write(path, inputStream.readAllBytes());
    }

    public void updateAvatar(Path path, InputStream inputStream) throws IOException {
        Files.delete(path);
        Files.write(path, inputStream.readAllBytes());
    }

    public void deleteAvatar(Path path) throws IOException {
        Files.delete(path);
    }
}

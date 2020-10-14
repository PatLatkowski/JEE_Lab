package com.jeelab.recordcompany.service;

import com.jeelab.recordcompany.entity.RecordCompany;
import lombok.NoArgsConstructor;
import com.jeelab.recordcompany.repository.RecordCompanyRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
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

    public void updateAvatar(Long id, InputStream is) {
        repository.find(id).ifPresent(recordCompany -> {
            try {
                recordCompany.setAvatar(is.readAllBytes());
                repository.update(recordCompany);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(Long id) {
        repository.find(id).ifPresent(recordCompany -> {
            recordCompany.setAvatar(null);
            repository.update(recordCompany);
        });
    }
}

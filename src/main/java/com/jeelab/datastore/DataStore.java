package com.jeelab.datastore;

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
}

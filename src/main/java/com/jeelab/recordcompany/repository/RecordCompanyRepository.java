package com.jeelab.recordcompany.repository;

import com.jeelab.recordcompany.entity.RecordCompany;
import com.jeelab.datastore.DataStore;
import com.jeelab.repository.Repository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class RecordCompanyRepository implements Repository<RecordCompany, Long> {

    private DataStore store;

    @Inject
    public RecordCompanyRepository(DataStore store) { this.store = store; }

    @Override
    public Optional<RecordCompany> find(Long id) { return store.findRecordCompany(id); }

    @Override
    public List<RecordCompany> findAll() { return store.findAllRecordCompanies(); }

    @Override
    public void create(RecordCompany recordCompany) { store.createRecordCompany(recordCompany); }

    @Override
    public void delete(RecordCompany recordCompany) { throw new UnsupportedOperationException("Not implemented."); }

    @Override
    public void update(RecordCompany recordCompany) { store.updateRecordCompany(recordCompany); }

    @Override
    public void detach(RecordCompany recordCompany) { throw new UnsupportedOperationException("Not implemented."); }

}

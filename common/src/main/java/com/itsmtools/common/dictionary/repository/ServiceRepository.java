package com.itsmtools.common.dictionary.repository;


import com.itsmtools.common.dictionary.model.Service;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


@Repository
public class ServiceRepository extends AbstractRepository<Service, Integer, String> {

    @Autowired
    private Session session;

    @Override
    public Service get(Integer id) {
        return null;
    }

    @Override
    public void create(Service input) {
        session.save(input);
        session.flush();
    }

    @Override
    public void update(Service input) {
        Service service = (Service) session.get(Service.class, input.getId());
        service.setTitle(input.getTitle());
        service.setDescription(input.getDescription());
        session.save(service);
        session.flush();
    }

    @Override
    public void delete(Integer id) {}

    @Override
    @SuppressWarnings("unchecked")
    public List<Service> list(Map<String, String> filter) {
        return session.createCriteria(Service.class).list();
    }
}
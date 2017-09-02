package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseService<S extends CrudRepository<E, Long>, E> {

    protected abstract S getRepository();


    public List<E> getList() {
        Iterable<E> iterator = getRepository().findAll();
        List<E> list = new ArrayList<>();
        iterator.forEach(list::add);
        return list;
    }

    public E getOne(long id) {
        return getRepository().findOne(id);
    }

    public ResponseEntity save(E entity) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        getRepository().save(entity);
        return ResponseEntity.ok().build();
    }

    public void delete(long id) {
        getRepository().delete(id);
    }
}

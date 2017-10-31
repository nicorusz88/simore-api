package ar.com.simore.simoreapi.services;

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

    public <T> ResponseEntity save(E entity){
        getRepository().save(entity);
        return ResponseEntity.ok().build();
    }

    public void delete(long id) {
        getRepository().delete(id);
    }
}

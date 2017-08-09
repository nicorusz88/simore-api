package ar.com.simore.simoreapi.services;

import org.springframework.data.repository.CrudRepository;

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

    public void save(E entity) {
        getRepository().save(entity);
    }

    public void delete(long id) {
        getRepository().delete(id);
    }
}

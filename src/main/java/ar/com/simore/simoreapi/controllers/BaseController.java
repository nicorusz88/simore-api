package ar.com.simore.simoreapi.controllers;

import ar.com.simore.simoreapi.entities.BaseEntity;
import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import ar.com.simore.simoreapi.services.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Base controller with predefines common methods:
 * Get List
 * GET /{domain}
 * <p>
 * Get One:
 * GET /{domain}/{id}
 * <p>
 * Add One:
 * POST /{domain}
 * <p>
 * Update One:
 * PUT /{domain}/{id}
 * <p>
 * Delete One:
 * DELETE /{domain}/{id}
 *
 * @param <S>
 * @param <E>
 */
public abstract class BaseController<S extends BaseService, E> {


    abstract S getService();

    @GetMapping
    public ResponseEntity<List> getList() {
        return new ResponseEntity<>(getService().getList(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<E> getOne(@PathVariable long id) {
        return new ResponseEntity<>((E) getService().getOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<E> add(@Valid @RequestBody E entity) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        getService().save(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> update(@PathVariable long id, @Valid @RequestBody E entity) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        if (!StringUtils.isEmpty(id)) {
            ((BaseEntity) entity).setId(id);
            getService().save(entity);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        if (!StringUtils.isEmpty(id)) {
            getService().delete(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        }
    }

}

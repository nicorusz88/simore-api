package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.TreatmentTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentTemplateRepository extends CrudRepository<TreatmentTemplate, Long> {

}

package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.ChoiceQuestionOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceQuestionOptionRepository extends CrudRepository<ChoiceQuestionOption, Long> {

}

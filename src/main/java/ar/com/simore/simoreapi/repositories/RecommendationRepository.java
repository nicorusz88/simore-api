package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Recommendation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {

}

package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Recommendation;
import ar.com.simore.simoreapi.repositories.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService extends BaseService<RecommendationRepository, Recommendation> {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    protected RecommendationRepository getRepository() {
        return recommendationRepository;
    }

}

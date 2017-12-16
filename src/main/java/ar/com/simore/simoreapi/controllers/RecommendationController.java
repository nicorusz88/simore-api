package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Recommendation;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.resources.RecommendationResource;
import ar.com.simore.simoreapi.services.RecommendationService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController extends BaseController<RecommendationService, Recommendation> {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private RecommendationService recommendationService;

    @Override
    RecommendationService getService() {
        return recommendationService;
    }


    /**
     * Method to add a recommendation to an existing treatment
     *
     * @param recommendation Recommendation to add to the treatment
     * @param treatmentId    ID of the tratment to add the recommendation
     * @return
     */
    @PostMapping("/add-to-treatment")
    public ResponseEntity<Treatment> addRecommendationToTreatment(@RequestParam Long treatmentId, @Valid @RequestBody Recommendation recommendation) {
        return treatmentService.addTreatmentComponentToTreatment(recommendation, treatmentId);
    }


    /**
     * Gets the 10 last recommendations
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RecommendationResource>> getRecommendationsByUserId(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(recommendationService.getByUserId(userId));
    }
}

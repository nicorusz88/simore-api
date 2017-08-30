package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recommendation_status")
public class RecommendationStatus extends Doable {

    @ManyToOne
    private Recommendation recommendation;

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }
}

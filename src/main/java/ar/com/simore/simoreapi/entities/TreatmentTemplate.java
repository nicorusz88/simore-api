package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class TreatmentTemplate extends BaseEntity {

    @ManyToMany
    private List<Vital> vitals;

    @ManyToMany
    private List<Medication> medications;

    @ManyToMany
    private List<Recommendation> recommendations;

    @ManyToMany
    private List<CheckIn> checkIns;


}

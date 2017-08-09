package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.utils.VitalTypesEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "vital")
public class Vital extends BaseTreatmentComponent {

    @Enumerated(EnumType.STRING)
    private VitalTypesEnum type;


    public VitalTypesEnum getType() {
        return type;
    }

    public void setType(VitalTypesEnum type) {
        this.type = type;
    }
}

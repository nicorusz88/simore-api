package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.utils.VitalsEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "vital")
public class Vital extends BaseTreatmentComponent {

    @Enumerated(EnumType.STRING)
    private VitalsEnum type;

    public VitalsEnum getType() {
        return type;
    }

    public void setType(VitalsEnum type) {
        this.type = type;
    }

}

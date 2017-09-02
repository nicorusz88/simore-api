package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.utils.VitalsEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vital")
public class Vital extends BaseTreatmentComponent {

    @NotNull
    @Enumerated(EnumType.STRING)
    private VitalsEnum type;

    public VitalsEnum getType() {
        return type;
    }

    public void setType(VitalsEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vital) {
            if (this.getType().name().equals(((Vital) obj).getType().name())) {
                return true;
            }
        }
        return false;
    }
}

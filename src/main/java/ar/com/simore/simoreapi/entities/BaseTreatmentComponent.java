package ar.com.simore.simoreapi.entities;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Table(name = "base_treatment_component")
public class BaseTreatmentComponent extends BaseEntity{

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "medical_insurance")
public class MedicalInsurance extends BaseEntity {
    @Size(max = 50)
    private String name;
    @Size(max = 150)
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

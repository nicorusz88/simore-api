package ar.com.simore.simoreapi.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @NotBlank(message = "El nombre de usuario no puede estar vacio.")
    @Size(max = 20)
    private String userName;
    private String password;

    @NotBlank(message = "El nombre no puede estar vacio.")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacio.")
    @Size(max = 50)
    private String lastName;

    @Size(max = 20)
    private String gender;

    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private MedicalInsurance medicalInsurance;
    @Size(max = 10)
    private String bloodType;

    private float height;
    private float weight;

    @Size(max = 50)
    private String phone;
    @Size(max = 300)
    private String avatar;

    @Email(message = "El email es inválido")
    @Size(max = 200)
    private String email;

    @Size(max = 400)
    private String allergies;

    @OneToOne(cascade = CascadeType.ALL)
    private Treatment treatment;

    private boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<Role> roles;

    @ManyToOne
    private User professional;

    /**
     * Wearable API authorizations
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OAuth> oauths;

    public List<OAuth> getOauths() {
        return oauths;
    }

    public void setOauths(List<OAuth> oauths) {
        this.oauths = oauths;
    }

    public User getProfessional() {
        return professional;
    }

    public void setProfessional(User professional) {
        this.professional = professional;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public MedicalInsurance getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(MedicalInsurance medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}

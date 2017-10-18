package ar.com.simore.simoreapi;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.entities.enums.VitalsEnum;
import ar.com.simore.simoreapi.entities.enums.WearableTypeEnum;
import ar.com.simore.simoreapi.entities.enums.YesNoOptionEnum;
import ar.com.simore.simoreapi.repositories.RoleRepository;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Bootstrap {
    private Logger logger = Logger.getLogger(Bootstrap.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TreatmentTemplateRepository treatmentTemplateRepository;

    @PostConstruct
    public void init() {

        try {
            initTreatmentsTemplates();
            initRoles();
            initUsers();
        } catch (Exception e) {
            logger.error("ERROR initializing data: " + e.getMessage());
        }
    }


    private void initRoles() {
        final long count = roleRepository.count();
        if (count==0) {
            logger.warn("ATENTION: No roles, creating them...");
            Role role1 = new Role();
            role1.setName(RolesNamesEnum.ADMINISTRATOR.name());
            roleRepository.save(role1);
            Role role2 = new Role();
            role2.setName(RolesNamesEnum.PATIENT.name());
            roleRepository.save(role2);
            Role role3 = new Role();
            role3.setName(RolesNamesEnum.PROFESSIONAL.name());
            roleRepository.save(role3);
        }

    }

    private void initUsers() {
        
        List<User> administrators = userRepository.findByRoles_Name(RolesNamesEnum.ADMINISTRATOR.name());
        List<User> professionals = userRepository.findByRoles_Name(RolesNamesEnum.PROFESSIONAL.name());

        if (administrators.isEmpty()) {
            logger.warn("ATENTION: No administrators, creating [user/pass]: admin/admin");

            User user1 = new User();
            user1.setUserName("admin");
            user1.setFirstName("admin");
            user1.setLastName("admin");
            user1.setPassword("admin");
            user1.setGender("Male");
            user1.setDeleted(false);
            Role role1 = roleRepository.findByName(RolesNamesEnum.ADMINISTRATOR.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            user1.setRoles(roles);
            userRepository.save(user1);
        }

        if (professionals.isEmpty()) {
            logger.warn("ATENTION: No professionals, creating [user/pass]: prof/prof");

            User user1 = new User();
            user1.setUserName("prof");
            user1.setFirstName("prof");
            user1.setLastName("prof");
            user1.setPassword("prof");
            user1.setGender("Male");
            user1.setDeleted(false);
            Role role1 = roleRepository.findByName(RolesNamesEnum.PROFESSIONAL.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            user1.setRoles(roles);
            userRepository.save(user1);

        }
    }


    private void initTreatmentsTemplates() {

        final long count = treatmentTemplateRepository.count();
        if (count==0) {
            logger.warn("ATENTION: No Treatment Templates, creating Treatment templates for Cardiac Disease, Diabeted Type I and Hypertension");
            TreatmentTemplate treatmentTemplate = generateTreatmentTemplateCardiacDisease();
            treatmentTemplateRepository.save(treatmentTemplate);
            TreatmentTemplate treatmentTemplate2 = generateTreatmentTemplateDiabetesType1();
            treatmentTemplateRepository.save(treatmentTemplate2);
            TreatmentTemplate treatmentTemplate3 = generateTreatmentTemplateHypertension();
            treatmentTemplateRepository.save(treatmentTemplate3);
        }

    }


    private TreatmentTemplate generateTreatmentTemplateCardiacDisease() {
        TreatmentTemplate treatmentTemplate = new TreatmentTemplate();
        treatmentTemplate.setName("Cardiovascular Disease");

        //Vitales
        List<Vital> vitales = generateVitalsCardiovascularDisease();
        treatmentTemplate.setVitals(vitales);

        //Medications
        List<Medication> medications = getMedicationsCardiovascularDisease();
        treatmentTemplate.setMedications(medications);

        //Checkins
        List<CheckIn> checkins = getCheckInsCardiovascularDisease();
        treatmentTemplate.setCheckIns(checkins);

        //Appointments
        List<Appointment> appointments = getAppointmentsCardiovascularDisease();
        treatmentTemplate.setAppointments(appointments);

        return treatmentTemplate;
    }

    private List<CheckIn> getCheckInsCardiovascularDisease() {
        List<CheckIn> checkins = new ArrayList<>();
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setName("Pains");
        checkIn1.setFrecuency(12);
        ChoiceQuestion question1 = new ChoiceQuestion();
        question1.setQuestion("¿Have you felt any chest pain in the last hours?");
        List<ChoiceQuestionOption> choiceQuestionOptions1 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption1 = new ChoiceQuestionOption();
        choiceQuestionOption1.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption2 = new ChoiceQuestionOption();
        choiceQuestionOption2.setOption(YesNoOptionEnum.NO.getText());
        choiceQuestionOptions1.add(choiceQuestionOption1);
        choiceQuestionOptions1.add(choiceQuestionOption2);
        question1.setChoiceQuestionOptions(choiceQuestionOptions1);
        checkIn1.setQuestion(question1);

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setName("Dificulties");
        checkIn2.setFrecuency(12);
        ChoiceQuestion question2 = new ChoiceQuestion();
        question2.setQuestion("¿Have you had any difficulty to breath in the last hours?");
        List<ChoiceQuestionOption> choiceQuestionOptions2 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption3 = new ChoiceQuestionOption();
        choiceQuestionOption3.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption4 = new ChoiceQuestionOption();
        choiceQuestionOption4.setOption(YesNoOptionEnum.NO.getText());
        choiceQuestionOptions2.add(choiceQuestionOption3);
        choiceQuestionOptions2.add(choiceQuestionOption4);
        question2.setChoiceQuestionOptions(choiceQuestionOptions2);
        checkIn2.setQuestion(question2);

        checkins.add(checkIn1);
        checkins.add(checkIn2);
        return checkins;
    }

    private List<Medication> getMedicationsCardiovascularDisease() {
        List<Medication> medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setName("Aspirin");
        medication1.setQuantity(20);
        medication1.setFrecuency(6);

        Medication medication2 = new Medication();
        medication2.setName("Atorvastatin");
        medication2.setQuantity(20);
        medication2.setFrecuency(24);

        medications.add(medication1);
        medications.add(medication2);
        return medications;
    }

    private List<Vital> generateVitalsCardiovascularDisease() {
        List<Vital> vitales = new ArrayList<>();
        Vital vital1 = new Vital();
        vital1.setType(VitalsEnum.HEART_RATE);
        vital1.setName(VitalsEnum.HEART_RATE.getName());
        vital1.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital2 = new Vital();
        vital2.setType(VitalsEnum.BLOOD_OXYGEN);
        vital2.setName(VitalsEnum.BLOOD_OXYGEN.getName());
        vital2.setWearableType(WearableTypeEnum.WITHINGS);

        Vital vital3 = new Vital();
        vital3.setType(VitalsEnum.BLOOD_PRESSURE);
        vital3.setName(VitalsEnum.BLOOD_PRESSURE.getName());
        vital3.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital4 = new Vital();
        vital4.setType(VitalsEnum.BURNT_CALORIES);
        vital4.setName(VitalsEnum.BURNT_CALORIES.getName());
        vital4.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital5 = new Vital();
        vital5.setType(VitalsEnum.WEIGHT);
        vital5.setName(VitalsEnum.WEIGHT.getName());
        vital5.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital6 = new Vital();
        vital6.setType(VitalsEnum.STEPS);
        vital6.setName(VitalsEnum.STEPS.getName());
        vital6.setWearableType(WearableTypeEnum.FITBIT);

        vitales.add(vital1);
        vitales.add(vital2);
        vitales.add(vital3);
        vitales.add(vital4);
        vitales.add(vital5);
        vitales.add(vital6);
        return vitales;
    }

    private List<Appointment> getAppointmentsCardiovascularDisease() {
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setName("Hospital");
        appointment1.setAddress("Av. Hilbert 1203, Minnetonka, Minnesota");
        appointment1.setDoctor("Dr. Foubert");
        appointment1.setDate(new Date());

        appointments.add(appointment1);
        return appointments;
    }

    private TreatmentTemplate generateTreatmentTemplateDiabetesType1() {
        TreatmentTemplate treatmentTemplate = new TreatmentTemplate();
        treatmentTemplate.setName("Diabetes Type 1");
        //Vitales
        List<Vital> vitales = generateVitalsDiabetesType1();
        treatmentTemplate.setVitals(vitales);

        //Medications
        List<Medication> medications = getMedicationsDiabetesType1();
        treatmentTemplate.setMedications(medications);

        //Checkins
        List<CheckIn> checkins = getCheckInsDiabetesType1();
        treatmentTemplate.setCheckIns(checkins);

        //Appointments
        List<Appointment> appointments = getAppointmentsDiabetesType1();
        treatmentTemplate.setAppointments(appointments);
        return treatmentTemplate;
    }

    private List<Vital> generateVitalsDiabetesType1() {
        List<Vital> vitales = new ArrayList<>();
        Vital vital1 = new Vital();
        vital1.setType(VitalsEnum.STEPS);
        vital1.setName(VitalsEnum.STEPS.getName());
        vital1.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital2 = new Vital();
        vital2.setType(VitalsEnum.WEIGHT);
        vital2.setName(VitalsEnum.WEIGHT.getName());
        vital2.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital3 = new Vital();
        vital3.setType(VitalsEnum.DISTANCE);
        vital3.setName(VitalsEnum.DISTANCE.getName());
        vital3.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital4 = new Vital();
        vital4.setType(VitalsEnum.BURNT_CALORIES);
        vital4.setName(VitalsEnum.BURNT_CALORIES.getName());
        vital4.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital5 = new Vital();
        vital5.setType(VitalsEnum.BLOOD_PRESSURE);
        vital5.setName(VitalsEnum.BLOOD_PRESSURE.getName());
        vital5.setWearableType(WearableTypeEnum.FITBIT);

        vitales.add(vital1);
        vitales.add(vital2);
        vitales.add(vital3);
        vitales.add(vital4);
        vitales.add(vital5);
        return vitales;
    }

    private List<Medication> getMedicationsDiabetesType1() {
        List<Medication> medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setName("Long Acting Insulin");
        medication1.setQuantity(20);
        medication1.setFrecuency(24);

        Medication medication2 = new Medication();
        medication2.setName("Benazepril");
        medication2.setQuantity(20);
        medication2.setFrecuency(48);

        medications.add(medication1);
        medications.add(medication2);
        return medications;
    }

    private List<CheckIn> getCheckInsDiabetesType1() {
        List<CheckIn> checkins = new ArrayList<>();
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setName("Diet");
        checkIn1.setFrecuency(12);
        ChoiceQuestion question1 = new ChoiceQuestion();
        question1.setQuestion("¿Are you following yoour recommended diet?");
        List<ChoiceQuestionOption> choiceQuestionOptions1 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption1 = new ChoiceQuestionOption();
        choiceQuestionOption1.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption2 = new ChoiceQuestionOption();
        choiceQuestionOption2.setOption(YesNoOptionEnum.NO.getText());
        choiceQuestionOptions1.add(choiceQuestionOption1);
        choiceQuestionOptions1.add(choiceQuestionOption2);
        question1.setChoiceQuestionOptions(choiceQuestionOptions1);
        checkIn1.setQuestion(question1);

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setName("Urination");
        checkIn2.setFrecuency(6);
        ChoiceQuestion question2 = new ChoiceQuestion();
        question2.setQuestion("¿How many times did you go to urinate in the last 6 hours?");
        List<ChoiceQuestionOption> choiceQuestionOptions2 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption3 = new ChoiceQuestionOption();
        choiceQuestionOption3.setOption("Between 1 and 3");
        ChoiceQuestionOption choiceQuestionOption4 = new ChoiceQuestionOption();
        choiceQuestionOption4.setOption("Between 3 and 5");
        ChoiceQuestionOption choiceQuestionOption5 = new ChoiceQuestionOption();
        choiceQuestionOption5.setOption("More than 5");
        choiceQuestionOptions2.add(choiceQuestionOption3);
        choiceQuestionOptions2.add(choiceQuestionOption4);
        choiceQuestionOptions2.add(choiceQuestionOption5);
        question2.setChoiceQuestionOptions(choiceQuestionOptions2);
        checkIn2.setQuestion(question2);

        CheckIn checkIn3 = new CheckIn();
        checkIn3.setName("Thirst");
        checkIn3.setFrecuency(6);
        ChoiceQuestion question3 = new ChoiceQuestion();
        question3.setQuestion("¿How you feel an increased thirst in the last 6 hours?");
        List<ChoiceQuestionOption> choiceQuestionOptions3 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption6 = new ChoiceQuestionOption();
        choiceQuestionOption6.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption7 = new ChoiceQuestionOption();
        choiceQuestionOption7.setOption(YesNoOptionEnum.NO.getText());

        choiceQuestionOptions3.add(choiceQuestionOption6);
        choiceQuestionOptions3.add(choiceQuestionOption7);
        question3.setChoiceQuestionOptions(choiceQuestionOptions3);
        checkIn3.setQuestion(question3);

        checkins.add(checkIn1);
        checkins.add(checkIn2);
        checkins.add(checkIn3);
        return checkins;
    }


    private List<Appointment> getAppointmentsDiabetesType1() {
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setName("Hospital");
        appointment1.setAddress("Av. Hilbert 1203, Minnetonka, Minnesota");
        appointment1.setDoctor("Dr. Hoffman");
        appointment1.setDate(new Date());

        appointments.add(appointment1);
        return appointments;
    }

    private TreatmentTemplate generateTreatmentTemplateHypertension() {
        TreatmentTemplate treatmentTemplate = new TreatmentTemplate();
        treatmentTemplate.setName("Hypertension");
        //Vitales
        List<Vital> vitales = generateVitalsHypertension();
        treatmentTemplate.setVitals(vitales);

        //Medications
        List<Medication> medications = getMedicationsHypertension();
        treatmentTemplate.setMedications(medications);

        //Checkins
        List<CheckIn> checkins = getCheckInsHypertension();
        treatmentTemplate.setCheckIns(checkins);

        //Appointments
        List<Appointment> appointments = getAppointmentsHypertension();
        treatmentTemplate.setAppointments(appointments);
        return treatmentTemplate;
    }

    private List<Vital> generateVitalsHypertension() {
        List<Vital> vitales = new ArrayList<>();
        Vital vital1 = new Vital();
        vital1.setType(VitalsEnum.STEPS);
        vital1.setName(VitalsEnum.STEPS.getName());
        vital1.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital2 = new Vital();
        vital2.setType(VitalsEnum.WEIGHT);
        vital2.setName(VitalsEnum.WEIGHT.getName());
        vital2.setWearableType(WearableTypeEnum.FITBIT);


        Vital vital3 = new Vital();
        vital3.setType(VitalsEnum.DISTANCE);
        vital3.setName(VitalsEnum.DISTANCE.getName());
        vital3.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital4 = new Vital();
        vital4.setType(VitalsEnum.BURNT_CALORIES);
        vital4.setName(VitalsEnum.BURNT_CALORIES.getName());
        vital4.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital5 = new Vital();
        vital5.setType(VitalsEnum.BLOOD_PRESSURE);
        vital5.setName(VitalsEnum.BLOOD_PRESSURE.getName());
        vital5.setWearableType(WearableTypeEnum.FITBIT);

        Vital vital6 = new Vital();
        vital6.setType(VitalsEnum.HEART_RATE);
        vital6.setName(VitalsEnum.HEART_RATE.getName());
        vital6.setWearableType(WearableTypeEnum.FITBIT);

        vitales.add(vital1);
        vitales.add(vital2);
        vitales.add(vital3);
        vitales.add(vital4);
        vitales.add(vital5);
        vitales.add(vital6);

        return vitales;
    }

    private List<Medication> getMedicationsHypertension() {
        List<Medication> medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setName("Lozol");
        medication1.setQuantity(20);
        medication1.setFrecuency(24);

        Medication medication2 = new Medication();
        medication2.setName("Afeditab CR");
        medication2.setQuantity(20);
        medication2.setFrecuency(48);

        medications.add(medication1);
        medications.add(medication2);
        return medications;
    }

    private List<CheckIn> getCheckInsHypertension() {
        List<CheckIn> checkins = new ArrayList<>();
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setName("Headache");
        checkIn1.setFrecuency(12);
        ChoiceQuestion question1 = new ChoiceQuestion();
        question1.setQuestion("¿Did you have any headache lately?");
        List<ChoiceQuestionOption> choiceQuestionOptions1 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption1 = new ChoiceQuestionOption();
        choiceQuestionOption1.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption2 = new ChoiceQuestionOption();
        choiceQuestionOption2.setOption(YesNoOptionEnum.NO.getText());
        choiceQuestionOptions1.add(choiceQuestionOption1);
        choiceQuestionOptions1.add(choiceQuestionOption2);
        question1.setChoiceQuestionOptions(choiceQuestionOptions1);
        checkIn1.setQuestion(question1);

        CheckIn checkIn2 = new CheckIn();
        checkIn2.setName("Vision");
        checkIn2.setFrecuency(6);
        ChoiceQuestion question2 = new ChoiceQuestion();
        question2.setQuestion("¿Have you felt altered vision(example blurring)?");
        List<ChoiceQuestionOption> choiceQuestionOptions2 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption3 = new ChoiceQuestionOption();
        choiceQuestionOption3.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption4 = new ChoiceQuestionOption();
        choiceQuestionOption4.setOption(YesNoOptionEnum.NO.getText());
        choiceQuestionOptions2.add(choiceQuestionOption3);
        choiceQuestionOptions2.add(choiceQuestionOption4);
        question2.setChoiceQuestionOptions(choiceQuestionOptions2);
        checkIn2.setQuestion(question2);

        CheckIn checkIn3 = new CheckIn();
        checkIn3.setName("Thirst");
        checkIn3.setFrecuency(6);
        ChoiceQuestion question3 = new ChoiceQuestion();
        question3.setQuestion("¿How you feel an increased thirst in the last 6 hours?");
        List<ChoiceQuestionOption> choiceQuestionOptions3 = new ArrayList<>();
        ChoiceQuestionOption choiceQuestionOption6 = new ChoiceQuestionOption();
        choiceQuestionOption6.setOption(YesNoOptionEnum.YES.getText());
        ChoiceQuestionOption choiceQuestionOption7 = new ChoiceQuestionOption();
        choiceQuestionOption7.setOption(YesNoOptionEnum.NO.getText());

        choiceQuestionOptions3.add(choiceQuestionOption6);
        choiceQuestionOptions3.add(choiceQuestionOption7);
        question3.setChoiceQuestionOptions(choiceQuestionOptions3);
        checkIn3.setQuestion(question3);

        checkins.add(checkIn1);
        checkins.add(checkIn2);
        checkins.add(checkIn3);
        return checkins;
    }

    private List<Appointment> getAppointmentsHypertension() {
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setName("Hospital");
        appointment1.setAddress("Av. Hilbert 1203, Minnetonka, Minnesota");
        appointment1.setDoctor("Dr. Riviera");
        appointment1.setDate(new Date());

        appointments.add(appointment1);
        return appointments;
    }

}

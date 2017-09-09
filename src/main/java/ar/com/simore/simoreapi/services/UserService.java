package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.utils.TreatmentTemplateHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<UserRepository, User> {

    private static final String ROLES_NOT_PRESENT = "Roles not present";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentTemplateRepository treatmentTemmplateRepository;

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    public List<User> getByRoles(String roles) {
        // Yes yes, I know we should use criteria here
        roles = roles.replaceAll(" ", "");
        String[] rolesSplitted = roles.split(",");
        String role1;
        String role2 = "";
        String role3 = "";
        switch (rolesSplitted.length) {
            case 1:
                role1 = rolesSplitted[0];
                break;
            case 2:
                role1 = rolesSplitted[0];
                role2 = rolesSplitted[1];
                break;
            case 3:
                role1 = rolesSplitted[0];
                role2 = rolesSplitted[1];
                role3 = rolesSplitted[2];
                break;
            default:
                return Collections.emptyList();
        }
        return userRepository.findByRoles_NameOrRoles_NameOrRoles_Name(role1, role2, role3);
    }

    @Override
    public ResponseEntity<User> save(User user) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        Optional<List<Role>> rolesOptional = Optional.ofNullable(user.getRoles());
        if (rolesOptional.isPresent()) {
            List<Role> roles = rolesOptional.get();
            final Optional<Role> hasRolePacient = roles.stream().filter(r ->
                    r.getName().equals(RolesNamesEnum.PACIENT.name())).findAny();
            if (hasRolePacient.isPresent()) {
                final Treatment treatment;

                treatment = assignTreatmentTemplate(user);
                assignCurrentDateDateToTreatment(treatment);
                user.setTreatment(treatment);
                super.save(user);
            } else {
                super.save(user);
            }
        } else {
            LOGGER.error(ROLES_NOT_PRESENT);
            throw new RolesNotPresentException(ROLES_NOT_PRESENT);
        }
        return ResponseEntity.ok(user);
    }

    /** Sets the "creeatedAt" Date to the treatment
     * @param treatment
     */
    private void assignCurrentDateDateToTreatment(Treatment treatment) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        treatment.setCreatedAt(currentDate);
    }

    private Treatment assignTreatmentTemplate(User user) throws TreatmentTemplateNotFoundException {
        final TreatmentTemplate treatmentTemplate = treatmentTemmplateRepository.findOne(user.getTreatment().getTreatmentTemplate().getId());
        if (treatmentTemplate != null) {
            return TreatmentTemplateHandler.copyFromTemplate(treatmentTemplate, user.getTreatment());
        } else {
            LOGGER.error(ROLES_NOT_PRESENT);
            throw new TreatmentTemplateNotFoundException(Long.toString(user.getTreatment().getTreatmentTemplate().getId()));
        }
    }

    /** Adds the fitbit token to the user
     * @param userId
     * @param oAuthNew
     * @return
     */
    public ResponseEntity addFitbitToken(long userId, OAuth oAuthNew) {
        final User user = userRepository.findOne(userId);
        for (OAuth oAuth : user.getOauths()) {
            if(oAuth.getWearableType().name().equals(oAuthNew.getWearableType().name())){
                oAuth.setAccess_token(oAuthNew.getAccess_token());
                oAuth.setUser_id(oAuthNew.getUser_id());
                oAuth.setExpires_in(oAuthNew.getExpires_in());
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        }
        user.getOauths().add(oAuthNew);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}

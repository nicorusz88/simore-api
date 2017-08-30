package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.TreatmentTemplate;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.entities.utils.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.utils.TreatmentTemplateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<UserRepository, User> {

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
    public void save(User user) {
        Optional<List<Role>> rolesOptional = Optional.ofNullable(user.getRoles());
        if (rolesOptional.isPresent()) {
            List<Role> roles = rolesOptional.get();
            final Optional<Role> hasRolePacient = roles.stream().filter(r ->
                    r.getName().equals(RolesNamesEnum.PACIENT.name())).findAny();
            if (hasRolePacient.isPresent()) {
                final TreatmentTemplate treatmentTemplate = treatmentTemmplateRepository.findOne(user.getTreatment().getTreatmentTemplate().getId());
                final Treatment treatment = TreatmentTemplateHandler.copyFromTemplate(treatmentTemplate, user.getTreatment());
                user.setTreatment(treatment);
                super.save(user);
            } else {
                super.save(user);
            }
        }
    }
}

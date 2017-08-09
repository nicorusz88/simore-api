package ar.com.simore.simoreapi;

import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.entities.utils.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.RoleRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Bootstrap {
    private Logger logger = Logger.getLogger(Bootstrap.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {

        try {
            initRoles();
            initUsers();
        } catch (Exception e) {
            logger.error("ERROR initializing data: " + e.getMessage());
        }
    }

    private void initRoles() {

        List<Role> roles = (List<Role>) roleRepository.findAll();
        if (roles.isEmpty()) {
            logger.warn("ATENTION: No roles, creating them...");
            Role role1 = new Role();
            role1.setName(RolesNamesEnum.ADMINISTRATOR.name());
            roleRepository.save(role1);
            Role role2 = new Role();
            role2.setName(RolesNamesEnum.PACIENT.name());
            roleRepository.save(role2);
            Role role3 = new Role();
            role3.setName(RolesNamesEnum.PROFESSIONAL.name());
            roleRepository.save(role3);
        }

    }

    private void initUsers() {

        List<User> administrators = userRepository.findByRoles_Name(RolesNamesEnum.ADMINISTRATOR.name());
        List<User> professionals = userRepository.findByRoles_Name(RolesNamesEnum.PROFESSIONAL.name());
        List<User> pacients = userRepository.findByRoles_Name(RolesNamesEnum.PACIENT.name());

        if (administrators.isEmpty()) {
            logger.warn("ATENTION: No administrators, creating [user/pass]: admin/admin");

            User user1 = new User();
            user1.setUserName("admin");
            user1.setFirstName("admin");
            user1.setLastName("admin");
            user1.setPassword("admin");
            user1.setGender("Masculino");
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
            user1.setGender("Masculino");
            user1.setDeleted(false);
            Role role1 = roleRepository.findByName(RolesNamesEnum.PROFESSIONAL.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            user1.setRoles(roles);
            userRepository.save(user1);

        }
        if (pacients.isEmpty()) {
            logger.warn("ATENTION: No pacients, creating [user/pass]: pacient/pacient");

            User user1 = new User();
            user1.setUserName("pacient");
            user1.setFirstName("pacient");
            user1.setLastName("pacient");
            user1.setPassword("pacient");
            user1.setGender("Masculino");
            user1.setDeleted(false);
            Role role1 = roleRepository.findByName(RolesNamesEnum.PACIENT.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            user1.setRoles(roles);
            userRepository.save(user1);
        }
    }
}

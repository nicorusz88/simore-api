package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService extends BaseService<UserRepository, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    public List<User> getByRoles(String roles) {
        // Yes yes, I know we should use criteria here
        roles.replaceAll(" ","");
        String[] rolesSplitted = roles.split(",");
        String role1 = "";
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

}

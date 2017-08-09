package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<RoleRepository, Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected RoleRepository getRepository() {
        return roleRepository;
    }

}

package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Role;
import ar.com.simore.simoreapi.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<RoleService, Role> {

    @Autowired
    private RoleService roleService;

    @Override
    RoleService getService() {
        return roleService;
    }
}

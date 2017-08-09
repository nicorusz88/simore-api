package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;

    @Override
    UserService getService() {
        return userService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<User>> getByRoles(@RequestParam String roles) {
        return new ResponseEntity<>(userService.getByRoles(roles), HttpStatus.OK);
    }

}

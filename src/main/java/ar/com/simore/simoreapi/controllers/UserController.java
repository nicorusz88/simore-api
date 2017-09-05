package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.OAuth;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import ar.com.simore.simoreapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
        return ResponseEntity.ok(userService.getByRoles(roles));
    }

    @PostMapping("{id}/fitbit")
    public ResponseEntity addFitbitToken(@PathVariable("id") Long id, @Valid @RequestBody OAuth oauth) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        return userService.addFitbitToken(id, oauth);
    }

    @Override
    public ResponseEntity<User> add(@Valid @RequestBody User user) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        return userService.save(user);
    }
}

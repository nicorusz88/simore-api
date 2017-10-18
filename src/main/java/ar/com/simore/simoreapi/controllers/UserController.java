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

    /** Gets all users within the passes in roles, comma separated.
     * Ex. ADMINISTRATOR,PROFESSIONAL
     * @param roles
     * @return
     */
    @GetMapping("/roles")
    public ResponseEntity<List<User>> getByRoles(@RequestParam String roles) {
        return ResponseEntity.ok(userService.getByRoles(roles));
    }


    /** Adds a user
     * @param user
     * @return
     * @throws TreatmentTemplateNotFoundException
     * @throws RolesNotPresentException
     */
    @Override
    public ResponseEntity<User> add(@Valid @RequestBody User user) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        return userService.save(user);
    }

    /** Adds a fitbit token to a user
     * @param id user id
     * @param oauth
     * @return
     * @throws TreatmentTemplateNotFoundException
     * @throws RolesNotPresentException
     */
    @PostMapping("{id}/fitbit")
    public ResponseEntity addFitbitToken(@PathVariable("id") Long id, @Valid @RequestBody OAuth oauth) throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        return userService.addFitbitToken(id, oauth);
    }

    /** Gets all patients from a professional
     * @param professionalID
     * @return
     */
    @GetMapping("/{professionalID}/get-patients")
    public ResponseEntity<List<User>> getPatientsFromProfessional(@PathVariable("professionalID") Long professionalID) {
        return ResponseEntity.ok(userService.getPatientsFromProfessional(professionalID));
    }


}

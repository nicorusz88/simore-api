package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.OAuth;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collections;
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

    /**
     * Gets all users within the passes in roles, comma separated.
     * Ex. ADMINISTRATOR,PROFESSIONAL
     *
     * @param roles
     * @return
     */
    @GetMapping("/roles")
    public ResponseEntity<List<User>> getByRoles(@RequestParam String roles) {
        return ResponseEntity.ok(userService.getByRoles(roles));
    }

    /**
     * Adds a fitbit token to a user
     *
     * @param userId
     * @param uploadfile
     * @return
     */
    @PostMapping("{userId}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.OK);
        }
        final User user = userService.saveAvatar(userId, Collections.singletonList(uploadfile));
        if (user!=null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("Unsuccessfully uploaded - " +
                    uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /** Gets the user avatar
     * @param userId
     * @return
     */
    @GetMapping("{userId}/avatar")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable Long userId) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media = userService.getAvatar(userId);

        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }


    /**
     * Adds a user
     *
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<User> add(@Valid @RequestBody User user) {
        return userService.save(user);
    }

    /**
     * Adds a fitbit token to a user
     *
     * @param id    user id
     * @param oauth
     * @return
     */
    @PostMapping("{id}/fitbit")
    public ResponseEntity addFitbitToken(@PathVariable("id") Long id, @Valid @RequestBody OAuth oauth) {
        return userService.addFitbitToken(id, oauth);
    }

    /**
     * Gets all patients from a professional
     *
     * @param professionalID
     * @return
     */
    @GetMapping("/{professionalID}/get-patients")
    public ResponseEntity<List<User>> getPatientsFromProfessional(@PathVariable("professionalID") Long professionalID) {
        return ResponseEntity.ok(userService.getPatientsFromProfessional(professionalID));
    }


}

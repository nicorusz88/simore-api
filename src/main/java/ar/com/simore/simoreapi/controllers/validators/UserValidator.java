package ar.com.simore.simoreapi.controllers.validators;


import ar.com.simore.simoreapi.entities.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by nicor on 7/8/2017.
 */
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

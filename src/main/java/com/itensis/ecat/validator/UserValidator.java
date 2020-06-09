package com.itensis.ecat.validator;

import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.SaveUserDto;
import com.itensis.ecat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SaveUserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SaveUserDto saveUserDto = (SaveUserDto) target;

        validateObject(saveUserDto, errors);
    }

    private void validateObject(SaveUserDto saveUserDto, Errors errors) {
        if(saveUserDto.getId() != null && saveUserDto.getId() != 0){
            Optional<User> optUser = userRepository.findById(saveUserDto.getId());
            if(optUser.isPresent()){
                if(!optUser.get().getName().equals(saveUserDto.getName()) && userRepository.findByName(saveUserDto.getName()).isPresent()){
                    errors.reject("user.name.isForgiven");
                }
            }else{
                errors.reject("user.id.notValid");
            }
        }else{
            if(saveUserDto.getPassword() == null || saveUserDto.getPassword().isBlank()){
                errors.reject("user.password.isEmpty");
            }
        }
        if(saveUserDto.getPermissions().isEmpty()){
            errors.reject("user.permissions.isEmpty");
        }
    }
}

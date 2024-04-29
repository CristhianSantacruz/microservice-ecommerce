package com.user.service;

import com.user.model.UserEntity;
import com.user.utils.PBKDF2Encoder;
import com.user.utils.RoleUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UserService implements  IUserService{

    @Inject
    PBKDF2Encoder pbkdf2Encoder;

    @Override
    public UserEntity saveUser(UserEntity user) {
        String password = user.getPassword();
        String passwordHash = pbkdf2Encoder.encode(password);
        user.setPassword(passwordHash);
        if(user.getRol()==null || user.getRol() != RoleUser.admin){
            user.setRol(RoleUser.user);
        }
        user.setPassword(passwordHash);
        user.persist();
        return user;
    }

    @Override
    public Optional<UserEntity> getUserById(String id) {
        return UserEntity.findByIdOptional(id);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return UserEntity.find("email",email).firstResultOptional();

    }


    @Override
    public boolean deleteUser(String id) {
        return UserEntity.deleteById(id);
    }

    @Override
    public boolean passwordLoginEquals(String passwordAuth, String passwordUser) {
        return passwordUser.equals(pbkdf2Encoder.encode(passwordAuth));
    }
}

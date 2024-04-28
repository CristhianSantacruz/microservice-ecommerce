package com.user.service;

import com.user.model.UserEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> getUserById(String id);
    Optional<UserEntity> getUserByEmail(String email);
    UserEntity saveUser(UserEntity user);
    boolean deleteUser(String id);

    boolean passwordLoginEquals(String passwordAuth,String passwordUserEncode);
}

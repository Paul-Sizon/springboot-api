package com.cryptoapi.dao;

import com.cryptoapi.api.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    int insertUser(UUID id, User user);
    default int addUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    Optional<User> selectUserById(UUID id);
    int deleteUser(UUID id);
    int updateUser(UUID id, User user);
}

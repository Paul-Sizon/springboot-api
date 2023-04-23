package com.cryptoapi.dao;

import com.cryptoapi.api.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class UserDataAccessService implements UserDao {
    private static List<User> DB = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertUser(UUID id, User user) {
        DB.add(new User(id, user.getName(), user.getEmail()));
        return 0;
    }

    @Override
    public Optional<User> selectUserById(UUID id) {
        final String sql = "SELECT id, name, email FROM user WHERE id = ?";
        User user = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID userId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    return new User(userId, name, email);
                });
        return Optional.ofNullable(user);
    }

    @Override
    public int deleteUser(UUID id) {
        Optional<User> userMaybe = selectUserById(id);
        if (userMaybe.isEmpty()) {
            return 0;
        } else {
            DB.remove(userMaybe.get());
            return 1;
        }
    }

    @Override
    public int updateUser(UUID id, User user) {
        return selectUserById(id)
                .map(u -> {
                    int indexOfUserToUpdate = DB.indexOf(u);
                    if (indexOfUserToUpdate >= 0) {
                        DB.set(indexOfUserToUpdate, new User(id, user.getName(), user.getEmail()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }


    @Override
    public List<User> selectAllUsers() {
        final String sql = "SELECT id, name, email FROM user";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            return new User(id, name, email);
        });
    }


}

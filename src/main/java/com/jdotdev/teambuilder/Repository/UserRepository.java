package com.jdotdev.teambuilder.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.jdotdev.teambuilder.Mapper.UserRowMapper;
import com.jdotdev.teambuilder.Model.User;

@Repository
public class UserRepository implements IRepository<User> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty(); // Returns empty if no user is found
        }
    }

    @Override
    public int save(User user) {
        String sql = "INSERT INTO Users (first_name, last_name, email, password, phone_number) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(),
                                        user.getEmail(), user.getPassword(),
                                        user.getPhoneNumber());
    }

    @Override
    public int update(User user, Integer id) {
        String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), 
                                        user.getPassword(), user.getPhoneNumber(), id);
    }

    @Override
    public int deleteById(Integer id) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

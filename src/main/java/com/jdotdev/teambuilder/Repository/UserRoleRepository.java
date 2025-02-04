package com.jdotdev.teambuilder.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.jdotdev.teambuilder.Mapper.UserRoleRowMapper;
import com.jdotdev.teambuilder.Model.UserRole;

@Repository
public class UserRoleRepository implements IRepository<UserRole> {
    
    private final JdbcTemplate jdbcTemplate;

    public UserRoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserRole> findAll() {
        String sql = "SELECT * FROM UserRoles";
        return jdbcTemplate.query(sql, new UserRoleRowMapper());
    }

    @Override
    public Optional<UserRole> findById(Integer id) {
        String sql = "SELECT * FROM UserRoles WHERE user_role_id = ?";
        try {
            UserRole userRole = jdbcTemplate.queryForObject(sql, new UserRoleRowMapper(), id);
            return Optional.ofNullable(userRole);
        } catch (Exception e) {
            return Optional.empty(); // Returns empty if no user role is found
        }
       // return jdbcTemplate.queryForObject(sql, new UserRoleRowMapper(), id);
    }

    @Override
    public int save(UserRole userRole) {
        String sql = "INSERT INTO UserRoles (role) VALUES (?)";
        return jdbcTemplate.update(sql, userRole.getRole());
    }

    @Override
    public int update(UserRole userRole, Integer id) {
        String sql = "UPDATE UserRoles SET role = ? WHERE user_role_id = ?";
        return jdbcTemplate.update(sql, userRole.getRole(), id);
    }

    @Override
    public int deleteById(Integer id) {
        String sql = "DELETE FROM UserRoles WHERE user_role_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

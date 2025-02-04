package com.jdotdev.teambuilder.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jdotdev.teambuilder.Model.UserRole;

public class UserRoleRowMapper implements RowMapper<UserRole>{

    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setUserRoleId(rs.getInt("user_role_id"));
        userRole.setRole(rs.getString("role"));
        return userRole;
    }
}

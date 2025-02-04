package com.jdotdev.teambuilder.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.jdotdev.teambuilder.Model.Team;
import com.jdotdev.teambuilder.Model.User;
import com.jdotdev.teambuilder.Model.UserRole;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNumber(rs.getString("phone_number"));
        
        // Extract team_id
        Integer teamId = (Integer) rs.getObject("team_id");
        if (teamId != null) 
        {
            Team team = new Team();
            team.setTeamId(teamId); // Set team_id in the Team object
            user.setTeam(team);     // Set Team object in the User object
        }

        // Extract user_role_id
        Integer userRoleId = (Integer) rs.getObject("user_role_id");
        if (userRoleId != null) 
        {
            UserRole userRole = new UserRole();
            userRole.setUserRoleId(userRoleId); // Set user_role_id in the UserRole object
            user.setUserRole(userRole);         // Set UserRole object in the User object
        }

        return user;
    }
    
}

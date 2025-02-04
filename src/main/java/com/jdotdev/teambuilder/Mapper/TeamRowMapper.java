package com.jdotdev.teambuilder.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jdotdev.teambuilder.Model.Team;

public class TeamRowMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team team = new Team();
        team.setTeamId(rs.getInt("team_id"));
        team.setName(rs.getString("name"));
        team.setPhotoKey(rs.getString("photo_key"));
        return team;
    }
    
}

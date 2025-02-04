package com.jdotdev.teambuilder.Repository;

import java.util.List;
import java.util.Optional;
import com.jdotdev.teambuilder.Mapper.TeamRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.jdotdev.teambuilder.Model.Team;

@Repository
public class TeamRepository implements IRepository<Team>{

    private final JdbcTemplate jdbcTemplate;

    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Team> findAll() {
        String sql = "SELECT * FROM Teams";
        return jdbcTemplate.query(sql, new TeamRowMapper());
    }

    @Override
    public Optional<Team> findById(Integer id) {
        String sql = "SELECT * FROM Teams WHERE team_id = ?";
        try {
            Team team = jdbcTemplate.queryForObject(sql, new TeamRowMapper(), id);
            return Optional.ofNullable(team);
        } catch (Exception e) {
            return Optional.empty(); 
        }
       // return jdbcTemplate.queryForObject(sql, new TeamRowMapper(), id);
    }

    @Override
    public int save(Team team) {
        String sql = "INSERT INTO Teams (name, photo_key) VALUES (?, ?)";
        return jdbcTemplate.update(sql, team.getName(), team.getPhotoKey());
    }

    @Override
    public int update(Team team, Integer id) {
        String sql = "UPDATE Teams SET name = ?, photo_key = ? WHERE team_id = ?";
        return jdbcTemplate.update(sql, team.getName(), team.getPhotoKey(), id);
    }

    @Override
    public int deleteById(Integer id) {
        String sql = "DELETE FROM Teams WHERE team_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

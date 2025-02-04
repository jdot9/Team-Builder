package com.jdotdev.teambuilder.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import com.jdotdev.teambuilder.Mapper.TeamRowMapper;
import com.jdotdev.teambuilder.Model.Team;

public class TeamRepositoryTests {
     
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTeam() 
    {
        // Arrange
        Team dotwave = new Team(1,"Dotwave");

        when(teamRepository.save(dotwave)).thenReturn(1);
        String sql = "INSERT INTO Teams (name, photo_key) VALUES (?, ?)";

        // Act
        int result = teamRepository.save(dotwave);

        // Assert
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(sql, dotwave.getName(), dotwave.getPhotoKey());
    }
 
    @Test
    void testFindById_TeamExists() 
    {
        // Arrange: Create a mock team
        Team dotwave = new Team(1,"Dotwave");

        // Mock behavior: When queryForObject() is called, return dotwave
        when(jdbcTemplate.queryForObject(anyString(), any(TeamRowMapper.class), eq(1))).thenReturn(dotwave);

        // Act: Call the repository method
        Optional<Team> result = teamRepository.findById(1);

        // Assert: Ensure the team (dotwave) is found and correct
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTeamId());
        assertEquals("Dotwave", result.get().getName());
   
        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(TeamRowMapper.class), eq(1));
    }

    @Test
    void testFindById_TeamNotFound() 
    {
        // Arrange: Simulate an exception when the team is not found
        when(jdbcTemplate.queryForObject(anyString(), any(TeamRowMapper.class), eq(2)))
            .thenThrow(new RuntimeException("Team not found"));

        // Act: Call the method
        Optional<Team> result = teamRepository.findById(2);
        Team team = result.isPresent() ? result.get() : null;

        // Assert: Ensure that the result is null (user not found)
        assertNull(team);

        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(TeamRowMapper.class), eq(2));
    }

    @Test
    void testFindAllTeams_TeamsExist()
    {
        // Arrange
        List<Team> mockTeams = new ArrayList<>();
        
        Team dotwave = new Team(1, "Dotwave");
        Team openai = new Team(2, "OpenAI");
        Team robotica = new Team(3, "Robotica");

        mockTeams.add(dotwave);
        mockTeams.add(openai);
        mockTeams.add(robotica);
        when(jdbcTemplate.query(anyString(), any(TeamRowMapper.class))).thenReturn(mockTeams);
 
        // Act
        List<Team> result = teamRepository.findAll();

        // Assert: Ensure the correct number of users are returned
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Dotwave", result.get(0).getName());
        assertEquals("OpenAI", result.get(1).getName());
        assertEquals("Robotica", result.get(2).getName());

        // Verify that jdbcTemplate.query() was called exactly once
        verify(jdbcTemplate, times(1)).query(anyString(), any(TeamRowMapper.class));
    }

    @Test
    void testFindAllTeams_NoTeamsExist()
    {
        // Arrange
        List<Team> mockTeams = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), any(TeamRowMapper.class))).thenReturn(mockTeams);

        // Act
        List<Team> result = teamRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(TeamRowMapper.class));
    }

    @Test
    void deleteByIdTest_TeamExists()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        // Act
        int rowsAffected = teamRepository.deleteById(1);

        // Assert
        assertEquals(1, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void deleteByIdTest_TeamDoesNotExist()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(0);

        // Act
        int rowsAffected = teamRepository.deleteById(1);

        // Assert
        assertEquals(0, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void testUpdateTeam_TeamExists()
    {
        // Arrange
        Team dotwave = new Team(1, "Dotwave");
        String sql = "UPDATE Teams SET name = ?, photo_key = ? WHERE team_id = ?";
        dotwave.setName("jdot");
        when(teamRepository.update(dotwave,1)).thenReturn(1);

        // Act
        int rowsUpdated = teamRepository.update(dotwave,1);

        // Assert
        assertEquals(1, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, dotwave.getName(), dotwave.getPhotoKey(), 1);
    }
     
    @Test
    void testUpdateTeam_TeamDoesNotExist()
    {
        // Arrange
        Team dotwave = new Team(1, "Dotwave");
        String sql = "UPDATE Teams SET name = ?, photo_key = ? WHERE team_id = ?";
        dotwave.setName("jdot");
        when(teamRepository.update(dotwave,2)).thenReturn(0);

        // Act
        int rowsUpdated = teamRepository.update(dotwave,2);

        // Assert
        assertEquals(0, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, dotwave.getName(), dotwave.getPhotoKey(), 2);
    }
}

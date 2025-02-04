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
import com.jdotdev.teambuilder.Mapper.UserRoleRowMapper;
import com.jdotdev.teambuilder.Model.UserRole;

public class UserRoleRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserRole() 
    {
        // Arrange
        UserRole admin = new UserRole(1,"Admin");

        when(userRoleRepository.save(admin)).thenReturn(1);
        String sql = "INSERT INTO UserRoles (role) VALUES (?)";

        // Act
        int result = userRoleRepository.save(admin);

        // Assert
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(sql, admin.getRole());
    }
 
    @Test
    void testFindById_UserRoleExists() 
    {
        // Arrange: Create a mock team
        UserRole admin = new UserRole(1,"Admin");

        // Mock behavior: When queryForObject() is called, return dotwave
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleRowMapper.class), eq(1))).thenReturn(admin);

        // Act: Call the repository method
        Optional<UserRole> result = userRoleRepository.findById(1);

        // Assert: Ensure the team (dotwave) is found and correct
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getUserRoleId());
        assertEquals("Admin", result.get().getRole());
   
        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRoleRowMapper.class), eq(1));
    }
 
    @Test
    void testFindById_UserRoleNotFound() 
    {
        // Arrange: Simulate an exception when the team is not found
        when(jdbcTemplate.queryForObject(anyString(), any(UserRoleRowMapper.class), eq(2)))
            .thenThrow(new RuntimeException("User Role not found"));

        // Act: Call the method
        Optional<UserRole> result = userRoleRepository.findById(2);
        UserRole userRole = result.isPresent() ? result.get() : null;

        // Assert: Ensure that the result is null (user not found)
        assertNull(userRole);

        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRoleRowMapper.class), eq(2));
    }

    @Test
    void testFindAllUserRoles_UserRolesExist()
    {
        // Arrange
        List<UserRole> mockUserRoles = new ArrayList<>();
        
        UserRole admin = new UserRole(1, "Admin");
        UserRole manager = new UserRole(2, "Manager");
        UserRole softwareEngineer = new UserRole(3, "Software Engineer");

        mockUserRoles.add(admin);
        mockUserRoles.add(manager);
        mockUserRoles.add(softwareEngineer);
        when(jdbcTemplate.query(anyString(), any(UserRoleRowMapper.class))).thenReturn(mockUserRoles);
 
        // Act
        List<UserRole> result = userRoleRepository.findAll();

        // Assert: Ensure the correct number of users are returned
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Admin", result.get(0).getRole());
        assertEquals("Manager", result.get(1).getRole());
        assertEquals("Software Engineer", result.get(2).getRole());

        // Verify that jdbcTemplate.query() was called exactly once
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRoleRowMapper.class));
    }

    @Test
    void testFindAllUserRoles_NoUserRolesExist()
    {
        // Arrange
        List<UserRole> mockUserRoles = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), any(UserRoleRowMapper.class))).thenReturn(mockUserRoles);

        // Act
        List<UserRole> result = userRoleRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRoleRowMapper.class));
    }

    @Test
    void deleteByIdTest_UserRoleExists()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        // Act
        int rowsAffected = userRoleRepository.deleteById(1);

        // Assert
        assertEquals(1, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void deleteByIdTest_UserRoleDoesNotExist()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(0);

        // Act
        int rowsAffected = userRoleRepository.deleteById(1);

        // Assert
        assertEquals(0, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void testUpdateUserRole_UserRoleExists()
    {
        // Arrange
        UserRole admin = new UserRole(1, "Admin");
        String sql = "UPDATE UserRoles SET role = ? WHERE user_role_id = ?";
        admin.setRole("Manager");
        when(userRoleRepository.update(admin,1)).thenReturn(1);

        // Act
        int rowsUpdated = userRoleRepository.update(admin,1);

        // Assert
        assertEquals(1, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, admin.getRole(), 1);
    }
 
    @Test
    void testUpdateUserRole_UserRoleDoesNotExist()
    {
        // Arrange
        UserRole admin = new UserRole(1, "Admin");
        String sql = "UPDATE UserRoles SET role = ? WHERE user_role_id = ?";
        admin.setRole("Electrical Engineer");
        when(userRoleRepository.update(admin,2)).thenReturn(0);

        // Act
        int rowsUpdated = userRoleRepository.update(admin,2);

        // Assert
        assertEquals(0, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, admin.getRole(), 2);
    }    
}

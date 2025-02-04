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
import com.jdotdev.teambuilder.Mapper.UserRowMapper;
import com.jdotdev.teambuilder.Model.User;
import com.jdotdev.teambuilder.Repository.UserRepository;

public class UserRepositoryTests {
    
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() 
    {
        // Arrange
        User jason = new User(1, "Jason", "Dotson", "jasondotson@gmail.com",
                              "myPassword", "9015748585", "LongPhotoKeyString");

        when(userRepository.save(jason)).thenReturn(1);
        String sql = "INSERT INTO Users (first_name, last_name, email, password, phone_number) VALUES (?, ?, ?, ?, ?)";

        // Act
        int result = userRepository.save(jason);

        // Assert
        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(sql, jason.getFirstName(), jason.getLastName(),
                                                                                jason.getEmail(), jason.getPassword(),
                                                                                jason.getPhoneNumber());
    }

        @Test
        void testFindById_UserExists() {
        // Arrange: Create a mock user
        User jason = new User(1, "Jason", "Dotson", "jason@example.com",
                                 "myPassword", "9015748585", "LongPhotoKeyString");

        // Mock behavior: When queryForObject() is called, return jason
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(1))).thenReturn(jason);

        // Act: Call the repository method
        Optional<User> result = userRepository.findById(1);

        // Assert: Ensure the user (jason) is found and correct
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getUserId());
        assertEquals("Jason", result.get().getFirstName());
        assertEquals("Dotson", result.get().getLastName());
        assertEquals("jason@example.com", result.get().getEmail());
        assertEquals("myPassword", result.get().getPassword());
        assertEquals("LongPhotoKeyString", result.get().getPhotoKey());

        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRowMapper.class), eq(1));
    }

    @Test
    void testFindById_UserNotFound() {
        // Arrange: Simulate an exception when the user is not found
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(2)))
            .thenThrow(new RuntimeException("User not found"));

        // Act: Call the method
        Optional<User> result = userRepository.findById(2);
        User user = result.isPresent() ? result.get() : null;

        // Assert: Ensure that the result is null (user not found)
        assertNull(user);

        // Verify that queryForObject() was called exactly once
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(UserRowMapper.class), eq(2));
    }

    @Test
    void testFindAllUsers_UsersExist()
    {
        // Arrange
        List<User> mockUsers = new ArrayList<>();
     
        User jason = new User(1, "Jason", "Dotson", "jason@example.com",
                     "myPassword", "9015748585", "LongPhotoKeyString");

        User carol = new User(2, "Carol", "Cee", "carol@twd.com",
                              "carolPass", "1112223333", "SuperLongPhotoKeyString");

        User daryl = new User(3, "Daryl", "Dixon", "crossbow@twd.com",
                              "merle", "2223334444", "");

        mockUsers.add(jason);
        mockUsers.add(carol);
        mockUsers.add(daryl);
        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class))).thenReturn(mockUsers);
 
        // Act
        List<User> result = userRepository.findAll();

        // Assert: Ensure the correct number of users are returned
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Jason", result.get(0).getFirstName());
        assertEquals("Carol", result.get(1).getFirstName());
        assertEquals("Daryl", result.get(2).getFirstName());

        // Verify that jdbcTemplate.query() was called exactly once
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRowMapper.class));
    }

    @Test
    void testFindAllUsers_NoUsersExist()
    {
        // Arrange
        List<User> mockUsers = new ArrayList<>();
        when(jdbcTemplate.query(anyString(), any(UserRowMapper.class))).thenReturn(mockUsers);

        // Act
        List<User> result = userRepository.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(UserRowMapper.class));
    }

    @Test
    void deleteByIdTest_UserExists()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        // Act
        int rowsAffected = userRepository.deleteById(1);

        // Assert
        assertEquals(1, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void deleteByIdTest_UserDoesNotExist()
    {
        // Arrange
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(0);

        // Act
        int rowsAffected = userRepository.deleteById(1);

        // Assert
        assertEquals(0, rowsAffected);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(1));
    }

    @Test
    void testUpdateUser_UserExists()
    {
        // Arrange
        User jason = new User(1, "Jason", "Dotson", "jasondotson@gmail.com",
                              "myPassword", "9015748585", "LongPhotoKeyString");
        String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ? WHERE user_id = ?";
        jason.setLastName("Wallace");
        when(userRepository.update(jason,1)).thenReturn(1);

        // Act
        int rowsUpdated = userRepository.update(jason,1);

        // Assert
        assertEquals(1, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, jason.getFirstName(), jason.getLastName(),
                                                                        jason.getEmail(), jason.getPassword(),
                                                                        jason.getPhoneNumber(), 1);
    }

    @Test
    void testUpdateUser_UserDoesNotExist()
    {
        // Arrange
        User jason = new User(1, "Jason", "Dotson", "jasondotson@gmail.com",
                              "myPassword", "9015748585", "LongPhotoKeyString");
        String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, password = ?, phone_number = ? WHERE user_id = ?";
        jason.setLastName("Wallace");
        when(userRepository.update(jason,2)).thenReturn(0);

        // Act
        int rowsUpdated = userRepository.update(jason,2);

        // Assert
        assertEquals(0, rowsUpdated);
        verify(jdbcTemplate, times(1)).update(sql, jason.getFirstName(), jason.getLastName(),
                                                                jason.getEmail(), jason.getPassword(),
                                                                jason.getPhoneNumber(), 2);
    }
}

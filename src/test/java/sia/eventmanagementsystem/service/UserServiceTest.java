package sia.eventmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sia.eventmanagementsystem.entity.User;
import sia.eventmanagementsystem.repo.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        when(userRepo.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("Paul");
        user.setPassword("2323");
        user.setEmail("example@email.com");


        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(userId, result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenGetUserById() {
        Long invalidUserId = -1L;

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(invalidUserId);
        });

        assertTrue(exception.getMessage().contains("User isn't found by id " + invalidUserId));
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users.size(), result.size());
    }

    @Test
    public void testUserUpdate() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setUsername("Charlie");
        User requestedUser = new User();
        requestedUser.setUsername("Paul");

        when(userRepo.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(existingUser)).thenReturn(existingUser);


        User result = userService.userUpdate(requestedUser, userId);


        assertNotNull(result);
        assertEquals("Paul", existingUser.getUsername());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenUserUpdate() {
        Long nonExistentUserId = -1L;
        User requestedUser = new User();
        when(userRepo.findById(nonExistentUserId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.userUpdate(requestedUser, nonExistentUserId);
        });

        assertTrue(exception.getMessage().contains("User isn't found by id " + nonExistentUserId));

        verify(userRepo, never()).save(any(User.class));
    }

}




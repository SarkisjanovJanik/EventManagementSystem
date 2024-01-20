package sia.eventmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sia.eventmanagementsystem.entity.User;
import sia.eventmanagementsystem.repo.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;


    public User createUser (User user) {
        return userRepo.save(user);
    }

    public User getUserById (Long userId){
        return userRepo.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User isn't found by id " + userId));
    }

    public List<User> getAllUsers () {
        return userRepo.findAll();
    }



    public User userUpdate (User user , Long userId) {
        User existingUser = userRepo.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User isn't found by id " + userId));

        if (user.getUsername() != null){
            existingUser.setUsername(user.getUsername());
        }
        if (user.getEmail() != null){
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getCreatedEvents() != null){
            existingUser.setCreatedEvents(user.getCreatedEvents());
        }
        if (user.getAttendedEvents() != null){
            existingUser.setAttendedEvents(user.getAttendedEvents());
        }
      return  userRepo.save(existingUser);
    }


}
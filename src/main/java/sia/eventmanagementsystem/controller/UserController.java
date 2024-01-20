package sia.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sia.eventmanagementsystem.entity.User;
import sia.eventmanagementsystem.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUserDetails(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/details";
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/users";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error",
                    "email or username already exists. Please choose a different email or username.");
            return "user/create";
        }
    }


    @GetMapping("/{userId}/edit")
    public String showEditUserForm(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/{userId}/edit")
    public String editUser(@PathVariable Long userId, @ModelAttribute User user) {
        user.setId(userId);
        userService.userUpdate(user,userId);
        return "redirect:/users";
    }

}

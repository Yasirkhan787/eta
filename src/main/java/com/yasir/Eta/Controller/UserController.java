package com.yasir.Eta.Controller;

import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Requests.RegisterRequest;
import com.yasir.Eta.Service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1")
public class UserController {

    private final UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";  // Adjust the login path as needed
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Return an error page or redirect as needed if user is not found
            return "error";  // Redirect or return an error template as appropriate
        }

        model.addAttribute("user", user);
        return "profile"; // Return the Thymeleaf template for the profile page
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";  // Adjust the login path as needed
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Return an error page or redirect as needed if user is not found
            return "error";  // Redirect or return an error template as appropriate
        }

        model.addAttribute("user", user);
        return "edit-profile"; // Return the Thymeleaf template for the edit profile page
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute RegisterRequest user) {


        // Call the service layer to update the user profile
        userService.updateUser(user);
        return "redirect:/v1/profile"; // Redirect to the profile page after saving changes
    }
}

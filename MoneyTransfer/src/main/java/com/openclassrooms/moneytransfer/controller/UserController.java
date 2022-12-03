package com.openclassrooms.moneytransfer.controller;

import com.openclassrooms.moneytransfer.dto.ConnectionForm;
import com.openclassrooms.moneytransfer.model.User;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import com.openclassrooms.moneytransfer.security.CustomUserDetails;
import com.openclassrooms.moneytransfer.security.oauth.CustomOAuth2User;
import com.openclassrooms.moneytransfer.service.TransactionService;
import com.openclassrooms.moneytransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * =============================================================================
 * this controller is responsible for receiving requests and providing responses
 * relating to users
 * =============================================================================
 */
@Controller
@RequiredArgsConstructor

public class UserController {
    @Autowired
    UserService userService;

    //Display page allowing new user to register : create new account
    @GetMapping("/user/register")
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }
    //Send information that user input in the register form
    @PostMapping("/user/register")
    public String register(@ModelAttribute User user, Model model){
        model.addAttribute("userForm",new User());
        userService.addOrUpdateUser(user);
        return "redirect:/login";
    }

    //Display page allowing user to add new connection with a user
    @GetMapping("/user/newconnection")
    public String getConnectionForm(Model model) {
        model.addAttribute("connectionForm", new ConnectionForm());
        return "connectionForm";
    }
    //Send informations that user input in the connection form
    @PostMapping("/user/newconnection")
    public String addConnection(@ModelAttribute ConnectionForm connectionForm,
                                Model model,
                                RedirectAttributes redirAttrs, Authentication authentication) {
        model.addAttribute("connectionForm", new ConnectionForm());
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }

        String response = userService.addConnection(connectionForm.getFriendEmail(), email);
        switch (response) {
            case "error":
                redirAttrs.addFlashAttribute("error", "no user found with provided email!");
                break;
            case "success":
                redirAttrs.addFlashAttribute("success", "connection added successfully.");
                break;
            case "exists":
                redirAttrs.addFlashAttribute("exists", "connection already exists.");
                break;
        }

        return "redirect:/user/newconnection";

    }

    //Display page allowing user to add new contact
    @GetMapping("/user/contact")
    public String getTransfers(Model model,  @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size, Authentication authentication) {
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }
        User user = userService.getUserByEmail(email).get();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Page<User> userPage = userService.findPaginated(user,PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        List<User> friends = user.getFriends();
        model.addAttribute("friends", friends);
        return "contact";
    }
    //Display page profile
    @GetMapping("/user/profile")
    public String getTransfers(Model model, Authentication authentication) {
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }
        User user=null;
        if (userService.existsByEmail(email))
        user=userService.getUserByEmail(email).get();

        else return "login";
        model.addAttribute("user", user);
        return "profile";
    }
    //Display page allowing user to modify profile
    @GetMapping("/user/modify")
    public String getModify(Model model, Authentication authentication) {
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }
        User user=userService.getUserByEmail(email).get();
        model.addAttribute("user", user);
        return "modify";
    }
    //Send informations that user input in the profile form
    @PostMapping("/user/modify")
    public String modifyUser(@ModelAttribute User user,
                              Model model, Authentication authentication,
                              RedirectAttributes redirAttrs){
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }
            if (userService.UpdateUser(user, email))
            redirAttrs.addFlashAttribute("success", "Profile updated successfully");
            else
                redirAttrs.addFlashAttribute("error", "please add password");
        return "redirect:/user/profile";
    }

}









package com.openclassrooms.moneytransfer.controller;

import com.openclassrooms.moneytransfer.dto.TransferForm;
import com.openclassrooms.moneytransfer.dto.TransferFormat;

import com.openclassrooms.moneytransfer.repository.UserRepository;
import com.openclassrooms.moneytransfer.security.CustomUserDetails;
import com.openclassrooms.moneytransfer.security.oauth.CustomOAuth2User;
import com.openclassrooms.moneytransfer.service.TransactionService;
import com.openclassrooms.moneytransfer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/*
 * =============================================================================
 * this controller is responsible for receiving requests and providing responses
 * relating to the home page
 * =============================================================================
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;


    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       Authentication authentication) {

        String email=null;

        // Local connexion
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        //Connexion with google
        else if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        //Connexion with facebook
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }

        if ( userService.existsByEmail(email))
        {int userId = userService.getUserByEmail(email).get().getId();
            Map<Integer, String> connections = transactionService.getConnections(email);
            List<TransferFormat> transfers = transactionService.getTransactions(userId, page);
            int totalPages = transactionService.getTotalPages(userId, page);
            model.addAttribute("transferList", transfers);
            model.addAttribute("connectionMap", connections);
            model.addAttribute("pagesCount", new int[totalPages]);
            model.addAttribute("currentPage", page);
            model.addAttribute("transferForm", new TransferForm());
        }
        return "home";
    }
}

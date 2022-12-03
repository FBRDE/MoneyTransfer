package com.openclassrooms.moneytransfer.controller;

import com.openclassrooms.moneytransfer.dto.TransferForm;
import com.openclassrooms.moneytransfer.dto.TransferFormat;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import com.openclassrooms.moneytransfer.security.CustomUserDetails;
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
 * relating to login and logout
 * =============================================================================
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }


}

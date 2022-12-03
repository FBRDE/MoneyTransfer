package com.openclassrooms.moneytransfer.controller;


import com.openclassrooms.moneytransfer.dto.TransferForm;
import com.openclassrooms.moneytransfer.dto.TransferFormat;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import com.openclassrooms.moneytransfer.security.CustomUserDetails;
import com.openclassrooms.moneytransfer.security.oauth.CustomOAuth2User;
import com.openclassrooms.moneytransfer.service.TransactionService;
import com.openclassrooms.moneytransfer.service.UserService;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/*
 * =============================================================================
 * this controller is responsible for receiving requests and providing responses
 * relating to transaction
 * =============================================================================
 */
@Controller

@RequiredArgsConstructor

public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    //Get all transfer saved in database of connected user
    @GetMapping("/transactions")
    public String getTransfers(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page, Authentication authentication
                               ) {

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

        int userId=userService.getUserByEmail(email).get().getId();
        Map<Integer, String> connections = transactionService.getConnections(email);
        List<TransferFormat> transfers = transactionService.getTransactions(userId, page);
        int totalPages = transactionService.getTotalPages(userId, page);
        model.addAttribute("transferList", transfers);
        model.addAttribute("connectionMap", connections);
        model.addAttribute("pagesCount", new int[totalPages]);
        model.addAttribute("currentPage", page);
        model.addAttribute("transferForm", new TransferForm());
        return "transactions";
    }

    // Allow connected user to add new transfer of money to another user
    @PostMapping("/transactions/new")
    public String addTransfer(@ModelAttribute TransferForm transferForm,
                                Model model, Authentication authentication,
                                    RedirectAttributes redirAttrs){
        String email=null;
        if(authentication.getPrincipal() instanceof CustomUserDetails)
        {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            email= userDetails.getUsername();
        }
        else if(authentication.getPrincipal() instanceof DefaultOidcUser)
        {
            DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");
        }
        else if(authentication.getPrincipal() instanceof CustomOAuth2User)
        {

            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            email = oauthUser.getAttribute("email");

        }

        model.addAttribute("transferForm",new TransferForm());
        if (transferForm.getReceiverId()==0) {
            redirAttrs.addFlashAttribute("error", "please select a recipient");
        } else {
           if(transactionService.addTransfer(transferForm, email))
            redirAttrs.addFlashAttribute("success", "transfer executed successfully");
           else
               redirAttrs.addFlashAttribute("error", "you don't have enough balance!");
        }
        return "redirect:/transactions";
    }
}
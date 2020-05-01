package nehe.sharenews.controllers;

import nehe.sharenews.models.User;
import nehe.sharenews.security.UserPrincipal;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    private  AuthService authService;
    private SecurityUtil securityUtil;

    @Autowired
    public AuthController(AuthService authService, SecurityUtil securityUtil) {
        this.authService = authService;
        this.securityUtil  =  securityUtil;
    }

    @GetMapping("/")
    public String firstPage(){

        if(securityUtil.isUserLoggedIn()){
            return "redirect:/posts";
        }

        return "login";
    }

    @GetMapping("/login")
    public String loginPage(){

        if(securityUtil.isUserLoggedIn()){
            return "redirect:/posts";
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(){

        if(securityUtil.isUserLoggedIn()){
            return "redirect:/posts";
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model){

        //check if user with email is already registered
        if(authService.isEmailExists(user.getEmail())){
            model.addAttribute("RegistrationError", "Email already in use !" );
            return "register";
        }

        var unEncryptedPassword = user.getPassword();
        var savedUser = authService.registerUser(user);

        if( savedUser != null ){

            securityUtil.authenticateUser( savedUser , unEncryptedPassword );

            return "redirect:/posts";
        }

        model.addAttribute("RegistrationError", "An error occurred ! Try again" );

        return "register";

    }


    @GetMapping("/posts")
    public String postsPage(){
        return "post";
    }

}

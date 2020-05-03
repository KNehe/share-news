package nehe.sharenews.controllers;

import nehe.sharenews.models.User;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.EmailService;
import nehe.sharenews.utils.PasswordReset;
import nehe.sharenews.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;


@Controller
public class AuthController {

    private  AuthService authService;
    private SecurityUtil securityUtil;
    private PasswordReset passwordReset;
    private EmailService emailService;

    @Autowired
    public AuthController(AuthService authService, SecurityUtil securityUtil, PasswordReset passwordReset, EmailService emailService) {
        this.authService = authService;
        this.securityUtil  =  securityUtil;
        this.passwordReset = passwordReset;
        this.emailService = emailService;
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
    public ModelAndView registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){

        //check if user with email is already registered
        if(authService.isEmailExists(user.getEmail())){
            redirectAttributes.addFlashAttribute("RegistrationError", "Email already in use !" );
            return new ModelAndView("redirect:/register");
        }

        if(user.getPassword().length() < 6){
            redirectAttributes.addFlashAttribute("RegistrationError", "Password should be at least 6 characters !" );
            return new ModelAndView("redirect:/register");
        }

        var unEncryptedPassword = user.getPassword();
        var savedUser = authService.registerUser(user);

        if( savedUser != null ){

            securityUtil.authenticateUser( savedUser , unEncryptedPassword );

            return new ModelAndView("redirect:/posts");
        }

        redirectAttributes.addFlashAttribute("RegistrationError", "An error occurred ! Try again" );

        return new ModelAndView("redirect:/register");

    }


    @GetMapping("/posts")
    public String postsPage(){
        return "post";
    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage(){
        return "forgotPassword";
    }

    @GetMapping("/resetPassword/{token}")
    public ModelAndView resetPasswordPage(@PathVariable String token){

        var model = new HashMap<String, String>();
        model.put("pathVar",token);

        return new ModelAndView("resetPassword",model);
    }

    @GetMapping("/resetPassword")
    public ModelAndView resetPasswordPageNoToken(){

        return new ModelAndView("resetPassword");
    }

    @PostMapping("/forgotPassword")
    public ModelAndView forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes){

        var user = authService.findUserByEmail(email);

        if( user == null ){

            redirectAttributes.addFlashAttribute("ErrorMessage","Email not registered !");
            return new ModelAndView("redirect:/forgotPassword");
        }

        var token = passwordReset.generateResetToken(email);
        var expirationDate = passwordReset.generateExpirationDate();

        user.setResetPasswordToken(token);
        user.setExpirationDate(expirationDate);

        var updatedUser = authService.updateUser(user);

        if(updatedUser == null ){
            redirectAttributes.addFlashAttribute("ErrorMessage","An error occurred");
            return new ModelAndView("redirect:/forgotPassword");
        }

        var resetLink = "http://localhost:8080/resetPassword/"+token;

        var subject = String.format("Click this link to reset your password %s . If you didn't request this please ignore",resetLink);

        var emailText = emailService.sendMail(email,"Share News Reset Password", subject);

        if(emailText == null){
            redirectAttributes.addFlashAttribute("ErrorMessage","An error occurred while sending email");
            return new ModelAndView("redirect:/forgotPassword");
        }

        redirectAttributes.addFlashAttribute("SuccessMessage","A password reset link has been sent to your email!");
        return new ModelAndView("redirect:/forgotPassword");
    }

    @PostMapping("/resetPassword/{token}")
    public ModelAndView resetPassword(@PathVariable("token") String token, @RequestParam String password,
                                      RedirectAttributes redirectAttributes){

        var user = authService.findUserByToken(token);
        var date = new Date();

        if( user == null ){

            redirectAttributes.addFlashAttribute("ErrorMessage","Your not registered !");
            return new ModelAndView("redirect:/resetPassword");
        }

        if( date.after( user.getExpirationDate() ) ){

            redirectAttributes.addFlashAttribute("ErrorMessage","Reset time expired !");
            return new ModelAndView("redirect:/resetPassword");
        }

        user.setPassword( new BCryptPasswordEncoder().encode(password));
        user.setExpirationDate(null);
        user.setResetPasswordToken(null);

        authService.updateUser(user);

        redirectAttributes.addFlashAttribute("SuccessMessage","Password reset !");
        return new ModelAndView("redirect:/resetPassword");


    }
}

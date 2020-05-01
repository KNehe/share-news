package nehe.sharenews.controllers;

import nehe.sharenews.models.Post;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {

    private PostService postService;
    private AuthService authService;

    @Autowired
    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping("/addPost")
    public ModelAndView addPost(@ModelAttribute Post post, Principal principal, @RequestParam("file") MultipartFile file, RedirectAttributes atts){

        var email = principal.getName();

        var user = authService.findUserByEmail(email);

        post.setUser(user);
        post.setImage(file.getOriginalFilename()); //TODO upload image

        if(postService.addPost(post) != null ){

            atts.addFlashAttribute("SuccessMessage","Post Added");

            return new ModelAndView("redirect:/posts");
        }

        atts.addFlashAttribute("FailureMessage","An error occurred try again");

        return new ModelAndView("redirect:/posts");

    }


}

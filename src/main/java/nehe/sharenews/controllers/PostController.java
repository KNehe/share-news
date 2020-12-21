package nehe.sharenews.controllers;

import nehe.sharenews.models.Post;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.CommentService;
import nehe.sharenews.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;


enum ContentType{
	IMAGE_JPEG,IMAGE_PNG
}

@Controller
public class PostController {

    private PostService postService;
    private AuthService authService;
    private CommentService commentService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";


    @Autowired
    public PostController(PostService postService, AuthService authService, CommentService commentService) {
        this.postService = postService;
        this.authService = authService;
        this.commentService = commentService;
    }

    @PostMapping("/addPost")
    public ModelAndView addPost(@ModelAttribute Post post, Principal principal, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        
    	if(file.isEmpty()) {
    		redirectAttributes.addFlashAttribute("FailureMessage","An image is required");

            return new ModelAndView("redirect:/posts");
    		
    	}
    	
    	if(Arrays.asList(ContentType.IMAGE_JPEG.toString(), ContentType.IMAGE_PNG.toString()).contains(file.getContentType())) {
    		redirectAttributes.addFlashAttribute("FailureMessage","File should be an image (JPEG or PNG");

            return new ModelAndView("redirect:/posts");
    	}
    	
        var email = principal.getName();

        var user = authService.findUserByEmail(email);

        post.setUser(user);
        
        var fileMetaData  = new HashMap<String,String>();
        fileMetaData.put("Content-Type", file.getContentType());
        fileMetaData.put("Content-Length", String.valueOf(file.getSize()));

        try {
             // check image
            Path path = Paths.get(uploadDirectory, principal.getName() + file.getOriginalFilename());

            Files.write(path,file.getBytes());

            String  [] stringPath = path.toString().split("static");

            post.setImage(stringPath[1]);

        }catch (Exception e){
            e.printStackTrace();
        }


        if(postService.addPost(post) != null ){

            redirectAttributes.addFlashAttribute("SuccessMessage","Post Added");

            return new ModelAndView("redirect:/posts");
        }

        redirectAttributes.addFlashAttribute("FailureMessage","An error occurred try again");

        return new ModelAndView("redirect:/posts");

    }

    @GetMapping("/post/{postId}")
    public  ModelAndView deletePost(@PathVariable Long postId){

        commentService.deleteCommentByPostId(postId);
        postService.deletePost(postId);

        return new ModelAndView("redirect:/posts");

    }



}

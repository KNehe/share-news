package nehe.sharenews.controllers;

import nehe.sharenews.models.Post;

import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.CommentService;
import nehe.sharenews.services.PostService;
import nehe.sharenews.services.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import nehe.sharenews.models.PostViewModel;

import org.springframework.ui.Model;





enum ContentType{
	IMAGE_JPEG,IMAGE_PNG
}

@Controller
public class PostController {

    private PostService postService;
    private AuthService authService;
    private CommentService commentService;
    private UploadService uploadService;

    @Autowired
    public PostController(PostService postService, AuthService authService, CommentService commentService,UploadService uploadService) {
        this.postService = postService;
        this.authService = authService;
        this.commentService = commentService;
        this.uploadService = uploadService;
    }

    @MessageMapping("/posts")
    @SendTo("/news-app/posts")
    public List<PostViewModel>  addPost(@ModelAttribute Post post, Principal principal){
        return postService.getPosts(principal);
    	// if(file.isEmpty()) {
    	// 	redirectAttributes.addFlashAttribute("FailureMessage","An image is required");

        //     return new ModelAndView("redirect:/posts");
    		
    	// }
    	
    	// if(Arrays.asList(ContentType.IMAGE_JPEG.toString(), ContentType.IMAGE_PNG.toString()).contains(file.getContentType())) {
    	// 	redirectAttributes.addFlashAttribute("FailureMessage","File should be an image (JPEG or PNG");

        //     return new ModelAndView("redirect:/posts");
    	// }
    	
        // var email = principal.getName();

        // var user = authService.findUserByEmail(email);

        // post.setUser(user);

        // try {
        	
    	// 	var imageUrl = uploadService.uploadImageWithCloudinary(file);
    		        
        //     post.setImage(imageUrl);

        // }catch (Exception e){
        //     e.printStackTrace();
            
        //     redirectAttributes.addFlashAttribute("FailureMessage","An error occurred while uploading image");

        //     return new ModelAndView("redirect:/posts");
        // }


        // if(postService.addPost(post) != null ){

        //     redirectAttributes.addFlashAttribute("SuccessMessage","Post Added");

        //     return new ModelAndView("redirect:/posts");
        // }

        // redirectAttributes.addFlashAttribute("FailureMessage","An error occurred try again");

        // return new ModelAndView("redirect:/posts");

    }

    @GetMapping("/post/{postId}")
    public  ModelAndView deletePost(@PathVariable Long postId){

        commentService.deleteCommentByPostId(postId);
        postService.deletePost(postId);

        return new ModelAndView("redirect:/posts");

    }

 @GetMapping("/posts")
    public String postsPage(Model model, Principal principal){

        model.addAttribute("FirstName", authService.getFirstName(principal.getName()));
        return "post";
    }

}

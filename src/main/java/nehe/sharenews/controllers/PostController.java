package nehe.sharenews.controllers;

import nehe.sharenews.models.Failure;
import nehe.sharenews.models.Post;

import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.CommentService;
import nehe.sharenews.services.PostService;
import nehe.sharenews.services.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Arrays;

import nehe.sharenews.models.RawPost;

import org.springframework.ui.Model;





enum ContentType{
	IMAGE_JPEG,IMAGE_PNG
}

@Controller
@CrossOrigin
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

    @MessageMapping("/get-posts")
    @SendTo("/news-app/get-posts")
    public ResponseEntity<?>  getPosts(Principal principal){
        return ResponseEntity.ok(postService.getPosts(principal));

    }
    
    @MessageMapping("/post")
    @SendTo("/news-app/get-posts")
    public ResponseEntity<?>  addPost(RawPost post, Principal principal){

        if( post.getImageUrl() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Failure("An image is required"));
        }
        
        var email = principal.getName();

        var user = authService.findUserByEmail(email);
        
        Post newPost = new Post();

        newPost.setUser(user);
        newPost.setImage(post.getImageUrl());
        newPost.setDescription(post.getDescription());

        if(postService.addPost(newPost) != null ){
            return ResponseEntity.ok(postService.getPosts(principal));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Failure("An error occurred try again"));     

    }
    
    @PostMapping("/image-upload")
    @ResponseBody
    public ResponseEntity<?> uploadImage(MultipartFile file){

       if(file.isEmpty()) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Failure("Image is required"));    		
        }
        
    	
    	if(Arrays.asList(ContentType.IMAGE_JPEG.toString(), ContentType.IMAGE_PNG.toString()).contains(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Failure("File should be an image (JPEG or PNG"));    		

        }

        try {
    		var imageUrl = uploadService.uploadImageWithCloudinary(file);
            return ResponseEntity.status(HttpStatus.OK).body(imageUrl);    		

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Failure("An error occurred while uploading image"));
        }       

    	
    }

    @MessageMapping("/delete-post")
    @SendTo("/news-app/get-posts")
    public  ResponseEntity<?>  deletePost(Long postId,Principal principal){

        commentService.deleteCommentByPostId(postId);
        postService.deletePost(postId);

        return ResponseEntity.ok(postService.getPosts(principal));
    }

   @GetMapping("/posts")
    public String postsPage(Model model, Principal principal){

        model.addAttribute("FirstName", authService.getFirstName(principal.getName()));
        return "post";
    }

}

package nehe.sharenews.controllers;

import nehe.sharenews.models.Comment;
import nehe.sharenews.models.CommentRequest;
import nehe.sharenews.models.Post;
import nehe.sharenews.models.User;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.CommentService;
import nehe.sharenews.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class CommentController {

    private CommentService commentService;
    private AuthService authService;
    private PostService postService;


    @Autowired
    public CommentController(CommentService commentService,AuthService authService,PostService postService) {
        this.commentService = commentService;
        this.authService =authService;
        this.postService = postService;
    }

    @MessageMapping("/add-comment")
    @SendTo("/news-app/get-posts")
    public  ResponseEntity<?> addComment(
                             Principal principal,
                             CommentRequest request){

        //user commenting
        var user = authService.findUserByEmail(principal.getName());
        var comment = new Comment();
        comment.setComment(request.getText());
        comment.setUser(user);

        //Which post is user commenting
        var post = new Post("","",new User());
        post.setId(request.getPostId());

        comment.setPost(post);

        commentService.addComment(comment);
        
        return ResponseEntity.ok(postService.getPosts(principal));
    }

    @GetMapping("/post/{postId}/comments")
    public String getComments(@PathVariable Long postId){

        return "redirect:/comments/"+postId;
    }

    @GetMapping("/comments/{postId}")
    public ModelAndView getCommentsPage(@PathVariable Long postId, Model model, Principal principal){

        var comments = commentService.getComments(postId,principal.getName());
        var post = commentService.getPost(postId);

        model.addAttribute("PostViewModel",post);
        model.addAttribute("Comments",comments);
        model.addAttribute("FirstName", authService.getFirstName(principal.getName()));

        return new ModelAndView("comments");
    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable Long postId,@PathVariable Long commentId){

        commentService.deleteCommentByPostIdAndCommentId(commentId);

        return "redirect:/comments/"+postId;
    }
}

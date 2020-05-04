package nehe.sharenews.controllers;

import nehe.sharenews.models.Comment;
import nehe.sharenews.models.Post;
import nehe.sharenews.models.User;
import nehe.sharenews.services.AuthService;
import nehe.sharenews.services.CommentService;
import nehe.sharenews.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CommentController {

    private CommentService commentService;
    private AuthService authService;

    @Autowired
    public CommentController(CommentService commentService,AuthService authService) {
        this.commentService = commentService;
        this.authService =authService;
    }

    @PostMapping("/comment")
    public String addComment(
                             Principal principal,
                             @RequestParam("postId") Long postId,
                             @RequestParam("text") String text){

        //user commenting
        var user = authService.findUserByEmail(principal.getName());

        var comment = new Comment();
        comment.setComment(text);
        comment.setUser(user);

        //Which post is user commenting
        var post = new Post("","",new User());
        post.setId(postId);

        comment.setPost(post);

        commentService.addComment(comment);

        return "redirect:/posts";
    }

    @GetMapping("/post/{postId}/comments")
    public String getComments(@PathVariable Long postId, RedirectAttributes redirectAttributes){

        redirectAttributes.addFlashAttribute("Comments",commentService.getComments(postId));

        return "redirect:/comments";
    }
    @GetMapping("/comments")
    public ModelAndView getCommentsPage(){
        return new ModelAndView("comments");
    }
}

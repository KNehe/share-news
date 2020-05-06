package nehe.sharenews.services;

import nehe.sharenews.models.Comment;
import nehe.sharenews.models.CommentViewModel;
import nehe.sharenews.models.Post;
import nehe.sharenews.models.PostViewModel;
import nehe.sharenews.repositories.CommentRepository;
import nehe.sharenews.repositories.PostRepository;
import nehe.sharenews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void addComment(Comment comment){
        commentRepository.save(comment);
    }

    public List<CommentViewModel> getComments(Long postId, String userEmail){

        var commentsViewModel = new ArrayList<CommentViewModel>();

        var comments = commentRepository.getCommentsByPostId(postId);

        comments.forEach( comment -> {

            var viewModel =  new CommentViewModel();

            //get name of person who commented
            var firstName = userRepository.findFirstName(comment.getUser().getId());
            var lastName = userRepository.findLastName( comment.getUser().getId());
            var name = firstName + " " + lastName;

            viewModel.setId( comment.getId() );
            viewModel.setCommentedBy( name );
            viewModel.setText( comment.getComment() );
            viewModel.setIcon( String.valueOf( firstName.charAt(0) ));

            viewModel.setCanDelete( comment.getUser().getEmail().equals( userEmail ) );

            commentsViewModel.add(viewModel);
        });

        return commentsViewModel;
    }

    public List<PostViewModel> getPost(Long postId){

        var posts = new ArrayList<PostViewModel>();

        var postViewModel = new PostViewModel();
        var post = postRepository.findById(postId).orElse(new Post());

        postViewModel.setPostId(post.getId());

        var count = commentRepository.countById(post.getId());
        var firstName = userRepository.findFirstName( post.getUser().getId() );
        var lastName = userRepository.findLastName( post.getUser().getId() );
        var postByName =  firstName + " " + lastName;

        postViewModel.setPostId(post.getId());
        postViewModel.setDescription(post.getDescription());
        postViewModel.setNoOfComments(count);
        postViewModel.setImage(post.getImage());
        postViewModel.setPostByName( postByName );
        postViewModel.setIcon( String.valueOf( firstName.charAt(0) ) );

        posts.add(postViewModel);

        return  posts;
    }

    public void deleteCommentByPostId(Long postId){
        commentRepository.deleteCommentByPostId(postId);
    }

    public void deleteCommentByPostIdAndCommentId(Long commentId) {
        commentRepository.deleteCommentByPostIdAndCommentId(commentId);
    }
}

package nehe.sharenews.services;

import nehe.sharenews.models.Comment;
import nehe.sharenews.models.CommentViewModel;
import nehe.sharenews.models.Post;
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

    public List<CommentViewModel> getComments(Long postId){

        var commentsViewModel = new ArrayList<CommentViewModel>();

        var post = postRepository.findById(postId).orElse(new Post());
        var comments = commentRepository.getCommentsByPostId(postId);

        comments.forEach( comment -> {

            var viewModel =  new CommentViewModel();

            //get name of person who commented
            var firstName = userRepository.findFirstName(comment.getUser().getId());
            var lastName = userRepository.findLastName( comment.getUser().getId());
            var name = firstName + " " + lastName;

            viewModel.setId( comment.getId() );
            viewModel.setName( name );
            viewModel.setPost( post );
            viewModel.setText( comment.getComment() );

            commentsViewModel.add(viewModel);
        });

        return commentsViewModel;
    }
}

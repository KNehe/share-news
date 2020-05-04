package nehe.sharenews.services;

import nehe.sharenews.models.Post;
import nehe.sharenews.models.PostViewModel;
import nehe.sharenews.repositories.CommentRepository;
import nehe.sharenews.repositories.PostRepository;
import nehe.sharenews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository,CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository =commentRepository;
        this.userRepository = userRepository;
    }

    public Post addPost(Post post){
         return  postRepository.save(post);
    }

    public List<PostViewModel> getPosts(){

         var posts = new ArrayList<PostViewModel>();

         var sort = Sort.by(Sort.Direction.DESC,"id");

         postRepository.findAll(sort).forEach(post ->{

             var postViewModel = new PostViewModel();

             var count = commentRepository.countById(post.getId());
             var firstName = userRepository.findFirstName( post.getUser().getId() );
             var lastName = userRepository.findLastName( post.getUser().getId() );
             var postByName =  firstName + " " + lastName;
             
             postViewModel.setPostId(post.getId());
             postViewModel.setDescription(post.getDescription());
             postViewModel.setNoOfComments(count);
             postViewModel.setImage(post.getImage());
             postViewModel.setPostByName( postByName );

             posts.add(postViewModel);

         });

         return posts;
    }

    public Post getPostById(Long id){
        return postRepository.findById(id).orElse(null);
    }
}

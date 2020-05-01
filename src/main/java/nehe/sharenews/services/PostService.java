package nehe.sharenews.services;

import nehe.sharenews.models.Post;
import nehe.sharenews.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;

     @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post addPost(Post post){
         return  postRepository.save(post);
    }
}

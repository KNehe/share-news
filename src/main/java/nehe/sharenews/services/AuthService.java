package nehe.sharenews.services;

import nehe.sharenews.models.User;
import nehe.sharenews.repositories.CommentRepository;
import nehe.sharenews.repositories.PostRepository;
import nehe.sharenews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private  CommentRepository commentRepository;
    private  PostRepository postRepository;

    @Autowired
    public AuthService(UserRepository userRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public User registerUser(User user ){

        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("User");
        user.setIsEnabled(1);

        return userRepository.save(user);
    }

    public boolean isEmailExists(String email){

        var user  = userRepository.findByEmail( email );

        return user != null ;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail( email );
    }

    public User findUserByToken(String token){
        return userRepository.findByResetPasswordToken(token);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public String getFirstName(String email){
        return userRepository.getFirstNameByEmail(email);
    }


    public String getLastName(String email) {
        return userRepository.getLastNameByEmail(email);
    }

    public User changeEmail(Principal principal,String email) {

        var user = userRepository.findByEmail(principal.getName());
        user.setEmail(email);

        return  userRepository.save(user);
    }

    public User changePassword(Principal principal, String password) {

        var user = userRepository.findByEmail(principal.getName());

        var newPassword = new BCryptPasswordEncoder().encode(password);

        user.setPassword(newPassword);

        return  userRepository.save(user);
    }

    public void deleteAccount(String email) {

        var user = userRepository.findByEmail(email);

        commentRepository.deleteCommentByUserId( user.getId());

        postRepository.deletePostByUserId( user.getId() );

        userRepository.deleteById(user.getId());

    }
}

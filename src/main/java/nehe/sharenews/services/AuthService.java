package nehe.sharenews.services;

import nehe.sharenews.models.User;
import nehe.sharenews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.getFirstName(email);
    }



}

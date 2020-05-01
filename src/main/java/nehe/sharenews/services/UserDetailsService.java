package nehe.sharenews.services;

import nehe.sharenews.repositories.UserRepository;
import nehe.sharenews.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmail( email);

        if ( user == null )
            throw new UsernameNotFoundException( String.format(" User with email { %s } not found ", email ) );

        return new UserPrincipal(user);
    }
}

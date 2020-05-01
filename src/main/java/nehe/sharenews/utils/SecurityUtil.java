package nehe.sharenews.utils;

import nehe.sharenews.models.User;
import nehe.sharenews.security.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public void authenticateUser( User user ,String  unEncryptedPassword){

        user.setPassword(unEncryptedPassword);

        UserPrincipal userPrincipal = new UserPrincipal(user);

        var authentication = new UsernamePasswordAuthenticationToken( userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public  boolean isUserLoggedIn(){

        var auth = SecurityContextHolder.getContext().getAuthentication();

        return !(auth instanceof AnonymousAuthenticationToken);

    }
}

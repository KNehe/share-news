package nehe.sharenews.security;

import nehe.sharenews.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    
    private static final long serialVersionUID = 1L;
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        var grantedAuthorities = new ArrayList<GrantedAuthority>();

        var grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole() );

        grantedAuthorities.add(grantedAuthority);

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsEnabled() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsEnabled() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsEnabled() == 1;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled() == 1;
    }
}

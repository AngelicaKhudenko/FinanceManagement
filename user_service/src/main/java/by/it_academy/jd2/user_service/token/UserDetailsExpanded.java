package by.it_academy.jd2.user_service.token;

import by.it_academy.jd2.user_service.core.enums.EUserStatus;
import by.it_academy.jd2.user_service.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsExpanded implements UserDetails {

    private final UserEntity user;

    public UserDetailsExpanded(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+this.user.getRole().name());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {

        return this.user.getPassword();
    }

    public UUID getUUID() {

        return this.user.getUuid();
    }

    @Override
    public String getUsername() {

        return this.user.getMail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !EUserStatus.DEACTIVATED.equals(this.user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return EUserStatus.ACTIVATED.equals(this.user.getStatus());
    }
}

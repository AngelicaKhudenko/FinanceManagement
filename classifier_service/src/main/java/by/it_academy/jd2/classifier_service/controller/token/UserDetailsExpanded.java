package by.it_academy.jd2.classifier_service.controller.token;

import by.it_academy.jd2.classifier_service.controller.token.enums.EUserStatus;
import by.it_academy.jd2.classifier_service.controller.token.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsExpanded implements UserDetails {

    private final UserDTO user;

    public UserDetailsExpanded(UserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+this.user.getRole().name());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {

        return null;
    }

    public UUID getUUID() {

        return this.user.getUuid();
    }

    public UserDTO getUser() {

        return this.user;
    }

    @Override
    public String getUsername() {

        return this.user.getUuid().toString();
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
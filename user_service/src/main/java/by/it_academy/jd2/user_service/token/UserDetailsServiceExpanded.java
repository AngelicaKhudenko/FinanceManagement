package by.it_academy.jd2.user_service.token;

import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceExpanded implements UserDetailsService {

    private final IUserService userService;

    public UserDetailsServiceExpanded(IUserService userService) {

        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {

        UserEntity entity = this.userService.get(UUID.fromString(uuid));

        return new UserDetailsExpanded(entity);
    }
}

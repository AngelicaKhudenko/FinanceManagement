package by.it_academy.jd2.user_service.token;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolderService {

    public UserDetailsExpanded getUser(){

        return (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package by.it_academy.jd2.user_service.controller.token;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public UserDetailsExpanded getUser(){

        return (UserDetailsExpanded) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

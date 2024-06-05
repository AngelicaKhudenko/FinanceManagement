package by.it_academy.jd2.classifier_service.controller.filter;

import by.it_academy.jd2.classifier_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.classifier_service.core.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtHandler;

    private final String urlUserService = "/api/v1/cabinet/me";

    public JwtFilter(JwtTokenHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {

            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if (!jwtHandler.validate(token)) {

            chain.doFilter(request, response);
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<UserDTO> entity = new RestTemplate().exchange(
                this.urlUserService,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserDTO.class
        );

        UserDTO user = entity.getBody();

        if (user == null) {

            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(entity);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(ResponseEntity<UserDTO> entity) {

        UserDetails userDetails = new UserDetails() {

            private final UserDTO user = entity.getBody();
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {

                if (user.getRole() == null) {
                    throw new IllegalStateException("Не указана роль");
                }
                
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+user.getRole().name());

                return Collections.singleton(authority);
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return user.getMail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        return authentication;
    }
}
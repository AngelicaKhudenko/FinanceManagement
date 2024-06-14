package by.it_academy.jd2.user_service.controller.filter;

import by.it_academy.jd2.user_service.controller.token.UserDetailsExpanded;
import by.it_academy.jd2.user_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.user_service.core.dto.UserDTO;
import by.it_academy.jd2.user_service.model.UserEntity;
import by.it_academy.jd2.user_service.service.api.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenHandler jwtHandler;
    private final IUserService userService;
    private final ConversionService conversionService;

    public JwtFilter(JwtTokenHandler jwtHandler, IUserService userService, ConversionService conversionService) {
        this.jwtHandler = jwtHandler;
        this.userService = userService;
        this.conversionService = conversionService;
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

        UUID uuid = UUID.fromString(jwtHandler.getUUID(token));
        UserEntity entity = this.userService.get(uuid);

        if (entity == null) {

            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = new UserDetailsExpanded(this.conversionService.convert(entity, UserDTO.class));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        authentication.setDetails(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
package ru.itmo.blss1.config.jwt;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itmo.blss1.config.CustomUserDetails;
import ru.itmo.blss1.config.CustomUserDetailsService;
import ru.itmo.blss1.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Log
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        //String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        String token = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION);
        if (token == null)
            logger.info("tocken is null");
        logger.info(jwtProvider.validateToken(token));
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            //logger.info(userLogin);
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            logger.info(auth.getPrincipal());
            logger.info(auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearer = request.getHeader(AUTHORIZATION);
//        log.info(bearer);
//        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
//            return bearer.substring(7);
//        }
//        return null;
//    }
}
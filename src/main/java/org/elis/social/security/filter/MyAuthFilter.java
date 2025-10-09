package org.elis.social.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.security.jwt.JwtUtilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Order(1)
public class MyAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;
    private final JwtUtilities jwtUtilities;

    public MyAuthFilter(UserDetailsService userDetailsService,
                        @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
                        JwtUtilities jwtUtilities) {
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
        this.jwtUtilities=jwtUtilities;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            boolean giaAutenticato = securityContext.getAuthentication()!=null;
            if(authHeader==null||giaAutenticato||!authHeader.startsWith("Bearer "))
            {
                filterChain.doFilter(request,response);
                return;
            }
            String token = authHeader.substring(7);
            String username = jwtUtilities.getUsernameFromToken(token);
            //arrivati qui il token e' stato letto e validato
            //se l'utente non viene trovato vuol dire che il token e' di un utente che non esiste piu'
            UserDetails utente = null;
            try{
                utente = userDetailsService.loadUserByUsername(username);
            }
            catch(NotFoundException e)
            {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "l'utente non e' piu' presente nel db");
            }

            var upat = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
            upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(upat);
            filterChain.doFilter(request,response);
            return;

        }
        catch (Exception e){
            resolver.resolveException(request,response,null,e);
            return;
        }
    }
}

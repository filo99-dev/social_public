package org.elis.social.service.impl;

import lombok.RequiredArgsConstructor;
import org.elis.social.errorhandling.exceptions.NotFoundException;
import org.elis.social.repository.jpa.UtenteRepositoryJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UtenteRepositoryJpa utenteRepositoryJpa;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utenteRepositoryJpa.findByUsername(username).orElseThrow(()->new NotFoundException("utente non trovato per username: "+username));
    }
}

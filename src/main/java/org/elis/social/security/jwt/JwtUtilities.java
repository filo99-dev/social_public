package org.elis.social.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.elis.social.dto.response.utente.ResponseUserDTO;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtilities {
    private static final String KEY = "SDFLKJ23ew0945rj.<ASMDFZXC-9304iul;AS:LD1-2904!@_)$()U$#)(#)$";

    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }

    public String generaToken(ResponseUserDTO utente)
    {
        long dataOggi = System.currentTimeMillis();
        long dataScadenza = dataOggi + 1000L *60*60*24*30;
        return Jwts.builder()
                .subject(utente.getUsername())
                .issuedAt(new Date(dataOggi))
                .expiration(new Date(dataScadenza))
                .claims()
                .add("ruolo",utente.getRole().toString())
                .and()
                .signWith(secretKey())
                .compact();

    }

    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

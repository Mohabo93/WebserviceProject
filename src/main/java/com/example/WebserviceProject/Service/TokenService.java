package com.example.WebserviceProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder; // Injicering av JwtEncoder för kodning av JWT

    @Autowired
    private JwtDecoder jwtDecoder; // Injicering av JwtEncoder för avkodning av JWT

    // Genererar en JWT baserat på autentiseringsinformation
    public String generateJwt(Authentication auth){

        Instant now = Instant.now();

        // Sammanställer behörigheter från autentiseringen
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Skapar JWT-anspråksuppsättning med utgivare, utfärdad tidpunkt, ämne och behörigheter
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("roles", scope)
                .build();

        // Kodar JWT med anspråksuppsättningen och returnerar tokenvärde
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

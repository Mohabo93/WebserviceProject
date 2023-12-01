package com.example.WebserviceProject.Config;


import com.example.WebserviceProject.Utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    // Instans av RSAKeyProperties för att hantera RSA-Keys
    private final RSAKeyProperties keys;

    // Constructor som tar emot RSA-KeyProperties som en parameter
    public SecurityConfiguration(RSAKeyProperties keys){
        this.keys = keys;
    }

    // Bean för att skapa en instans av BCryptPasswordEncoder, används för att koda lösenord
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Bean för att skapa en instans av AuthenticationManager
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService) {

        // Skapar en DaoAuthenticationProvider och konfigurerar den användarinformation och lösenordskodare
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    // Bean för att konfigurera säkerhetsfiltren och reglerna för åtkomstkontroll
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // Inaktiverar CSRF-skydd
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll(); // Tillåter åtkomst till "/auth/**" för alla

                    auth.requestMatchers("/admin/**").hasRole("ADMIN"); // Kräver att användaren har rollen "ADMIN" för "/admin/**"
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER"); // Kräver att användaren har antingen rollen "ADMIN" eller "USER" för "/user/**"

                    auth.anyRequest().authenticated(); // Alla andra förfrågningar kräver autentisering
                });

        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())); // Konfigurerar OAuth 2.0-resursservern med JWT
        http.sessionManagement( // Konfigurerar sessionshantering för att vara "STATELESS"
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return http.build();
    }

    // Bean för att skapa en instans av JwtDecoder för att dekryptera JWT
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    // Bean för att skapa en instans av JwtEncoder för att kryptera JWT
    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    // Bean för att skapa en instans av JwtAuthenticationConverter för att konvertera JWT till Authentication
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        //Skapar en JwtGrantedAuthoritiesConverter för att extrahera roller från JWT
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        // Skapar en JwtAuthenticationConverter och konfigurerar den med JwtGrantedAuthoritiesConverter
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter; // Returnerar den skapade JwtAuthenticationConverter
    }
}
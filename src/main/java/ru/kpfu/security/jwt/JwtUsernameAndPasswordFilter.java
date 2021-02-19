package ru.kpfu.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
//First filter implementation
public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
    //Autowired from SecurityConfig
    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Method which get user data from request and validate it
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            //mapping request object to UserAndPasswordObject
            UsernameAndPasswordRequest usernameAndPasswordRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordRequest.class);
            //Creating authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernameAndPasswordRequest.getUsername(),
                    usernameAndPasswordRequest.getPassword()
            );
            //Method which return authenticated user object
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //Secret key for token
        String key = "loremloremloremloremloremloremloremloremlorem";
        //Token creation
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        //adding token in response header
        response.addHeader("Authorization", "Bearer " + token);
    }
}

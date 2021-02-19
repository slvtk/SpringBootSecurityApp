package ru.kpfu.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.security.auth.UserService;
import ru.kpfu.security.jwt.JwtUsernameAndPasswordFilter;


@Configuration
//Tell that its security configuration
@EnableWebSecurity
//Enable annotation security
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    //main security configuration method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //disabling csrf because we have api based application
                .csrf().disable()
                //AuthenticationManager from WebSecurityAdapter
                .addFilter(new JwtUsernameAndPasswordFilter(authenticationManager()))
                //after that annotation we allow some urls
                .authorizeRequests()

                .antMatchers("/", "index", "/css/*", "/js/*", "/img/*").permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll().defaultSuccessUrl("/students")
//                .and()
//                .rememberMe().key("uniqueAndSecret")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .and()
//                .authenticationProvider(daoAuthenticationProvider());
    }
//For form-based authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}

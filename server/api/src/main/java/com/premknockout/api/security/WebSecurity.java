package com.premknockout.api.security;

import com.premknockout.api.security.token.AuthenticationEntryPoint;
import com.premknockout.api.security.token.TokenAuthenticationFilter;
import com.premknockout.api.service.securityUser.SecurityUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserServiceImpl securityUserService;

    @Value("${authentication-test.auth.address}")
    private String address;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .cors().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .csrf()
                .csrfTokenRepository(new CookieCsrfTokenRepository().withHttpOnlyFalse())
                .ignoringAntMatchers("/api/auth/onboarding/")
                .ignoringAntMatchers("/api/user/signup/")
                .ignoringAntMatchers("/api/images/**")

                .and()
                .formLogin().disable()

                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/logout")
                .deleteCookies("accessToken")
                .deleteCookies("refreshToken")

                .and()

                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint()).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**", "/api/user/mutation/create/", "/api/images/**", "/api/helper/types/**").permitAll()
                .antMatchers("/api/**", "/internal/**").authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}

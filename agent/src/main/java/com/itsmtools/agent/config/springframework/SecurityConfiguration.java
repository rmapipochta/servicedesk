package com.itsmtools.agent.config.springframework;


import com.itsmtools.common.service.security.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.itsmtools.common.service.security.DbUserDetailsService.Role;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private DbUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void autowiredUserDetailsService(DbUserDetailsService dbUserDetailsService){
        dbUserDetailsService.setRequiredApplicationRoles(Role.AGENT);
        dbUserDetailsService.setRequiredGroupRoles(
            Role.AGENT_ADMIN,
            Role.AGENT_MANAGER,
            Role.AGENT_OPERATOR,
            Role.AGENT_PERFORMER
        );
        userDetailsService = dbUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login", "/public/**")
            .permitAll()
            .antMatchers("/**")
            .authenticated()
            .and()

            .csrf()
            .disable()
            .formLogin()
            .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .loginProcessingUrl("/auth")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .and()

            .logout()
            .logoutSuccessUrl("/login")
            .logoutUrl("/logout")
            .and()

            .exceptionHandling();
    }
}
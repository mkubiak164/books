package pl.jstk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("first_user").password("admin").roles("ADMIN")
                .and()
                .withUser("second_user").password("user").roles("USER");;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/","/books")
        .permitAll();
    }

}


/*
@Overrideprotected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.authorizeRequests().antMatchers("/","/products","/product/show/*","/console/**")
        .permitAll()                .anyRequest().authenticated()                .and()
        .formLogin().loginPage("/login").permitAll()                .and()
        .logout().permitAll();    httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
        }
*/

package pl.jstk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/books", "/books/", "/books/add*", "/search", "/webjars/**", "/img/*", "/css/*", "/books/delete*").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .failureUrl("/loginError")
                .successForwardUrl("/loginSuccess")
                .and()
                .logout().permitAll();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("user1").password("{noop}user1").roles("USER");

        /*auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username,password, enabled from UserEntity where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");*/
    }
}

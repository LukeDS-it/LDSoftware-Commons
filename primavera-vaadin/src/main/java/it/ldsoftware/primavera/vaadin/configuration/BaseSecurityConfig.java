package it.ldsoftware.primavera.vaadin.configuration;

import it.ldsoftware.primavera.services.DummyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.vaadin.spring.security.VaadinSecurityContext;
import org.vaadin.spring.security.web.VaadinDefaultRedirectStrategy;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;
import org.vaadin.spring.security.web.authentication.SavedRequestAwareVaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.authentication.VaadinAuthenticationSuccessHandler;

import javax.annotation.PostConstruct;

import static it.ldsoftware.primavera.util.UserUtil.ROLE_DB_CONSOLE;
import static it.ldsoftware.primavera.util.UserUtil.ROLE_SUPERADMIN;

/**
 * This class provides basic security configuration. To add custom mappings
 * override the <code>configure(HttpSecurity http)</code> method.
 * <p>
 * The base configuration specifies a dummy user detail service that logs in the users as follow:
 * <ul>
 * <li>user/user: simple user with ROLE_USER</li>
 * <li>admin/admin: administrator with ROLE_ADMIN</li>
 * <li>inactive/inactive: user that is inactive</li>
 * <li>expired/expired: user with expired credentials</li>
 * </ul>
 * To override this service, override the function <code>getUserDetailService()</code>.
 * This class also configures a password encoder with the BCrypt algorithm, which has proven to be
 * one of the most reliable against brute force attacks.
 *
 * @author Luca
 */
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    VaadinSecurityContext vaadinSecurityContext;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(getUserDetailService())
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Override this function to add custom configuration
     * and authorizations
     * @param http use classical {@link HttpSecurity} methods.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/").anonymous()
                .antMatchers("/login/**").anonymous()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                .antMatchers(CommonsVaadinConfig.ADDRESS_DB_CONSOLE + "/*").hasAnyAuthority(ROLE_DB_CONSOLE, ROLE_SUPERADMIN)
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/VAADIN/**");
    }

    /**
     * In order to use the application with normal users, override this function
     * and return the custom user details service
     *
     * @return in the base security configuration returns a dummy user detail service
     * with the following users: <ul>
     * <li>user - user (user with only user permission)</li>
     * <li>admin - admin (user with only superadmin permission)</li>
     * </ul>
     * This allows to better test your program with different user types
     */
    public UserDetailsService getUserDetailService() {
        return new DummyUserDetailService();
    }

    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        this.vaadinSecurityContext.addAuthenticationSuccessHandler(redirectSaveHandler());
    }

    /*
     * The HttpSessionRequestCache is where the initial request before redirect
     * to the login is cached so it can be used after successful login
     */
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    /*
     * The RequestCacheAwareFilter is responsible for storing the initial
     * request
     */
    @Bean
    public RequestCacheAwareFilter requestCacheAwareFilter() {
        return new RequestCacheAwareFilter(requestCache());
    }

    /*
     * The VaadinRedirectStrategy
     */
    @Bean
    public VaadinRedirectStrategy vaadinRedirectStrategy() {
        return new VaadinDefaultRedirectStrategy();
    }

    @Bean
    public VaadinAuthenticationSuccessHandler redirectSaveHandler() {
        SavedRequestAwareVaadinAuthenticationSuccessHandler handler = new SavedRequestAwareVaadinAuthenticationSuccessHandler();

        handler.setRedirectStrategy(vaadinRedirectStrategy());
        handler.setRequestCache(requestCache());
        handler.setDefaultTargetUrl("/");
        handler.setTargetUrlParameter("r");

        return handler;
    }
}

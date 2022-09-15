package com.myproject.test.myproject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/static/**", "/dist/**", "/css/**", "/fonts/**", "/js/**", "/images/**", "/ie8-panel/**").permitAll()

                .antMatchers("/home").hasAuthority("USER")
                .antMatchers("/admin/register").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    //    @Autowired
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @Autowired
//    private LoggingAccessDeniedHandler loggingAccessDeniedHandler;
//
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public HttpFirewall defaultHtttpFirewall() {
//        return new DefaultHttpFirewall();
//    }
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception{
//
//        httpSecurity.authorizeRequests()
//                .antMatchers("/static/**","/dist/**","/css/**","/fonts/**","/js/**","/images/**","/ie8-panel/**").permitAll()
////                .antMatchers("../static/dist/css/login.css").permitAll()
//                .antMatchers("/admin/register", "/admin","/admin/registeruser").permitAll()
//                .antMatchers("/home","/").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll()
//                .successForwardUrl("/loginsuccess")
//                .and().logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").permitAll()
//                .and().exceptionHandling().accessDeniedHandler(loggingAccessDeniedHandler);
//
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
//    }


}

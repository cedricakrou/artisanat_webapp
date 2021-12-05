package com.cedricakrou.artisanat.application.configuration.security

import com.cedricakrou.artisanat.application.configuration.security.handler.AuthenticationFailureHandler
import com.cedricakrou.artisanat.application.configuration.security.handler.AuthenticationSuccessHandler
import com.cedricakrou.artisanat.application.configuration.security.handler.LogOutSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val userDetailsServiceImplementation : UserDetailsServiceImplementation,
                        private val authenticationSuccessHandler : AuthenticationSuccessHandler,
                        private val authenticationFailureHandler: AuthenticationFailureHandler,
                        private val logoutSuccessHandler : LogOutSuccessHandler
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.
                csrf()
                    .ignoringAntMatchers("/api/v1/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/home/**", "/email/**", "/api/v1/**", "/account/**", "/backend/artisan/save"  ).permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/account/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler( authenticationSuccessHandler )
                    .failureHandler( authenticationFailureHandler )
                    .defaultSuccessUrl("/backend", true)
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessHandler( logoutSuccessHandler )
                    .logoutRequestMatcher( AntPathRequestMatcher( "/account/logout" ) )
                    .logoutSuccessUrl("/account/login")
                    .permitAll()



    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        super.configure(web)

        web?.ignoring()?.antMatchers(
            "/assets/**",
            "/frontend/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/webjars/**"
        )

    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {

        auth.userDetailsService(userDetailsServiceImplementation).passwordEncoder( BCryptPasswordEncoder() )


    }



}
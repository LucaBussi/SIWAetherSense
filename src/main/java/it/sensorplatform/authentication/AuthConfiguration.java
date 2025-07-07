package it.sensorplatform.authentication;

import javax.sql.DataSource;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.sensorplatform.failurehandler.CustomAuthenticationFailureHandler;
import it.sensorplatform.successhandler.CustomLoginSuccessHandler;

import static it.sensorplatform.model.Credentials.ADMIN_ROLE;
import static it.sensorplatform.model.Credentials.LTRAD_ROLE;
import static it.sensorplatform.model.Credentials.FIRE_ROLE;
import static it.sensorplatform.model.Credentials.VOLCANO_ROLE;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfiguration {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Lazy
	private CustomLoginSuccessHandler successHandler;
	
	@Autowired
	@Lazy
	private CustomAuthenticationFailureHandler failureHandler;


	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


		http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/", "/home", "/login", "/register", "/access", "/css/**", "/img/**", "/favicon.ico", "/videos/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
				.requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(ADMIN_ROLE)
				.requestMatchers(HttpMethod.GET, "/ltrad/**").hasAuthority(LTRAD_ROLE)
				.requestMatchers(HttpMethod.POST, "/ltrad/**").hasAuthority(LTRAD_ROLE)
				.requestMatchers(HttpMethod.GET, "/fire/**").hasAuthority(FIRE_ROLE)
				.requestMatchers(HttpMethod.POST, "/fire/**").hasAuthority(FIRE_ROLE)
				.requestMatchers(HttpMethod.GET, "/volcano/**").hasAuthority(VOLCANO_ROLE)
				.requestMatchers(HttpMethod.POST, "/volcano/**").hasAuthority(VOLCANO_ROLE)
				.anyRequest().authenticated()
				)
		.formLogin(form -> form
			    .loginPage("/login")
			    .loginProcessingUrl("/login") 
			    .successHandler(successHandler)
			    .failureHandler(failureHandler) 
			    .permitAll()
			)
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
				)
		.exceptionHandling(exception -> exception
				.accessDeniedPage("/home")
				);

		return http.build();
	}
}
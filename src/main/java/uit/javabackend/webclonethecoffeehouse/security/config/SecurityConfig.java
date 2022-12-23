package uit.javabackend.webclonethecoffeehouse.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import uit.javabackend.webclonethecoffeehouse.security.jwt.JwtAuthenticationFilter;
import uit.javabackend.webclonethecoffeehouse.security.oauth.CustomOAuth2UserService;
import uit.javabackend.webclonethecoffeehouse.security.oauth.RestAuthenticationEntryPoint;
import uit.javabackend.webclonethecoffeehouse.security.oauth.handler.OAuth2AuthenticationSuccessHandler;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter authenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private ListAllowedOriginConfig listAllowedOriginConfig;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter authenticationFilter, CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CROSS ORIGIN
        http.cors();
        http.csrf().disable();
        // DISABLE SESSION -> STATELESS
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT Filter
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // API Authentication
        http
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/", "/login/**", "/oauth2/**", "/oauth/**", "/auth/**", "/v3/api-docs/**", "/swagger-ui.html/**","/swagger-resources/**", "/swagger-ui/**","/webjars/**").permitAll()
                .antMatchers( "/api/ProductsManagement/common/**", "/api/ProductGroupManagement/common/**", "/api/CurrencyMangement/common/**").permitAll()
                .antMatchers( "/api/v1/business/common/**","/api/v1/discount/common/**").permitAll()
                .antMatchers( "/api/v1/payment/billing-infomation").permitAll()
                .antMatchers( "/api/Files/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/js/**", "/images/**");
    }


}

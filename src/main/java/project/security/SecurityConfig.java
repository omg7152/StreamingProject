package project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //rest api이므로 basic auth 및 csrf 보안을 사용하지 않는다는 설정
                .httpBasic().disable().csrf().disable()
                //JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //antMatchers("호출URL").permitAll() : 해당 API에 대해서는 모든 요청을 허가한다는 설정
                .antMatchers("/css/**", "/font/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/members/login").permitAll()
                //antMatchers().hasRole("권한명"): 특정 권한이 있어야 요청할 수 있다는 설정
                .antMatchers("/members/test").hasRole("USER")
                //anyRequest().authenticated(): 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

package com.microservices.userservice.Security;

import com.microservices.userservice.Filter.JwtFilter;
import com.microservices.userservice.Provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    // Tạo ra chuẩn mã hóa password là lưu trữ ở IOC
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomAuthenProvider customAuthenProvider;

    // Chỉ định AuthenticationManager sử dụng CustomAuthenProvider
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Tắt tấn công theo kiểu cross-site (gọi link theo nhiều trình duyệt khác nhau)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không sử dụng session --- STATELESS: Không sử dụng cái gì hết
                .and()
                .authorizeHttpRequests()

                // các thằng dưới là con của thằng trên ----- Matchers là so sánh kiểm tra dữ liệu
                //permitALl() : nếu có .per thì link này ai gọi cũng được tất cả (ALL)
                .requestMatchers("/**").permitAll()


                /*.requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN") // link /product với phương thức POST phải có role ADMIN mới truy cập được
                .requestMatchers(HttpMethod.GET, "/product").permitAll()
                .requestMatchers(HttpMethod.PUT, "/product").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/cart").hasRole("USER")*/


                .anyRequest().authenticated()// Tất cả các link còn lại cần phải chứng thực
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin((form) -> form
                        .loginPage("/login/signin")
                        .usernameParameter("user")
                        .passwordParameter("pass")
                        .successHandler((request, response, authentication) -> {
                            // Kiểm tra role của người dùng và chuyển hướng tương ứng
                            if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
                                response.sendRedirect("/admin");
                            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USER") || r.getAuthority().equals("MEMBER"))) {
                                response.sendRedirect("/");
                            } else {
                                // Nếu không có role nào khớp, chuyển hướng mặc định
                                response.sendRedirect("/");
                            }
                        })
                        .failureUrl("/login/signin?error=true")
                        .permitAll()
                )
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login/signin")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                //declare exeption
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        return http.build();
    }
}

//package com.shop.shop.config;
//
//import com.shop.shop.service.userDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig1 extends WebSecurityConfigurerAdapter{
//
//    @Autowired
//    userDetailsServiceImpl detailsService;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        // Sét đặt dịch vụ để tìm kiếm User trong Database.
//        // Và sét đặt PasswordEncoder.
//        auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
//
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        // Các yêu cầu phải login với vai trò ROLE_EMPLOYEE hoặc ROLE_MANAGER.
//        // Nếu chưa login, nó sẽ redirect tới trang /admin/login.
//        http.authorizeRequests().antMatchers("/cart/**","/order/**","/profile")//
//                .access("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')");
//
//        // Các trang chỉ dành cho MANAGER
//        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_MANAGER')");
//
//        // Khi người dùng đã login, với vai trò XX.
//        // Nhưng truy cập vào trang yêu cầu vai trò YY,
//        // Ngoại lệ AccessDeniedException sẽ ném ra.
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//
//        // Cấu hình cho Login Form.
//        http.authorizeRequests().and().formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/home")//
//                .failureUrl("/login?error=true")
//                .and().logout().logoutUrl("/logout");
//
//    }
//}
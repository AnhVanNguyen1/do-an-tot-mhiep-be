package com.shop.shop.config;

import com.google.gson.Gson;
import com.shop.shop.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {


    protected JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
        setAuthenticationManager(authenticationManager);
    }

    // Thực hiện kiểm tra dữ liệu email, password client gửi lên để kiểm tra
    // trả về 1 đối tượng Authentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        User user = new Gson().fromJson(httpServletRequest.getReader(), User.class);
        String email = user.getEmail();
        String password = user.getPassword();
        try {
            System.out.println("JWTLoginFilter.attemptAuthentication: username = " + email);
            return getAuthenticationManager().
                    authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (AuthenticationException e){
           String message = TokenAuthenticationService.checkEmailPassword(email,password);
            throw new AuthenticationException(message) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        System.out.println("JWTLoginFilter.successfulAuthentication: username = " + userDetails.getUsername());
        //Gọi sevice để tạo token và gửi về client
        TokenAuthenticationService.addAuthentication(response, userDetails.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        System.out.println("JWTLoginFilter.unsuccessfulAuthentication: exception = "+failed.getMessage());
        TokenAuthenticationService.unsuccessfulAuthentication(request, response, failed);
//        logger.info("Logout");
    }
}

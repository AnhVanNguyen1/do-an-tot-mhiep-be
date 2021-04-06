package com.shop.shop.config;

import com.google.gson.Gson;
import com.shop.shop.common.AppConstant;
import com.shop.shop.entity.User;
import com.shop.shop.repository.AccountRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import response.LoginResponse;
import response.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
public class TokenAuthenticationService {

    private static AccountRepository accountRepository;

    private static Gson gson;

    //Lấy bean từ aplication context để dùng
    public TokenAuthenticationService(ApplicationContext ctx) {
        accountRepository = ctx.getBean(AccountRepository.class);
    }

    //Thực hiện tạo ra chuỗi JWT và đưa vào response để trả về cho người dùng
    public static void addAuthentication(HttpServletResponse res, String email) {
        try {
            gson = new Gson();
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + AppConstant.EXPIRATION_TIME_MS);
            String jwt = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, AppConstant.SECRET).compact();

            User user = accountRepository.findByEmail(email);
            User update = accountRepository.save(user);
            UserDto userDto = UserDto.fromEntity(update);

            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.addHeader("Authorization", jwt);
            res.getWriter().write(gson.toJson(new LoginResponse<>("200", "Login success", userDto, jwt)));
        } catch (Exception e) {
            log.error("TokenAuthenticationService.addAuthentication: Error :" + e);
        }
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            try {
                String user = Jwts.parser()
                        .setSigningKey(AppConstant.SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                return user != null
                        ? new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        : null;
            } catch (ExpiredJwtException e) {
                System.out.println("ExpiredJwtException");
            } catch (SignatureException e) {
                System.out.println("SignatureException");
            } catch (Exception e) {
                System.out.println("JWT parsing error");
            }
        }
        return null;
    }

    public static void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                                  AuthenticationException failed) {
        try {
            gson = new Gson();
            String respLogin;
            if (failed instanceof DisabledException) {
                respLogin = failed.getMessage();
            } else {
                respLogin = failed.getMessage();
            }
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(gson.toJson(new LoginResponse<>("401", respLogin, null, null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String checkEmailPassword(String userName, String password) {
        User user = accountRepository.findByEmail(userName);
        String bcryPassword = new BCryptPasswordEncoder().encode(password);
        if (user != null) {
            if (!user.getPassword().equals(bcryPassword)) {
                String msg = "Your email or password is incorrect. Please try enter!";
                accountRepository.save(user);
                return msg;
            }
        } else {
            return "Your email or password is incorrect. Please try enter!";
        }
        return null;
    }
}

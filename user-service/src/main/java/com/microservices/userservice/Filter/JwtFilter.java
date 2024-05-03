package com.microservices.userservice.Filter;

import com.microservices.userservice.Helper.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
//OncePerRequestFilter : Có request yêu cầu chứng thực thì chạy vào filter
public class JwtFilter extends OncePerRequestFilter {
    //    Bước 1 : Lấy token
//    Bước 2 : Giải mã token
//    Bước 3 : Token hợp lệ tạo chứng thực cho Security
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //        Lấy giá trị trên header
        String header = request.getHeader("Authorization");

//      Kiểm tra token lấy được xem có thể do hệ thống sinh ra hay không
        try{
            //        Cắt chuỗi bỏ chữ Bearer để lấy đúng token
            String token = header.substring(7);
            String data = jwtHelper.validToken(token);
//            Nếu token khác rỗng tức là hợp lệ thì tạo chứng thực
            if(!data.isEmpty()){
//          Tạo chứng thực cho Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken("","",new ArrayList<>());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authenticationToken);
//
            }
            System.out.println("Kiem tra " + data);
        }catch (Exception e){
//            Token không hợp lệ
            //System.out.println("token không hợp lệ");
        }

//        Cho phép đi vào link người dùng muốn truy cập
        filterChain.doFilter(request,response);
    }
    }

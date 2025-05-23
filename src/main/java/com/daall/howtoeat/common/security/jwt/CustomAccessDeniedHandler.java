package com.daall.howtoeat.common.security.jwt;

import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.ExceptionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionResponseDto exceptionDto = new ExceptionResponseDto(ErrorType.NOT_AVAILABLE_PERMISSION);

        response.setStatus(exceptionDto.getErrorType().getHttpStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDto));
        response.setCharacterEncoding("UTF-8");
    }
}

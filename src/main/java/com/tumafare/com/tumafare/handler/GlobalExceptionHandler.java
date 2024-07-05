package com.tumafare.com.tumafare.handler;

import jakarta.mail.MessagingException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

import static com.tumafare.com.tumafare.handler.BusinessErrorCode.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ModelAndView handleException(LockedException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(DisabledException.class)
    public ModelAndView handleException(DisabledException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView handleException(BadCredentialsException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorCode(BAD_CREDENTIALS.getCode())
                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                .error(BAD_CREDENTIALS.getDescription())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException(AccessDeniedException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(MessagingException.class)
    public ModelAndView handleException(MessagingException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exp, Model model) {
        exp.printStackTrace();
        ExceptionResponse response = ExceptionResponse.builder()
                .businessErrorDescription("Internal error, please contact the admin")
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleException(UsernameNotFoundException exp, Model model) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error(exp.getMessage())
                .build();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("response", response);
        return modelAndView;
    }
}

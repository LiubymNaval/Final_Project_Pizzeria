package sk.ukf.pizzeria.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Stránka nenájdená
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404() {
        return "error/404";
    }

    // 403 - Prístup odmietnutý (Security)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String handle403() {
        return "error/403";
    }

    // 500 - Chyba servera
    @ExceptionHandler(Exception.class)
    public String handle500(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}

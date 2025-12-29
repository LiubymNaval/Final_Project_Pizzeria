package sk.ukf.pizzeria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 404
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ObjectNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
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

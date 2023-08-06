package kr.co.mkm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorController {
    private final static String EXCEPTION_HANDLER = "EXCEPTION_HANDLER";

    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFoundException() {
        return "common/error/notFoundError";// view name for 404 error
    }

    private Map<String, Object> makeExceptionAttribute(
            HttpServletRequest request, HttpStatus status, Exception e, String message){

        Map<String, Object> map = new HashMap<>();
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(e.getMessage());
        if(message != null){
            errorMessage.append("<br>").append(message);
        }

        map.put("errorCode", status.value());
        map.put("errorCodeString", status.getReasonPhrase());
        map.put("errorClass", e.getClass().getName());
        map.put("errorMessage", e.getMessage());

        if(request != null){
            request.setAttribute(EXCEPTION_HANDLER, map);
        }
        return map;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return "common/error/notFoundError";// view name for 404 error
    }

//    @RequestMapping("common/error/databaseError")
//    public String databaseError() {
//        return "common/error/databaseError";
//    }
//
//    @RequestMapping("common/error/securityError")
//    public String securityError() {
//        return "common/error/securityError";
//    }
//
//    @RequestMapping("common/error/notFoundError")
//    public String notFoundError() {
//        return "common/error/notFoundError";
//    }
//
//    @RequestMapping("common/error/error")
//    public String error() {
//        return "common/error/notFoundError";
//    }
}

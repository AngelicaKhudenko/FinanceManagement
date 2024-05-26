package by.it_academy.jd2.finance_management.user_service.controller.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LogManager.getLogger();

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleIllegal(IllegalArgumentException e) {

        logger.log(Level.WARN, "Пользователь передал некорректные данные",e);

        Map<String,Object> errorObject = new HashMap<>();
        errorObject.put("error",e.getMessage());

        return new ResponseEntity<>(errorObject,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handleException(Exception e) {

        logger.log(Level.ERROR, "Ошибка на сервере",e);

        Map<String,Object> errorObject = new HashMap<>();
        errorObject.put("error",e.getMessage());

        return new ResponseEntity<>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

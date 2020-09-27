package com.udacity.jdnd.course3.critter;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class EntityControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleMaxSizeException(
            EntityNotFoundException exc) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
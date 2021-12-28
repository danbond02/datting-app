package edu.kpi.iasa.mmsa.userservice.api;

import edu.kpi.iasa.mmsa.userservice.api.dto.ErrorDto;
import edu.kpi.iasa.mmsa.userservice.exceptions.IllegalPhoneNumberException;
import edu.kpi.iasa.mmsa.userservice.exceptions.IllegalSexException;
import edu.kpi.iasa.mmsa.userservice.exceptions.NoUsersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class HandleController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public HandleController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = {NoUsersException.class})
    protected ResponseEntity<ErrorDto> handleNoUsersException(){
        ErrorDto error = ErrorDto.builder().code("BAD_REQUEST").description("No users yet.").build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {IllegalSexException.class})
    protected ResponseEntity<ErrorDto> handleIllegalSexException(){
        ErrorDto error = ErrorDto.builder().code("BAD_REQUEST").description("Illegal sex symbol.").build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {IllegalPhoneNumberException.class})
    protected ResponseEntity<ErrorDto> handleIllegalPhoneNumberException(){
        ErrorDto error = ErrorDto.builder().code("BAD_REQUEST").description("Illegal phone number.").build();
        return ResponseEntity.badRequest().body(error);
    }
}

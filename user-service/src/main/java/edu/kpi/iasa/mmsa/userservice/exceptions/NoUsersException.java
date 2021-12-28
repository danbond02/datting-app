package edu.kpi.iasa.mmsa.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no users yet")
public class NoUsersException extends RuntimeException{
}

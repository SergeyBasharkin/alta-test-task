package ru.alta.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "vacancy not found")
public class VacancyNotFoundException extends RuntimeException{
}

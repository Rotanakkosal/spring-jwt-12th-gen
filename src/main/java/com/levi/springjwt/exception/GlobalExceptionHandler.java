package com.levi.springjwt.exception;

import com.levi.springjwt.model.dto.response.ApiErrorDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
    @ExceptionHandler(EmailDuplicatedException.class)
    public ProblemDetail handleEmailDuplicatedException(EmailDuplicatedException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(DataBadRequestException.class)
    public ProblemDetail handleEmailDuplicatedException(DataBadRequestException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    public ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException ex,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatusCode status,
            @NonNull final WebRequest request
    ) {
//        log.info(ex.getMessage(), ex);

        List<ApiErrorDetails> errors = new ArrayList<>();

        for (final ObjectError err : ex.getBindingResult().getAllErrors()) {
            errors.add(
                    ApiErrorDetails.builder()
                            .pointer(((FieldError) err).getField())
                            .reason(err.getDefaultMessage())
                            .build());
        }

        return ResponseEntity.status(BAD_REQUEST)
                .body(this.buildProblemDetail(BAD_REQUEST, "Validation failed.", errors));
    }
    //  problem details with status detail and errors
    private ProblemDetail buildProblemDetail(final HttpStatus status, final String detail,final List<ApiErrorDetails> errors){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, StringUtils.normalizeSpace(detail));
//        following RFC 9457 best practices. (Request for Comments: 9457) released on july 2023
//        RFC 9457 is a standardised format for error responses in HTTP APIs
        if(!CollectionUtils.isEmpty(errors)){
            problemDetail.setProperty("errors", errors);
        }
        return problemDetail;
    }
}

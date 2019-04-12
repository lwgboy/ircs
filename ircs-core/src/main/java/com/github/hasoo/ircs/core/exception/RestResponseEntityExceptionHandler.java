package com.github.hasoo.ircs.core.exception;

import com.github.hasoo.ircs.core.controller.MessageController;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.github.hasoo.ircs.core.dto.MessageResponse;

@ControllerAdvice(assignableTypes = MessageController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return new ResponseEntity<Object>(new MessageResponse("", ex.toString(), "3000"), status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();

    List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
        .map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getCode(),
            fieldError.getRejectedValue()))
        .collect(Collectors.toList());

    List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream()
        .map(globalError -> new ApiGlobalError(globalError.getCode())).collect(Collectors.toList());

    ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors);

    return new ResponseEntity<>(apiErrorsView, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}

package br.com.cwi.crescer.usuarios.excpetions.handler;


import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.excpetions.domain.ExceptionDetalhes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ExceptionDetalhes> handlerNegocioException(ResponseStatusException e, HttpServletRequest httpServletRequest) {

        ExceptionDetalhes exceptionDetalhes = ExceptionDetalhes.builder()
                .titulo("Problema encontrado, verifique a menssagem e o status")
                .message(e.getReason())
                .path(httpServletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(e.getStatus().value()).build();

        return new ResponseEntity<>(exceptionDetalhes, e.getStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetalhes> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {

        List<FieldError> todosErros = e.getBindingResult().getFieldErrors();
        List<String> camposComErros = todosErros.stream().map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage()).collect(Collectors.toList());

        ExceptionDetalhes exceptionDetails = ExceptionDetalhes.builder()
                .titulo("Verificar campos da request")
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getServletPath())
                .campos(camposComErros)
                .status(HttpStatus.BAD_REQUEST.value()).build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetalhes> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest httpServletRequest) {

        ExceptionDetalhes exceptionDetalhes = ExceptionDetalhes.builder()
                .titulo("Verifique seus campos e tente novamente")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getServletPath())
                .status(HttpStatus.BAD_REQUEST.value()).build();

        return new ResponseEntity<>(exceptionDetalhes, HttpStatus.BAD_REQUEST);
    }

}

package br.com.cwi.crescer.usuarios.excpetions.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDetalhes {

    private String titulo;
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private List<String> campos;
}
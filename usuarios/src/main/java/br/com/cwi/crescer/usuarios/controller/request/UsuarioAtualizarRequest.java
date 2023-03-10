package br.com.cwi.crescer.usuarios.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioAtualizarRequest {

    @NotBlank
    private String nomeCompleto;

    @NotBlank
    @URL
    private String foto;
}

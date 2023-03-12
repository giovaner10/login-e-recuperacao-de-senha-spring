package br.com.cwi.crescer.usuarios.controller.request.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailSolicitarRequest {

    @NotBlank @Email
    private String email;
}

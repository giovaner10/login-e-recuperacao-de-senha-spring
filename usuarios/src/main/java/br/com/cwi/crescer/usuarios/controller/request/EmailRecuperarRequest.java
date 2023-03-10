package br.com.cwi.crescer.usuarios.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EmailRecuperarRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 6)
    private String novaSenha;
}

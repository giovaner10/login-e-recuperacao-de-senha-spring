package br.com.cwi.crescer.usuarios.controller.request.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    private String nomeCompleto;

    @NotNull
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String foto;

    @NotNull
    private Boolean isAdmin;
}

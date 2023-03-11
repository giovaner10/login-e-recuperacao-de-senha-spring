package br.com.cwi.crescer.usuarios.controller.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String foto;
    private List<String> permissoes = new ArrayList<>();
}

package br.com.cwi.crescer.usuarios.controller.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nomeCompleto;
    private String email;
    private List<String> permissoes = new ArrayList<>();
}

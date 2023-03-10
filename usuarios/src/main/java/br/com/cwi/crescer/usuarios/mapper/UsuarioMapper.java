package br.com.cwi.crescer.usuarios.mapper;

import br.com.cwi.crescer.usuarios.controller.request.UsuarioRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.domain.Permissao;
import br.com.cwi.crescer.usuarios.domain.Usuario;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setFoto(request.getFoto());
        entity.setAtivo(true);
        entity.setCriadoEm(LocalDateTime.now());

        return entity;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        return UsuarioResponse.builder()
                .id(entity.getId())
                .nomeCompleto(entity.getNomeCompleto())
                .email(entity.getEmail())
                .permissoes(buildPermissoesResponse(entity.getPermissoes()))
                .build();
    }

    private static List<String> buildPermissoesResponse(List<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> permissao.getFuncao().getRole())
                .collect(toList());
    }
}


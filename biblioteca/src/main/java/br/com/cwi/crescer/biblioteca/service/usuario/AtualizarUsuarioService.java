package br.com.cwi.crescer.biblioteca.service.usuario;

import br.com.cwi.crescer.biblioteca.controller.request.UsuarioAtualizarRequest;
import br.com.cwi.crescer.biblioteca.controller.response.UsuarioResponse;
import br.com.cwi.crescer.biblioteca.domain.Usuario;
import br.com.cwi.crescer.biblioteca.repository.UsuarioRepository;
import br.com.cwi.crescer.biblioteca.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static br.com.cwi.crescer.biblioteca.mapper.UsuarioMapper.toResponse;


@Service
public class AtualizarUsuarioService {

    @Autowired
    private BuscarValidarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public UsuarioResponse atualizar(Long idUsuario, UsuarioAtualizarRequest request) {

        Usuario usuario = buscarUsuarioService.porId(idUsuario);

        usuario.setFoto(request.getFoto());
        usuario.setNomeCompleto(request.getNomeCompleto());

        usuario.setEditadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

}

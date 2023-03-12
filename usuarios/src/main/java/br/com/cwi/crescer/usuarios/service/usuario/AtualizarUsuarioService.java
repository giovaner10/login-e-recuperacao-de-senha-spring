package br.com.cwi.crescer.usuarios.service.usuario;

import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioAtualizarRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static br.com.cwi.crescer.usuarios.mapper.UsuarioMapper.toResponse;


@Service
public class AtualizarUsuarioService {

    @Autowired
    private BuscarValidarUsuarioService buscarUsuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public UsuarioResponse atualizar(Long idUsuario, UsuarioAtualizarRequest request) {

        Usuario usuario = buscarUsuarioService.devolverPorId(idUsuario);

        usuario.setFoto(request.getFoto());
        usuario.setNomeCompleto(request.getNomeCompleto());

        usuario.setEditadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

}

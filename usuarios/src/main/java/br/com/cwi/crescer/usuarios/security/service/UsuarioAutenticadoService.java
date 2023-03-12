package br.com.cwi.crescer.usuarios.security.service;

import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.mapper.UsuarioMapper;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticadoService {

    @Autowired
    private BuscarValidarUsuarioService usuarioService;

    public UsuarioResponse getResponse(String email) {
        Usuario usuario = usuarioService.devolverPorEmail(email);
        return UsuarioMapper.toResponse(usuario);
    }
}

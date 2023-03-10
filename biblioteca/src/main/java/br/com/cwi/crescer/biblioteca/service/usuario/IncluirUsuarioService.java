package br.com.cwi.crescer.biblioteca.service.usuario;

import br.com.cwi.crescer.biblioteca.controller.request.UsuarioRequest;
import br.com.cwi.crescer.biblioteca.controller.response.UsuarioResponse;
import br.com.cwi.crescer.biblioteca.domain.Permissao;
import br.com.cwi.crescer.biblioteca.domain.Usuario;
import br.com.cwi.crescer.biblioteca.repository.UsuarioRepository;
import br.com.cwi.crescer.biblioteca.validator.ValidarEmailUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.cwi.crescer.biblioteca.domain.Funcao.ADMIN;
import static br.com.cwi.crescer.biblioteca.domain.Funcao.USUARIO;
import static br.com.cwi.crescer.biblioteca.mapper.UsuarioMapper.toEntity;
import static br.com.cwi.crescer.biblioteca.mapper.UsuarioMapper.toResponse;

@Service
public class IncluirUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidarEmailUsuarioService emailUsuarioService;

    public UsuarioResponse incluir(UsuarioRequest request) {

        emailUsuarioService.validarEmail(request.getEmail());
        Usuario usuario = toEntity(request);
        usuario.setSenha(getSenhaCriptografada(request.getSenha()));
        adicionarPermissoes(request.getPermissoes(), usuario);
        usuario.setAtivo(true);

        usuario.setCriadoEm(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }

    private void adicionarPermissoes(List<String> listaPermissao, Usuario usuario) {
        Permissao permissaoUsuario = new Permissao();
        permissaoUsuario.setFuncao(USUARIO);
        usuario.adicionarPermissao(permissaoUsuario);
        if (listaPermissao.contains("ADMIN")) {
            Permissao permissaoAdmin = new Permissao();
            permissaoAdmin.setFuncao(ADMIN);
            usuario.adicionarPermissao(permissaoAdmin);
        }
    }
}

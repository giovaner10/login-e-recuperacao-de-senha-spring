package br.com.cwi.crescer.usuarios.service.usuario;

import br.com.cwi.crescer.usuarios.controller.request.UsuarioRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.domain.Funcao;
import br.com.cwi.crescer.usuarios.domain.Permissao;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.factory.EncoderFactory;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.validator.ValidarEmailUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.cwi.crescer.usuarios.domain.Funcao.ADMIN;
import static br.com.cwi.crescer.usuarios.domain.Funcao.USUARIO;
import static br.com.cwi.crescer.usuarios.mapper.UsuarioMapper.toEntity;
import static br.com.cwi.crescer.usuarios.mapper.UsuarioMapper.toResponse;

@Service
public class IncluirUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EncoderFactory encoderFactory;


    @Autowired
    private ValidarEmailUsuarioService emailUsuarioService;

    public UsuarioResponse incluir(UsuarioRequest request) {

        emailUsuarioService.validarEmail(request.getEmail());

        Usuario usuario = toEntity(request);
        usuario.setSenha(encoderFactory.encriptar(request.getSenha()));
        adicionarPermissoes(request.getPermissoes(), usuario);

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }


    private void adicionarPermissoes(List<Funcao> listaPermissao, Usuario usuario) {

        usuario.adicionarPermissao(Permissao.builder().funcao(USUARIO).build());

        if (listaPermissao.contains(ADMIN)) {
            usuario.adicionarPermissao(Permissao.builder().funcao(ADMIN).build());
        }
    }
}

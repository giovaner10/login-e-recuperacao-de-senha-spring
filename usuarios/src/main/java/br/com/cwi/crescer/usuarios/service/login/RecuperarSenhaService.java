package br.com.cwi.crescer.usuarios.service.login;


import br.com.cwi.crescer.usuarios.controller.request.EmailRecuperarRequest;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factory.EncoderFactory;
import br.com.cwi.crescer.usuarios.repository.TokenRepository;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RecuperarSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository resetarSenhaRepository;

    @Autowired
    private EncoderFactory encoderFactory;

    @Autowired
    private BuscarValidarUsuarioService validarUsuarioService;


    @Transactional
    public void recuperar(EmailRecuperarRequest usuarioDados) {

        Usuario usuario = validarUsuarioService.porEmail(usuarioDados.getEmail());

        if (Objects.isNull(usuario.getToken()) || !usuario.getToken().getToken().equals(usuarioDados.getToken())) {
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

        if(LocalDateTime.now().isAfter(usuario.getToken().getDataExpiracao())){
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

        usuario.setSenha(encoderFactory.encriptar(usuarioDados.getNovaSenha()));

        resetarSenhaRepository.deleteById(usuario.getToken().getId());

        usuarioRepository.save(usuario);
    }

}


package br.com.cwi.crescer.biblioteca.service.login;


import br.com.cwi.crescer.biblioteca.controller.request.EmailRecuperarRequest;
import br.com.cwi.crescer.biblioteca.domain.Usuario;
import br.com.cwi.crescer.biblioteca.repository.TokenRepository;
import br.com.cwi.crescer.biblioteca.repository.UsuarioRepository;
import br.com.cwi.crescer.biblioteca.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BuscarValidarUsuarioService validarUsuarioService;


    @Transactional
    public void recuperar(EmailRecuperarRequest usuarioDados) {

        Usuario usuario = validarUsuarioService.porEmail(usuarioDados.getEmail());

        if (Objects.isNull(usuario.getToken()) || !usuario.getToken().getToken().equals(usuarioDados.getToken())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

        if(LocalDateTime.now().isAfter(usuario.getToken().getDataExpiracao())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

        usuario.setSenha(getSenhaCriptografada(usuarioDados.getNovaSenha()));

        resetarSenhaRepository.deleteById(usuario.getToken().getId());

        usuarioRepository.save(usuario);
    }


    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }


}


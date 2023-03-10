package br.com.cwi.crescer.biblioteca.service.login;


import br.com.cwi.crescer.biblioteca.controller.request.EmailSolicitarRequest;
import br.com.cwi.crescer.biblioteca.domain.Token;
import br.com.cwi.crescer.biblioteca.domain.Usuario;
import br.com.cwi.crescer.biblioteca.factory.TokenFactory;
import br.com.cwi.crescer.biblioteca.repository.TokenRepository;
import br.com.cwi.crescer.biblioteca.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SolicitarTrocaSenhaService {


    @Autowired
    private BuscarValidarUsuarioService validarUsuarioService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EnviarEmailService emailSender;


    public void solicitar(EmailSolicitarRequest email) {

        Usuario usuario = validarUsuarioService.porEmail(email.getEmail());

        if (Objects.nonNull(usuario.getToken())) {
            if (LocalDateTime.now().isAfter(usuario.getToken().getDataExpiracao())) {
                tokenRepository.delete(usuario.getToken());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token ja solicitado, verifique sua caixa de emails");
            }
        }

        Token tokenUsuario = TokenFactory.getToken(usuario);

        tokenRepository.save(tokenUsuario);

        emailSender.preparar(usuario.getEmail(), tokenUsuario.getToken());
    }

}

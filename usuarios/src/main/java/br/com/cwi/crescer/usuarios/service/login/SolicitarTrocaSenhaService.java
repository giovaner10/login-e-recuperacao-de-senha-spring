package br.com.cwi.crescer.usuarios.service.login;


import br.com.cwi.crescer.usuarios.domain.Token;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factory.TokenFactory;
import br.com.cwi.crescer.usuarios.repository.TokenRepository;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SolicitarTrocaSenhaService {

    @Autowired
    private BuscarValidarUsuarioService buscarValidarUsuarioService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EnviarEmailService emailSender;


    @Transactional
    public void solicitar(String email) {

        Usuario usuario = buscarValidarUsuarioService.devolverPorEmail(email);

        if (Objects.nonNull(usuario.getToken())) {
            if (LocalDateTime.now().isAfter(usuario.getToken().getDataExpiracao())) {
                tokenRepository.delete(usuario.getToken());
                tokenRepository.flush();
            } else {
                throw new NegocioException(HttpStatus.NOT_FOUND, "Token ja solicitado, verifique sua caixa de emails");
            }
        }
        Token tokenUsuario = TokenFactory.getToken(usuario);
        tokenRepository.save(tokenUsuario);

        emailSender.preparar(usuario.getEmail(), tokenUsuario.getToken());
    }

}


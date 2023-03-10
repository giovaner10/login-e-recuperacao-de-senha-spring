package br.com.cwi.crescer.biblioteca.factory;

import br.com.cwi.crescer.biblioteca.domain.Token;
import br.com.cwi.crescer.biblioteca.domain.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenFactory {

    private static final int EXPIRACAO_TEMPO_MINUTOS = 15;

    public static Token getToken(Usuario usuario){
        return Token.builder()
                .token(UUID.randomUUID().toString())
                .dataExpiracao(LocalDateTime.now().plusMinutes(EXPIRACAO_TEMPO_MINUTOS))
                .usuario(usuario)
                .build();
    }
}

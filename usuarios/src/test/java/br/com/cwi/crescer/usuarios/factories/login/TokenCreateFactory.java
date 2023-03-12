package br.com.cwi.crescer.usuarios.factories.login;

import br.com.cwi.crescer.usuarios.domain.Token;

import java.time.LocalDateTime;

public class TokenCreateFactory {

    private static final int PLUS_EXPIRACAO = 15;
    public static final String TOKEN = "d3b91ad1-715b-4fe1-bb09-aa6689424208";


    public static Token getEntityToken(){
        return Token.builder()
                .token(TOKEN)
                .dataExpiracao(LocalDateTime.now().plusYears(PLUS_EXPIRACAO))
                .usuario(UsuarioFactory.getUsuario())
                .build();
    }


    public static Token getEntityInativo(){
        return Token.builder()
                .token(TOKEN)
                .dataExpiracao(LocalDateTime.now().plusYears(-PLUS_EXPIRACAO))
                .usuario(UsuarioFactory.getUsuario())
                .build();
    }
}

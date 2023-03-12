package br.com.cwi.crescer.usuarios.validator.login;

import br.com.cwi.crescer.usuarios.domain.Token;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ValidarTokenValidator {


    public static void validarToken(String tokenRecuperar, Token token) {
        if (Objects.isNull(token)) {
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

       else if (!token.getToken().equals(tokenRecuperar)) {
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }

        else if (LocalDateTime.now().isAfter(token.getDataExpiracao())) {
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Token invalido");
        }
    }
}

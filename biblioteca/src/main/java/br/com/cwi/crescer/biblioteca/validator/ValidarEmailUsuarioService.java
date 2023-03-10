package br.com.cwi.crescer.biblioteca.validator;


import br.com.cwi.crescer.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ValidarEmailUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validarEmail(String email) {
        if (usuarioRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email já cadastrado");
        }
    }

}

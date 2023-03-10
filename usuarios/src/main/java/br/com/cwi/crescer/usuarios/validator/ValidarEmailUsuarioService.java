package br.com.cwi.crescer.usuarios.validator;


import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ValidarEmailUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validarEmail(String email) {
        if (usuarioRepository.existsByEmail(email)){
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Email já cadastrado");
        }
    }

}

package br.com.cwi.crescer.usuarios.service.validator;

import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class BuscarValidarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario devolverPorId(Long idUsuario) {

        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }

    public Usuario devolverPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NegocioException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }

    public void validarEmail(String email) {
        if (usuarioRepository.existsByEmail(email)){
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Email já cadastrado");
        }
    }

}

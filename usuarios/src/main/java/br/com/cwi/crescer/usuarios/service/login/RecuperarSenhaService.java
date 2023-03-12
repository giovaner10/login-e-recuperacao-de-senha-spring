package br.com.cwi.crescer.usuarios.service.login;


import br.com.cwi.crescer.usuarios.controller.request.usuario.EmailRecuperarRequest;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.factory.EncoderFactory;
import br.com.cwi.crescer.usuarios.repository.TokenRepository;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import br.com.cwi.crescer.usuarios.validator.login.ValidarTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecuperarSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EncoderFactory encoderFactory;

    @Autowired
    private BuscarValidarUsuarioService validarUsuarioService;


    @Transactional
    public void recuperar(EmailRecuperarRequest usuarioDados) {

        Usuario usuario = validarUsuarioService.devolverPorEmail(usuarioDados.getEmail());

        ValidarTokenValidator.validarToken(usuarioDados.getToken(), usuario.getToken());

        usuario.setSenha(encoderFactory.encriptar(usuarioDados.getNovaSenha()));

        tokenRepository.delete(usuario.getToken());

        usuarioRepository.save(usuario);
    }


}


package br.com.cwi.crescer.usuarios.service.login;

import br.com.cwi.crescer.usuarios.controller.request.usuario.EmailRecuperarRequest;
import br.com.cwi.crescer.usuarios.domain.Token;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.login.EmailFactory;
import br.com.cwi.crescer.usuarios.factories.login.TokenCreateFactory;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
import br.com.cwi.crescer.usuarios.factory.EncoderFactory;
import br.com.cwi.crescer.usuarios.repository.TokenRepository;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecuperarSenhaServiceTest {


    @InjectMocks
    private RecuperarSenhaService validator;

    @Mock
    private BuscarValidarUsuarioService buscarValidarUsuarioService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UsuarioRepository usuarioRepository;


    @Mock
    private EncoderFactory encoderFactory;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;


    Usuario usuario = null;
    Token tokenInativo = null;
    Token tokenAtivo = null;
    Token token = null;
    EmailRecuperarRequest emailRecuperarRequest = null;
    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.getUsuario();
        token = Token.builder().build();
        tokenInativo = TokenCreateFactory.getEntityInativo();
        tokenAtivo = TokenCreateFactory.getEntityToken();
        emailRecuperarRequest = EmailFactory.getEmailRequest();
    }

    @Test
    @DisplayName("Deve alterar senha, usuario valido, token valido")
    void deveAdicionarTokenParaRecuperarSenhaUsuarioComToken() {

        usuario.setToken(tokenAtivo);

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);
        when(encoderFactory.encriptar(emailRecuperarRequest.getNovaSenha())).thenReturn("nova senha");

        validator.recuperar(emailRecuperarRequest);

        verify(usuarioRepository).save(usuarioCaptor.capture());
        Usuario usuarioCaptorValue = usuarioCaptor.getValue();

        verify(tokenRepository).delete(usuario.getToken());
        assertEquals("nova senha", usuarioCaptorValue.getSenha());
    }


    @Test
    @DisplayName("Deve lancar excessao ao usuario invalido")
    void deveLancarExcessaoUsurioInvalido() {

        doThrow(NegocioException.class).when(buscarValidarUsuarioService).devolverPorEmail(usuario.getEmail());

        assertThrows(NegocioException.class, () -> validator.recuperar(emailRecuperarRequest));

        verify(usuarioRepository, never()).save(null);
    }


    @Test
    @DisplayName("Deve lancar excessao usuario sem token")
    void deveLancarExcessaoUsurioSemToken() {

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(NegocioException.class, () -> validator.recuperar(emailRecuperarRequest));

        verify(usuarioRepository, never()).save(null);
    }


    @Test
    @DisplayName("Deve lancar excessao usuario com tokens diferentes")
    void deveLancarExcessaoUsurioComTokensDiferentes() {

        tokenAtivo.setToken("novo token");
        usuario.setToken(tokenAtivo);

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(NegocioException.class, () -> validator.recuperar(emailRecuperarRequest));

        verify(usuarioRepository, never()).save(null);
    }


    @Test
    @DisplayName("Deve lancar excessao usuario com token invalido ")
    void deveLancarExcessaoUsurioComInativo() {

        usuario.setToken(tokenInativo);

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(NegocioException.class, () -> validator.recuperar(emailRecuperarRequest));

        verify(usuarioRepository, never()).save(null);
    }


}
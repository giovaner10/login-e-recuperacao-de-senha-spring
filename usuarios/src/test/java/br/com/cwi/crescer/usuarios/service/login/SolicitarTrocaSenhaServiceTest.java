package br.com.cwi.crescer.usuarios.service.login;

import br.com.cwi.crescer.usuarios.domain.Token;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.login.TokenCreateFactory;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
import br.com.cwi.crescer.usuarios.repository.TokenRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitarTrocaSenhaServiceTest {

    @InjectMocks
    private SolicitarTrocaSenhaService validator;

    @Mock
    private BuscarValidarUsuarioService buscarValidarUsuarioService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private EnviarEmailService emailSender;

    @Captor
    private ArgumentCaptor<Token> tokenCaptor;


    Usuario usuario = null;
    Token tokenInativo = null;
    Token tokenAtivo = null;
    Token token = null;
    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.getUsuario();
        token = Token.builder().build();
        tokenInativo = TokenCreateFactory.getEntityInativo();
        tokenAtivo = TokenCreateFactory.getEntityToken();
    }

    @Test
    @DisplayName("Deve adicionar o token para recuperacao de senha")
    void deveAdicionarTokenParaRecuperarSenha() {

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        validator.solicitar(usuario.getEmail());

        verify(tokenRepository, never()).delete(usuario.getToken());
        verify(tokenRepository, never()).flush();
        verify(tokenRepository).save(tokenCaptor.capture());
        Token tokenCaptorValue = tokenCaptor.getValue();
        verify(emailSender).preparar(usuario.getEmail(), tokenCaptorValue.getToken());
    }


    @Test
    @DisplayName("Deve adicionar o token para recuperacao de com usuario com token inativo")
    void deveAdicionarTokenParaRecuperarSenhaUsuarioComToken() {

        usuario.setToken(tokenInativo);

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        validator.solicitar(usuario.getEmail());

        verify(tokenRepository).delete(usuario.getToken());
        verify(tokenRepository).flush();

        verify(tokenRepository).save(tokenCaptor.capture());
        Token tokenCaptorValue = tokenCaptor.getValue();
        verify(emailSender).preparar(usuario.getEmail(), tokenCaptorValue.getToken());
    }


    @Test
    @DisplayName("Deve lancar excessao token usuario ativo")
    void deveLancarExcessaoTokenAtivo() {

        usuario.setToken(tokenAtivo);

        when(buscarValidarUsuarioService.devolverPorEmail(usuario.getEmail())).thenReturn(usuario);

        assertThrows(NegocioException.class, () -> validator.solicitar(usuario.getEmail()));

        verify(tokenRepository, never()).delete(usuario.getToken());
        verify(tokenRepository, never()).flush();

        verify(emailSender, never()).preparar(usuario.getEmail(), null);
    }


    @Test
    @DisplayName("Deve lancar excessao ao usuario invalido")
    void deveLancarExcessaoUsurioInvalido() {

        doThrow(NegocioException.class).when(buscarValidarUsuarioService).devolverPorEmail(usuario.getEmail());


        assertThrows(NegocioException.class, () -> validator.solicitar(usuario.getEmail()));

        verify(tokenRepository, never()).delete(usuario.getToken());
        verify(tokenRepository, never()).flush();

        verify(emailSender, never()).preparar(usuario.getEmail(), null);
    }

}
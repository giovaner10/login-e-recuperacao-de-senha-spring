package br.com.cwi.crescer.usuarios.service.usuario;


import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioRequest;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
import br.com.cwi.crescer.usuarios.factory.EncoderFactory;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncluirUsuarioServiceTest {

    @InjectMocks
    private IncluirUsuarioService validator;

    @Mock
    private BuscarValidarUsuarioService validarEmailUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EncoderFactory encoderFactory;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;


    UsuarioRequest request = null;
    UsuarioRequest requestAdmin = null;

    @BeforeEach
    void setUp() {
        request = UsuarioFactory.getUsuarioRequest();
        requestAdmin = UsuarioFactory.getUsuarioRequestAdmin();
    }


    @Test
    @DisplayName("Deve criar usuario valido")
    void deveDevolverUsuarioValido() {

        when(encoderFactory.encriptar(request.getSenha())).thenReturn("senha encriptadata");

        validator.incluir(request);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuario = usuarioCaptor.getValue();

        verify(validarEmailUsuarioService).validarEmail(request.getEmail());

        assertTrue(usuario.isAtivo());

        assertEquals("senha encriptadata", usuario.getSenha());
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals(request.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(request.getFoto(), usuario.getFoto());
        assertEquals(1, usuario.getPermissoes().size());
        assertNull(usuario.getEditadoEm());
        assertNotNull(usuario.getCriadoEm());
    }


    @Test
    @DisplayName("Deve criar usuario valido admin")
    void deveDevolverUsuarioValidoAdmin() {

        when(encoderFactory.encriptar(requestAdmin.getSenha())).thenReturn("senha encriptadata");

        validator.incluir(requestAdmin);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuario = usuarioCaptor.getValue();

        verify(validarEmailUsuarioService).validarEmail(request.getEmail());

        assertTrue(usuario.isAtivo());

        assertEquals("senha encriptadata", usuario.getSenha());
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals(request.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(request.getFoto(), usuario.getFoto());
        assertEquals(2, usuario.getPermissoes().size());
        assertNull(usuario.getEditadoEm());
        assertNotNull(usuario.getCriadoEm());
    }

    @Test
    @DisplayName("nao deve criar usario com email existente")
    void lacarExcessaoEmailExistente() {

        doThrow(NegocioException.class).when(validarEmailUsuarioService).validarEmail(request.getEmail());

        assertThrows(NegocioException.class, () -> validator.incluir(request));

        verify(usuarioRepository, never()).save(null);
        verify(encoderFactory, never()).encriptar(null);
        verify(validarEmailUsuarioService).validarEmail(request.getEmail());
    }

}
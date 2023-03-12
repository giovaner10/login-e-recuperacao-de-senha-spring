package br.com.cwi.crescer.usuarios.service.usuario;

import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioAtualizarRequest;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
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
class AtualizarUsuarioServiceTest {

    @InjectMocks
    private AtualizarUsuarioService validator;

    @Mock
    private BuscarValidarUsuarioService buscarUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;


    UsuarioAtualizarRequest request = null;
    Usuario usuario = null;

    @BeforeEach
    void setUp() {
        request = UsuarioFactory.getUsuarioAtualizarRequest();
        usuario = UsuarioFactory.getUsuario();
    }


    @Test
    @DisplayName("Deve atualizar usuario valido")
    void deveAtualizarUsuarioValido() {

        when(buscarUsuarioService.devolverPorId(usuario.getId())).thenReturn(usuario);

        validator.atualizar(usuario.getId(), request);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuario = usuarioCaptor.getValue();

        assertEquals(request.getNomeCompleto(), usuario.getNomeCompleto());
        assertEquals(request.getFoto(), usuario.getFoto());
        assertNotNull(usuario.getEditadoEm());

    }

    @Test
    @DisplayName("nao deve atualizar com usuario inexistente")
    void lacarExcessaoUsuarioInexistente() {

        doThrow(NegocioException.class).when(buscarUsuarioService).devolverPorId(usuario.getId());

        assertThrows(NegocioException.class, () -> validator.atualizar(usuario.getId(), request));

        verify(usuarioRepository, never()).save(usuario);
    }

}
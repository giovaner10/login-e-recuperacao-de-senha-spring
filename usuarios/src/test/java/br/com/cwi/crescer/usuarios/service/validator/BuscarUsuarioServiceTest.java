package br.com.cwi.crescer.usuarios.service.validator;


import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.service.validator.BuscarValidarUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioServiceTest {

    @InjectMocks
    private BuscarValidarUsuarioService validator;

    @Mock
    private UsuarioRepository usuarioRepository;


    Usuario usuario = null;
    @BeforeEach
    void setUp() {
        usuario = UsuarioFactory.getUsuario();
    }


    @Test
    @DisplayName("Deve devolver usuario valido")
    void deveDevolverUsuarioValido() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Usuario usuarioBuscado = validator.devolverPorId(usuario.getId());

        assertEquals(usuario, usuarioBuscado);
    }

    @Test
    @DisplayName("deve lancar excessao ao buscar usuario indisponivel")
    void deveLancarExcessaoAoNaoEcontrarUsuario() {

        doThrow(NegocioException.class).when(usuarioRepository).findById(usuario.getId());

        assertThrows(NegocioException.class, () -> validator.devolverPorId(usuario.getId()));
    }

    @Test
    @DisplayName("Deve devolver usuario valido - email")
    void deveDevolverUsuarioValidoSolicitante() {

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario usuarioBuscado = validator.devolverPorEmail(usuario.getEmail());

        assertEquals(usuario, usuarioBuscado);
    }

    @Test
    @DisplayName("deve lancar excessao ao buscar usuario indisponivel - email")
    void deveLancarExcessaoAoNaoEcontrarUsuarioSolicitante() {

        doThrow(NegocioException.class).when(usuarioRepository).findByEmail(usuario.getEmail());

        assertThrows(NegocioException.class, () -> validator.devolverPorEmail(usuario.getEmail()));
    }


    @Test
    @DisplayName("Deve validar o email")
    void deveValidarEmail() {

        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarEmail(usuario.getEmail()));
    }

    @Test
    @DisplayName("Nao deve validar o email")
    void deveInvalidarEmail() {

        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(NegocioException.class, () -> validator.validarEmail(usuario.getEmail()));
    }

}
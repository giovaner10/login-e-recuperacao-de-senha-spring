package br.com.cwi.crescer.usuarios.validator;


import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.UsuarioFactory;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        Usuario usuarioBuscado = validator.porId(usuario.getId());

        assertEquals(usuario, usuarioBuscado);
    }

    @Test
    @DisplayName("deve lancar excessao ao buscar usuario indisponivel")
    void deveLancarExcessaoAoNaoEcontrarUsuario() {

        doThrow(NegocioException.class).when(usuarioRepository).findById(usuario.getId());

        assertThrows(NegocioException.class, () -> validator.porId(usuario.getId()));
    }

    @Test
    @DisplayName("Deve devolver usuario valido - email")
    void deveDevolverUsuarioValidoSolicitante() {

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario usuarioBuscado = validator.porEmail(usuario.getEmail());

        assertEquals(usuario, usuarioBuscado);
    }

    @Test
    @DisplayName("deve lancar excessao ao buscar usuario indisponivel - email")
    void deveLancarExcessaoAoNaoEcontrarUsuarioSolicitante() {

        doThrow(NegocioException.class).when(usuarioRepository).findByEmail(usuario.getEmail());

        assertThrows(NegocioException.class, () -> validator.porEmail(usuario.getEmail()));
    }

}
package br.com.cwi.crescer.usuarios.validator;


import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.factories.SimpleFactory;
import br.com.cwi.crescer.usuarios.factories.UsuarioFactory;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidarEmailUsuarioServiceTest {

    @InjectMocks
    private ValidarEmailUsuarioService validator;

    @Mock
    private UsuarioRepository usuarioRepository;


    String email = SimpleFactory.getEmail();
    @BeforeEach
    void setUp() {
        email = UsuarioFactory.getUsuario().getEmail();
    }


    @Test
    @DisplayName("Deve validar o email")
    void deveDevolverUsuarioValido() {

        when(usuarioRepository.existsByEmail(email)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validarEmail(email));
    }

    @Test
    @DisplayName("Nao deve validar o email")
    void deveLancarExcessaoAoNaoEcontrarUsuario() {

        when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(NegocioException.class, () -> validator.validarEmail(email));
    }

}
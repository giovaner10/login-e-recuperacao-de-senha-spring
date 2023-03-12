package br.com.cwi.crescer.usuarios.mapper;


import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.factories.login.UsuarioFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioMapperTest {

    UsuarioRequest request = null;
    Usuario usuario = null;
    @BeforeEach
    void setUp() {
        request = UsuarioFactory.getUsuarioRequest();
        usuario = UsuarioFactory.getUsuario();

    }


    @Test
    @DisplayName("Deve criar uma entidade valida")
    void toEntity() {

        Usuario usuarioEntity = UsuarioMapper.toEntity(request);

        assertEquals(request.getFoto(), usuarioEntity.getFoto());
        assertEquals(request.getEmail(), usuarioEntity.getEmail());
        assertTrue(usuarioEntity.isAtivo());
        assertNotNull(usuarioEntity.getCriadoEm());

    }

    @Test
    @DisplayName("Deve retornar NullPointerException quando a request for nula")
    void toEntityUsuarioNull() {

        assertThrows(NullPointerException.class, ()-> UsuarioMapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve criar uma response valida")
    void toResponse() {

        UsuarioResponse usuarioResponse = UsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), usuarioResponse.getId());
        assertEquals(usuario.getFoto(), usuarioResponse.getFoto());
        assertEquals(usuario.getEmail(), usuarioResponse.getEmail());
        assertEquals(usuario.getNomeCompleto(), usuarioResponse.getNomeCompleto());
        assertNotNull(usuarioResponse.getPermissoes());

    }

    @Test
    @DisplayName("Deve retornar NullPointerException quando a entity for nula")
    void toResponseUsuarioNull() {

        assertThrows(NullPointerException.class, ()-> UsuarioMapper.toResponse(null));
    }

}
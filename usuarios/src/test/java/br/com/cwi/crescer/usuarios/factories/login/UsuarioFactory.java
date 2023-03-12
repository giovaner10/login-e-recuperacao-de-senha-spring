package br.com.cwi.crescer.usuarios.factories.login;


import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioAtualizarRequest;
import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioRequest;
import br.com.cwi.crescer.usuarios.domain.Usuario;
import br.com.cwi.crescer.usuarios.factories.login.SimpleFactory;

import java.time.LocalDate;

public class UsuarioFactory {

    public static Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(SimpleFactory.getRandomLong());
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setAtivo(true);
        usuario.setCriadoEm(LocalDate.of(2000, 6, 7).atStartOfDay());
        return usuario;
    }

    public static UsuarioRequest getUsuarioRequest() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setFoto("mina foto");
        usuario.setSenha("minha senha");
        usuario.setIsAdmin(false);
        return usuario;
    }

    public static UsuarioRequest getUsuarioRequestAdmin() {
        UsuarioRequest usuario = new UsuarioRequest();
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setEmail("teste@cwi.com.br");
        usuario.setFoto("mina foto");
        usuario.setSenha("minha senha");
        usuario.setIsAdmin(true);
        return usuario;
    }

    public static UsuarioAtualizarRequest getUsuarioAtualizarRequest() {
        UsuarioAtualizarRequest usuario = new UsuarioAtualizarRequest();
        usuario.setNomeCompleto("Usuário de teste");
        usuario.setFoto("minha foto");
        return usuario;
    }

}

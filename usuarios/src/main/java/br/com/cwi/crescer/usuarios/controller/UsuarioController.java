package br.com.cwi.crescer.usuarios.controller;

import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioAtualizarRequest;
import br.com.cwi.crescer.usuarios.controller.request.usuario.UsuarioRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.service.usuario.AtualizarUsuarioService;
import br.com.cwi.crescer.usuarios.service.usuario.IncluirUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IncluirUsuarioService service;

    @Autowired
    private AtualizarUsuarioService atualizarUsuarioService;

    @PostMapping
    public UsuarioResponse incluir(@Valid @RequestBody UsuarioRequest request) {
        return service.incluir(request);
    }

    @PutMapping("admin/{usuarioId}")
    public UsuarioResponse atualizar(@PathVariable Long usuarioId, @Valid @RequestBody UsuarioAtualizarRequest request) {
        return atualizarUsuarioService.atualizar(usuarioId, request);
    }
}

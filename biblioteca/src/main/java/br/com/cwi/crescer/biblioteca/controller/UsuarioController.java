package br.com.cwi.crescer.biblioteca.controller;

import br.com.cwi.crescer.biblioteca.controller.request.UsuarioAtualizarRequest;
import br.com.cwi.crescer.biblioteca.controller.request.UsuarioRequest;
import br.com.cwi.crescer.biblioteca.controller.response.UsuarioResponse;
import br.com.cwi.crescer.biblioteca.service.usuario.AtualizarUsuarioService;
import br.com.cwi.crescer.biblioteca.service.usuario.IncluirUsuarioService;
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
    public UsuarioResponse atualizar(@PathVariable Long usuarioId,@Valid @RequestBody UsuarioAtualizarRequest request) {
        return atualizarUsuarioService.atualizar(usuarioId, request);
    }
}

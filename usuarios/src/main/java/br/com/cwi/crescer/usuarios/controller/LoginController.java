package br.com.cwi.crescer.usuarios.controller;

import br.com.cwi.crescer.usuarios.controller.request.EmailRecuperarRequest;
import br.com.cwi.crescer.usuarios.controller.request.EmailSolicitarRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.service.login.RecuperarSenhaService;
import br.com.cwi.crescer.usuarios.service.login.SolicitarTrocaSenhaService;
import br.com.cwi.crescer.usuarios.service.usuario.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioAutenticadoService service;

    @Autowired
    private SolicitarTrocaSenhaService emailService;

    @Autowired
    private RecuperarSenhaService recuperarSenhaService;

    @PostMapping
    public UsuarioResponse login() {
        return service.getResponse();
    }

    @GetMapping("/me")
    public UsuarioResponse me() {
        return service.getResponse();
    }


    @PostMapping("/solicitar")
    public void solicitar(@Valid @RequestBody EmailSolicitarRequest email) {
        emailService.solicitar(email);
    }

    @PostMapping("/recuperar")
    public void recuperar(@Valid @RequestBody EmailRecuperarRequest email) {
        recuperarSenhaService.recuperar(email);
    }

}

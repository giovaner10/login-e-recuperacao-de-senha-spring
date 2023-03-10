package br.com.cwi.crescer.usuarios.controller;

import br.com.cwi.crescer.usuarios.controller.request.EmailRecuperarRequest;
import br.com.cwi.crescer.usuarios.controller.request.EmailSolicitarRequest;
import br.com.cwi.crescer.usuarios.controller.response.UsuarioResponse;
import br.com.cwi.crescer.usuarios.service.login.RecuperarSenhaService;
import br.com.cwi.crescer.usuarios.service.login.SolicitarTrocaSenhaService;
import br.com.cwi.crescer.usuarios.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UsuarioResponse login(@AuthenticationPrincipal UserDetails userDetails) {
        return service.getResponse(userDetails.getUsername());
    }

    @GetMapping("/me")
    public UsuarioResponse me(@AuthenticationPrincipal UserDetails userDetails) {
        return service.getResponse(userDetails.getUsername());
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

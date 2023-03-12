package br.com.cwi.crescer.usuarios.factories.login;

import br.com.cwi.crescer.usuarios.controller.request.usuario.EmailRecuperarRequest;
import org.springframework.mail.SimpleMailMessage;

public class EmailFactory {


    public static SimpleMailMessage getEmail(){
        SimpleMailMessage menssagem = new SimpleMailMessage();
        menssagem.setFrom("1studentsucess@gmail.com");
        menssagem.setTo(SimpleFactory.getEmail());
        menssagem.setSubject("Email de recuperação de senha");
        menssagem.setText("Clique aqui para recuperar sua senha: http://localhost:8080/login/recuperar , seu token é: d3b91ad1-715b-4fe1-bb09-aa6689424208 .");

        return menssagem;
    }

    public static EmailRecuperarRequest getEmailRequest(){
        EmailRecuperarRequest request = new EmailRecuperarRequest();
        request.setEmail(SimpleFactory.getEmail());
        request.setToken("d3b91ad1-715b-4fe1-bb09-aa6689424208");
        request.setNovaSenha("nova senha");
        return request;

    }

}

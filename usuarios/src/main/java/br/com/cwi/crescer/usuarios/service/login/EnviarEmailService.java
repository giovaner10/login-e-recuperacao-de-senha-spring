package br.com.cwi.crescer.usuarios.service.login;


import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailService {

    @Autowired
    private JavaMailSender emailSender;


    public void preparar(String emailDestino, String token) {

        try {
            SimpleMailMessage menssagem = new SimpleMailMessage();
            menssagem.setFrom("1studentsucess@gmail.com");
            menssagem.setTo(emailDestino);
            menssagem.setSubject("Email de recuperação de senha");
            menssagem.setText("Clique aqui para recuperar sua senha: http://localhost:8080/login/recuperar , seu token é: " + token + " .");
            emailSender.send(menssagem);

       } catch (MailException e) {
            throw new NegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "Falha ao enviar email");
        }
    }

}


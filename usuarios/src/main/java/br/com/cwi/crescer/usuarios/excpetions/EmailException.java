package br.com.cwi.crescer.usuarios.excpetions;

import org.springframework.mail.MailException;

public class EmailException extends MailException {

    public EmailException() {
        super("Falha ao enviar.");
    }
}

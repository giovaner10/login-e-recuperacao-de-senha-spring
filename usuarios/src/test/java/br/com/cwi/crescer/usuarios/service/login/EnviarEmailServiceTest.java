package br.com.cwi.crescer.usuarios.service.login;

import br.com.cwi.crescer.usuarios.excpetions.EmailException;
import br.com.cwi.crescer.usuarios.factories.login.EmailFactory;
import br.com.cwi.crescer.usuarios.factories.login.SimpleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EnviarEmailServiceTest {


    @InjectMocks
    private EnviarEmailService validator;

    @Mock
    private JavaMailSender emailSender;


    String token = null;
    String email = null;
    SimpleMailMessage emailBody = null;

    @BeforeEach
    void setUp() {
        email = SimpleFactory.getEmail();
        token = "d3b91ad1-715b-4fe1-bb09-aa6689424208";
        emailBody = EmailFactory.getEmail();
    }


    @Test
    @DisplayName("Deve enviar email")
    void deveEnviarEmail() {

        validator.preparar(email, token);

        verify(emailSender).send(emailBody);
    }

    @Test
    @DisplayName("deve lancar excessao ao tentar enviar email")
    void deveLancarExcessaoAoEnviarEmail() {

        doThrow(EmailException.class).when(emailSender).send(emailBody);

        assertThrows(EmailException.class, () -> validator.preparar(email, token));
    }

}
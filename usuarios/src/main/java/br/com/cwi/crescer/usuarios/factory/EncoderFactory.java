package br.com.cwi.crescer.usuarios.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderFactory {

    @Autowired
    PasswordEncoder passwordEncoder;

    public String encriptar(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }


}

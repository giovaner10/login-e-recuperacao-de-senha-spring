package br.com.cwi.crescer.usuarios.security.service;

import br.com.cwi.crescer.usuarios.excpetions.NegocioException;
import br.com.cwi.crescer.usuarios.repository.UsuarioRepository;
import br.com.cwi.crescer.usuarios.security.details.UsuarioSecurityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioSecuritySerivce implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return usuarioRepository.findByEmail(email)
                .map(UsuarioSecurityDetails::new)
                .orElseThrow(() -> new NegocioException(HttpStatus.BAD_REQUEST,"Credenciais inválidas"));
    }
}

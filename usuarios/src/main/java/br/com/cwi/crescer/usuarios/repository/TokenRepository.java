package br.com.cwi.crescer.usuarios.repository;

import br.com.cwi.crescer.usuarios.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Long> {

}

package br.com.cwi.crescer.biblioteca.repository;

import br.com.cwi.crescer.biblioteca.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Long> {

}

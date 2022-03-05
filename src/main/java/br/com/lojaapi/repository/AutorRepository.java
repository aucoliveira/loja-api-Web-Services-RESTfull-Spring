package br.com.lojaapi.repository;

import br.com.lojaapi.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}

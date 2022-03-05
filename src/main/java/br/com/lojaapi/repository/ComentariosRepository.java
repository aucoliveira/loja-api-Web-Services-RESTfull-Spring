package br.com.lojaapi.repository;

import br.com.lojaapi.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentariosRepository extends JpaRepository<Comentario, Long> {

}

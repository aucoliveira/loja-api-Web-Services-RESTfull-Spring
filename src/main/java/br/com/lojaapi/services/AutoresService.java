package br.com.lojaapi.services;

import br.com.lojaapi.domain.Autor;
import br.com.lojaapi.repository.AutorRepository;
import br.com.lojaapi.services.exceptions.AutorExistenteException;
import br.com.lojaapi.services.exceptions.AutorNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoresService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    public Autor salvar(Autor autor) {
        if (autor.getId() != null) {
            Optional<Autor> a = autorRepository.findById(autor.getId());

            if(a != null) {
                throw new AutorExistenteException(" O autor já existe!");
            }
        }
        return autorRepository.save(autor);
    }

    public Autor buscar(Long id) {
        Autor autor = autorRepository.findById(id).orElse(null);;

        if(autor == null) {
            throw new AutorNaoEncontradoException("Autor não encontrado");
        }
        return autor;
    }
}

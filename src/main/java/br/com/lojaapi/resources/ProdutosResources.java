package br.com.lojaapi.resources;

import br.com.lojaapi.domain.Comentario;
import br.com.lojaapi.domain.Produto;
import br.com.lojaapi.repository.ProdutoRepository;
import br.com.lojaapi.services.ProdutoService;
import br.com.lojaapi.services.exceptions.ProdutoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/produtos")
public class ProdutosResources {
    /*
        Essa classe representa o controller em um padrão MVC
    */
    /**
     * Autor: Augusto
     * CacheControl, foi configurado para durar 20 segundos do lado do cliente
     */
    @Autowired
    private ProdutoService produtoService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listar());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@Valid @RequestBody Produto produto) {
        produto = produtoService.salvar(produto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
        Produto produto = produtoService.buscar(id);

        CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

      return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(produto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@RequestBody Produto produto,
                                          @PathVariable("id") Long id) {
        produto.setId(id);
        produtoService.atualizar(produto);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarComentario(@Valid @PathVariable("id") Long produtoId,
                                    @RequestBody Comentario comentario) {

        //pegando os dados do usuario logado, no caso em memoria
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        comentario.setUsuario(auth.getName());

        produtoService.salvarComentario(produtoId, comentario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long produtoId) {
        List<Comentario> comentarios = produtoService.listarComentarios(produtoId);

        return ResponseEntity.status(HttpStatus.OK).body(comentarios);
    }
}

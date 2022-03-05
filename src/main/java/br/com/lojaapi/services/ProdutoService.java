package br.com.lojaapi.services;

import br.com.lojaapi.domain.Comentario;
import br.com.lojaapi.domain.Produto;
import br.com.lojaapi.repository.ComentariosRepository;
import br.com.lojaapi.repository.ProdutoRepository;
import br.com.lojaapi.services.exceptions.ProdutoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ComentariosRepository comentariosRepository;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto buscar(Long id) {
        Produto produto =produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("O produto não pode ser encontrado.");
        }
        return produto;
    }

    public Produto salvar(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }
    public void deletar(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProdutoNaoEncontradoException("O produto não pôde ser encontrado");
        }
    }

    public void atualizar(Produto produto) {
        verificaExistencia(produto);
        produtoRepository.save(produto);
    }
    private void verificaExistencia(Produto produto) {
        buscar(produto.getId());
    }

    public Comentario salvarComentario(Long produtoId, Comentario comentario) {
        Produto produto = buscar(produtoId);

        comentario.setProduto(produto);
        comentario.setData(new Date());

        return comentariosRepository.save(comentario);
    }

    public List<Comentario> listarComentarios(Long produtoId) {
        Produto produto = buscar(produtoId);
        return produto.getComentarios();
    }
}

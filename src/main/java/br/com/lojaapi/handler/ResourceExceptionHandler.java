package br.com.lojaapi.handler;

import br.com.lojaapi.domain.DetalhesErro;
import br.com.lojaapi.services.exceptions.AutorExistenteException;
import br.com.lojaapi.services.exceptions.AutorNaoEncontradoException;
import br.com.lojaapi.services.exceptions.ProdutoNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * ControlelerAdvice vai chamar para ela todas as exceçoes que acontecerem, chamar aqui tbm todas as classes
 * relacionadas a exceçoes.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<DetalhesErro> handlerProdutoNaoEncontradoException
            (ProdutoNaoEncontradoException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(404l);
        erro.setTitulo("Produto não encontrado");
        erro.setMensagemDesenvolvedor("http://erros.loja-api.com/404");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(AutorExistenteException.class)
    public ResponseEntity<DetalhesErro> handlerAutorExistenteException
            (AutorExistenteException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(404l);
        erro.setTitulo("Autor já existente");
        erro.setMensagemDesenvolvedor("http://erros.loja-api.com/409");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }


    @ExceptionHandler(AutorNaoEncontradoException.class)
    public ResponseEntity<DetalhesErro> handlerAutorNaoEncontradoException
            (AutorNaoEncontradoException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(404l);
        erro.setTitulo("Autor não encontrado.");
        erro.setMensagemDesenvolvedor("http://erros.loja-api.com/409");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DetalhesErro> handlerDataIntegrityViolationException
            (DataIntegrityViolationException e, HttpServletRequest request) {

        DetalhesErro erro = new DetalhesErro();
        erro.setStatus(400l);
        erro.setTitulo("Requisição invalida.");
        erro.setMensagemDesenvolvedor("http://erros.loja-api.com/400");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}

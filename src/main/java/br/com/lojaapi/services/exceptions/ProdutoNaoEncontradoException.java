package br.com.lojaapi.services.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1869300553614629710L;
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

package br.com.lojaapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Esse campo text tem que ser preenchido.")
    @Size(max = 1500, message = "O comentário não pode ser maior que 1500 caracteres.")
    @JsonProperty("comentario")
    private String texto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String usuario;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date data;

    @ManyToOne(fetch = FetchType.LAZY) // vários comentários para um Produto
    @JoinColumn(name = "PRODUTO_ID")
    @JsonIgnore // para não acontecer um loop.
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

package br.com.leosalema.dto;

import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.entity.PessoaEntity;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;


public class EnderecoDTO {

    private Long id;
    private Long cep;
    private String logradouro;
    private Integer numero;
    private String cidade;
    private String estado;
    private boolean isPrincipal;

    private PessoaEntity pessoa;

    public EnderecoDTO() {}

    public EnderecoDTO(EnderecoEntity endereco) {
        BeanUtils.copyProperties(endereco, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(boolean isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

}

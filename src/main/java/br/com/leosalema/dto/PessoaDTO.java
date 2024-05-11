package br.com.leosalema.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.entity.PessoaEntity;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;


public class PessoaDTO {
    private Long id;

    private String nomeCompleto;
    private String dataNascimento;

    private List<EnderecoEntity> listaEnderecos;

    public PessoaDTO(PessoaEntity pessoa) {
        BeanUtils.copyProperties(pessoa, this);
        this.listaEnderecos = new ArrayList<>();

        if(pessoa.getListaEndereco() != null) {
            this.listaEnderecos = pessoa.getListaEndereco();
        }

    }

    public PessoaDTO() {
        this.listaEnderecos = new ArrayList<>();
    }

    public <T> PessoaDTO(long l, String leonardo, String s, List<T> ts) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public List<EnderecoEntity> getListaEnderecos() {
        return listaEnderecos;
    }

    public void setListaEnderecos(List<EnderecoEntity> listaEnderecos) {
        this.listaEnderecos = listaEnderecos;
    }

}

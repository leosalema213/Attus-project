package br.com.leosalema.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PESSOA")
public class PessoaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nomeCompleto;
	@Column(nullable = false)
	private String dataNascimento;
	
	@OneToMany
	@JoinColumn(name = "ID_ENDERECO")
	private List<EnderecoEntity> listaEnderecos;
	
	public PessoaEntity(PessoaEntity pessoa) {
		BeanUtils.copyProperties(pessoa, this);
		if(pessoa != null && pessoa.getListaEndereco() != null) {
			this.listaEnderecos = new ArrayList<>(pessoa.getListaEndereco());
		}
		
	}
	
	public PessoaEntity() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	
	public List<EnderecoEntity> getListaEndereco() {
		return listaEnderecos;
	}

	public void setListaEndereco(List<EnderecoEntity> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomeCompleto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaEntity other = (PessoaEntity) obj;
		return Objects.equals(nomeCompleto, other.nomeCompleto);
	}
	
	
}

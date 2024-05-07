package br.com.leosalema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<EnderecoEntity> listarTodos() {
		List<EnderecoEntity> enderecos = enderecoRepository.findAll();
		return enderecos.stream().map(EnderecoEntity::new).toList();
	};
	
	public void inserir(EnderecoEntity endereco) {
		EnderecoEntity enderecoEntity = new EnderecoEntity(endereco);
		enderecoRepository.save(enderecoEntity);
	}
	
	public EnderecoEntity alterar(EnderecoEntity endereco) {
		EnderecoEntity enderecoEntity = new EnderecoEntity(endereco);
		return new EnderecoEntity(enderecoRepository.save(enderecoEntity));
	}
	
	public void excluir(Long id) {
		EnderecoEntity endereco = enderecoRepository.findById(id).get();
		enderecoRepository.delete(endereco);	
	}
	
	public EnderecoEntity buscarPorId(Long id) {
		return new EnderecoEntity(enderecoRepository.findById(id).get());
	}
	
}

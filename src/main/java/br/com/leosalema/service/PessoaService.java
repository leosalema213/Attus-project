package br.com.leosalema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leosalema.entity.PessoaEntity;
import br.com.leosalema.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	
	public List<PessoaEntity> listarTodos() {
		List<PessoaEntity> pessoas = pessoaRepository.findAll();
		return pessoas.stream().map(PessoaEntity::new).toList();
	}
	
	public void inserir(PessoaEntity pessoa) {
		PessoaEntity pessoaEntity = new PessoaEntity(pessoa);
		pessoaRepository.save(pessoaEntity);
	}
	
	public PessoaEntity alterar(PessoaEntity pessoa) {
		PessoaEntity pessoaEntity = new PessoaEntity(pessoa);
		return new PessoaEntity(pessoaRepository.save(pessoaEntity));
	}
	
	public void excluir(Long id) {
		PessoaEntity pessoa = pessoaRepository.findById(id).get();
		pessoaRepository.delete(pessoa);
	}
	
	public PessoaEntity buscarPorId(Long id) {
		return new PessoaEntity(pessoaRepository.findById(id).get());
	}
	
}
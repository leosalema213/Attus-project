package br.com.leosalema.service;

import java.util.List;

import br.com.leosalema.dto.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leosalema.entity.PessoaEntity;
import br.com.leosalema.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
		
	
	public List<PessoaDTO> listarTodos() {
		var pessoas = pessoaRepository.findAll();

		return pessoas.stream().map(PessoaDTO::new).toList();
	}

	public PessoaDTO inserir(PessoaDTO data) {
		pessoaRepository.save(new PessoaEntity(data));
		return data;
	}
	
	public void alterar(Long id, PessoaDTO data) {

		var pessoaEntity = pessoaRepository.findById(id);

		if(pessoaEntity.isPresent()) {
			var pessoa = pessoaEntity.get();

			if(data.getNomeCompleto() != null) {
				pessoa.setNomeCompleto(data.getNomeCompleto());
			}

			if(data.getDataNascimento() != null) {
				pessoa.setDataNascimento(data.getDataNascimento());
			}

			if(data.getListaEnderecos() != null) {
				pessoa.setListaEndereco(data.getListaEnderecos());
			}

			pessoaRepository.save(pessoa);
		}
	}
	
	public void excluir(Long id) {
		 var pessoa = pessoaRepository.existsById(id);

		if(pessoa) {
			pessoaRepository.deleteById(id);
		}


	}
	
}
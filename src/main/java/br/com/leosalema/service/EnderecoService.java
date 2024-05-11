package br.com.leosalema.service;

import java.util.List;

import br.com.leosalema.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<EnderecoDTO> listarTodos() {
		List<EnderecoEntity> enderecos = enderecoRepository.findAll();
		return enderecos.stream().map(EnderecoDTO::new).toList();
	};
	
	public EnderecoDTO inserir(EnderecoDTO endereco) {
		var enderecEntity =  enderecoRepository.save(new EnderecoEntity(endereco));
		return new EnderecoDTO(enderecEntity);
	}
	
	public void alterar(Long id, EnderecoDTO data) {
		var enderecoEntity = enderecoRepository.findById(id);

		if (enderecoEntity.isPresent()) {
			var endereco = enderecoEntity.get();

			if (data.getCep() != null) {
				endereco.setCep(data.getCep());
			}

			if (data.getCidade() != null) {
				endereco.setCidade(data.getCidade());
			}

			if (data.getEstado() != null) {
				endereco.setEstado(data.getEstado());
			}

			if (data.getLogradouro() != null) {
				endereco.setLogradouro(data.getLogradouro());
			}

			if (data.getNumero() != null) {
				endereco.setNumero(data.getNumero());
			}

			enderecoRepository.save(endereco);
		}
	}
	
	public void excluir(Long id) {
		var adress = enderecoRepository.existsById(id);

		if(adress) {
			enderecoRepository.deleteById(id);
		}
	}
	
}

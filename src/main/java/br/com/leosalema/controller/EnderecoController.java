package br.com.leosalema.controller;

import java.util.List;

import br.com.leosalema.dto.EnderecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leosalema.entity.EnderecoEntity;
import br.com.leosalema.service.EnderecoService;

@RestController
@RequestMapping(value = "/endereco")
@CrossOrigin
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@GetMapping
	public List<EnderecoDTO> listarTodos() {
		return enderecoService.listarTodos();
	}
	
	@PostMapping
	public EnderecoDTO inserir(@RequestBody EnderecoDTO endereco){
		enderecoService.inserir(endereco);
		return endereco;

	}
	
	@PutMapping
	public ResponseEntity<Void> alterar(@RequestBody EnderecoDTO endereco) {
		enderecoService.alterar(endereco.getId(), endereco);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		enderecoService.excluir(id);
		return ResponseEntity.ok().build();
	}
}

package br.com.leosalema.controller;

import java.util.List;

import br.com.leosalema.dto.EnderecoDTO;
import br.com.leosalema.dto.PessoaDTO;
import br.com.leosalema.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.leosalema.entity.PessoaEntity;
import br.com.leosalema.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoa")
@CrossOrigin
public class PessoaController {
	 
	@Autowired
	private PessoaService pessoaService;


	@Autowired
	private EnderecoService enderecoService;

	@GetMapping
	public ResponseEntity<List<PessoaDTO> > listarTodos() {
		return new ResponseEntity<>(pessoaService.listarTodos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PessoaDTO> inserir(@RequestBody PessoaDTO pessoa) {
		PessoaDTO newPessoa = pessoaService.inserir(pessoa);

		return new ResponseEntity<>(newPessoa, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> alterar(@RequestBody PessoaDTO pessoa) {
		pessoaService.alterar(pessoa.getId(), pessoa);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		pessoaService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
}
	
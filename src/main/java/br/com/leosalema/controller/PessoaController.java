package br.com.leosalema.controller;

import java.util.List;

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

import br.com.leosalema.entity.PessoaEntity;
import br.com.leosalema.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoa")
@CrossOrigin
public class PessoaController {
	 
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<PessoaEntity>  listarTodos() {
		return pessoaService.listarTodos();
	}
	
	@GetMapping("/{id}")
	public PessoaEntity listarPorId(@PathVariable("id") Long id) {
		return pessoaService.buscarPorId(id);
	}
	
	@PostMapping
	public void inserir(@RequestBody PessoaEntity pessoa) {
		pessoaService.inserir(pessoa);
	}
	
	@PutMapping
	public PessoaEntity alterar(@RequestBody PessoaEntity pessoa) {
		return pessoaService.alterar(pessoa);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		pessoaService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
}
	
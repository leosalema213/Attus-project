package br.com.leosalema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leosalema.entity.EnderecoEntity;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
	
}

package br.com.leosalema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leosalema.entity.PessoaEntity;


public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {

}

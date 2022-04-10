package com.algaworks.algamoneyapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoneyapy.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}

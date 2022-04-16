package com.algaworks.algamoneyapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoneyapy.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}

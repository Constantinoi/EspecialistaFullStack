package com.algaworks.algamoneyapy.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapy.model.Lancamento;
import com.algaworks.algamoneyapy.model.Pessoa;
import com.algaworks.algamoneyapy.repository.LancamentoRepository;
import com.algaworks.algamoneyapy.repository.PessoaRepository;
import com.algaworks.algamoneyapy.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(@Valid Lancamento lancamento) {

		Pessoa pessoa = pessoaRepository.getById(lancamento.getPessoa().getCodigo());

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		return lancamentoRepository.save(lancamento);
	}

}

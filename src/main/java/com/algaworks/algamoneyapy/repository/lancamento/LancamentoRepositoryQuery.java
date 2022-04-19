package com.algaworks.algamoneyapy.repository.lancamento;

import java.util.List;

import com.algaworks.algamoneyapy.model.Lancamento;
import com.algaworks.algamoneyapy.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}

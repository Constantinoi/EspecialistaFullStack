package com.algaworks.algamoneyapy.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapy.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapy.model.Lancamento;
import com.algaworks.algamoneyapy.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Lancamento> lista = lancamentoRepository.findAll();

		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscaLancamento(@PathVariable Long codigo) {

		Optional<Lancamento> lanc = lancamentoRepository.findById(codigo);
		return !lanc.isEmpty() ? ResponseEntity.ok(lanc) : ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

		Lancamento lanc = lancamentoRepository.save(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lanc.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lanc);
	}

}

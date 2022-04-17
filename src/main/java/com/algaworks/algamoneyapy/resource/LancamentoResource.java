package com.algaworks.algamoneyapy.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.algaworks.algamoneyapy.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapy.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoneyapy.model.Lancamento;
import com.algaworks.algamoneyapy.repository.LancamentoRepository;
import com.algaworks.algamoneyapy.service.LancamentoService;
import com.algaworks.algamoneyapy.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;

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

		Lancamento lanc = lancamentoService.salvar(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lanc.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lanc);
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUser = messageSource.getMessage("pessoa.inexistente-inativa", null,
				LocaleContextHolder.getLocale());
		String messagemDev = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemUser, messagemDev));

		return ResponseEntity.badRequest().body(erros);
	}
}

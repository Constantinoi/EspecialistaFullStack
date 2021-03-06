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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapy.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapy.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.algaworks.algamoneyapy.model.Lancamento;
import com.algaworks.algamoneyapy.repository.LancamentoRepository;
import com.algaworks.algamoneyapy.repository.filter.LancamentoFilter;
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
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
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

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
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

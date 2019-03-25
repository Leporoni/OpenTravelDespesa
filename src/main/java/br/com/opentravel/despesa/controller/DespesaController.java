package br.com.opentravel.despesa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.opentravel.despesa.model.Despesa;
import br.com.opentravel.despesa.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;

	@GetMapping
	public List<Despesa> listar() {
		return despesaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Despesa> buscar(@PathVariable Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);

		if (despesa.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(despesa.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Despesa adicionar(@Valid @RequestBody Despesa despesa) {
		Optional<Despesa> despesaExistente = despesaRepository.findByClienteAndProjeto(despesa.getCliente(),
				despesa.getProjeto());

		if (despesaExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe uma despesa com o mesmo cliente e projeto.");
		}

		return despesaRepository.save(despesa);

	}
	


}

package br.com.opentravel.despesa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.opentravel.despesa.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	Optional<Despesa> findByClienteAndProjeto(String cliente, String projeto);

}

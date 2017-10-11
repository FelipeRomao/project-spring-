package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos repositorio;

	public void salvar(Titulo titulo) {
		try {
			repositorio.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		repositorio.delete(codigo);

	}

	public String receber(Long codigo) {
		Titulo titulo = repositorio.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		repositorio.save(titulo);

		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return repositorio.findByDescricaoContaining(descricao);
	}

	
}

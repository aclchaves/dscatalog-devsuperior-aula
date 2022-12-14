package com.devsuperior.dscatalog.servicos;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoriaDTO;
import com.devsuperior.dscatalog.entidades.Categoria;
import com.devsuperior.dscatalog.repositorios.CategoriaRepository;
import com.devsuperior.dscatalog.servicos.exceptions.DataBaseException;
import com.devsuperior.dscatalog.servicos.exceptions.EntidadeNaoEncontradaException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	@Transactional(readOnly = true)
	public Page<CategoriaDTO> buscaTodosPaginado(PageRequest pageRequest){
		Page<Categoria> lista = repositorio.findAll(pageRequest);
		
		return lista.map(x -> new CategoriaDTO(x));
		
		/*List<CategoriaDTO> listaDto = new ArrayList<>();
		for (Categoria cat : lista) {
			listaDto.add(new CategoriaDTO(cat));
		}
		
		return listaDto;*/
	}

	@Transactional(readOnly = true)
	public CategoriaDTO buscaPorId(Long id) {
		Optional<Categoria> obj = repositorio.findById(id);
		//Categoria entidade = obj.get();
		Categoria entidade = obj.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não Encontrada"));
		return new CategoriaDTO(entidade);
	}

	@Transactional
	public CategoriaDTO inserir(CategoriaDTO dto) {
		Categoria entidade = new Categoria();
		entidade.setNome(dto.getNome());
		entidade = repositorio.save(entidade);
		return new CategoriaDTO(entidade);
	}

	@Transactional
	public CategoriaDTO update(Long id, CategoriaDTO dto) {
		try {		
			Categoria entidade = repositorio.getOne(id);
			entidade.setNome(dto.getNome());
			entidade = repositorio.save(entidade);
			return new CategoriaDTO(entidade);
		}catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontradaException("Id não encontrado " + id);
		}
	}

	public void deletar(Long id) {
		try {
			repositorio.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Id não encontrado " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violacao de integridade");
		}
		
	}

}

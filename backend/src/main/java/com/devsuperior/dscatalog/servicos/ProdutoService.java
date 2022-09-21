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
import com.devsuperior.dscatalog.dto.ProdutoDTO;
import com.devsuperior.dscatalog.entidades.Categoria;
import com.devsuperior.dscatalog.entidades.Produto;
import com.devsuperior.dscatalog.repositorios.CategoriaRepository;
import com.devsuperior.dscatalog.repositorios.ProdutoRepository;
import com.devsuperior.dscatalog.servicos.exceptions.DataBaseException;
import com.devsuperior.dscatalog.servicos.exceptions.EntidadeNaoEncontradaException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> buscaTodosPaginado(PageRequest pageRequest){
		Page<Produto> lista = repositorio.findAll(pageRequest);
		
		return lista.map(x -> new ProdutoDTO(x));
		
		/*List<ProdutoDTO> listaDto = new ArrayList<>();
		for (Produto cat : lista) {
			listaDto.add(new ProdutoDTO(cat));
		}
		
		return listaDto;*/
	}

	@Transactional(readOnly = true)
	public ProdutoDTO buscaPorId(Long id) {
		Optional<Produto> obj = repositorio.findById(id);
		//Produto entidade = obj.get();
		Produto entidade = obj.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não Encontrada"));
		return new ProdutoDTO(entidade, entidade.getCategorias());
	}

	@Transactional
	public ProdutoDTO inserir(ProdutoDTO dto) {		
		Produto entidade = new Produto();
		CopisDtoParaEntidade(dto, entidade);		
		entidade = repositorio.save(entidade);
		return new ProdutoDTO(entidade);
	}	

	@Transactional
	public ProdutoDTO update(Long id, ProdutoDTO dto) {
		try {		
			Produto entidade = repositorio.getOne(id);
			CopisDtoParaEntidade(dto, entidade);			
			entidade = repositorio.save(entidade);
			return new ProdutoDTO(entidade);
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
	
	private void CopisDtoParaEntidade(ProdutoDTO dto, Produto entidade) {
		
		entidade.setNome(dto.getNome());
		entidade.setDescricao(dto.getDescricao());
		entidade.setDate(dto.getDate());
		entidade.setImgUrl(dto.getImgUrl());
		entidade.setPreco(dto.getPreco());
		
		entidade.getCategorias().clear();
		for (CategoriaDTO catDto : dto.getCategorias()) {
			Categoria categoria = categoriaRepositorio.getOne(catDto.getId());
			entidade.getCategorias().add(categoria);
		}
		
	}

}

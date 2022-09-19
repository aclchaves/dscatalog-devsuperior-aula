package com.devsuperior.dscatalog.servicos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoriaDTO;
import com.devsuperior.dscatalog.entidades.Categoria;
import com.devsuperior.dscatalog.repositorios.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> buscaTodos(){
		List<Categoria> lista = repositorio.findAll();
		
		return lista.stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
		
		/*List<CategoriaDTO> listaDto = new ArrayList<>();
		for (Categoria cat : lista) {
			listaDto.add(new CategoriaDTO(cat));
		}
		
		return listaDto;*/
	}

}

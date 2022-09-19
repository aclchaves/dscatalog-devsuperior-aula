package com.devsuperior.dscatalog.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dscatalog.entidades.Categoria;
import com.devsuperior.dscatalog.repositorios.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repositorio;
	
	public List<Categoria> buscaTodos(){
		return repositorio.findAll();
	}

}

package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entidades.Categoria;
import com.devsuperior.dscatalog.servicos.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource{
	
	@Autowired
	private CategoriaService servico;

	@GetMapping
	public ResponseEntity<List<Categoria>> bucarTodas(){
		List<Categoria> lista = servico.buscaTodos();		
		return ResponseEntity.ok().body(lista);
	}
}

package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.ProdutoDTO;
import com.devsuperior.dscatalog.servicos.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource{
	
	@Autowired
	private ProdutoService servico;

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> bucarTodas(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ProdutoDTO> lista = servico.buscaTodosPaginado(pageRequest);		
		return ResponseEntity.ok().body(lista);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> bucaPorId(@PathVariable Long id ){
		ProdutoDTO Produto = servico.buscaPorId(id);		
		return ResponseEntity.ok().body(Produto);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> inserir(@RequestBody ProdutoDTO dto){
		dto = servico.inserir(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto){
		dto = servico.update(id,dto);
		return ResponseEntity.ok().body(dto);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> deletar(@PathVariable Long id){
		servico.deletar(id);
		return ResponseEntity.noContent().build();
		
	}
	
}

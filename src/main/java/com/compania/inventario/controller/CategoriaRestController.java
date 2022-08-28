package com.compania.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compania.inventario.response.CategoriaResponseRest;
import com.compania.inventario.service.CategoriaService;

@RestController
@RequestMapping("/api/v1")
public class CategoriaRestController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> recuperarCategorias(){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.recuperarCategorias();
		return responseEntity;
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> buscarCategorias(@PathVariable Long id){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.buscarCategoriaPorId(id);
		return responseEntity;
	}
}

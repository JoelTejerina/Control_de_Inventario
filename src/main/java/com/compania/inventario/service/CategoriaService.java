package com.compania.inventario.service;

import org.springframework.http.ResponseEntity;

import com.compania.inventario.response.CategoriaResponseRest;

public interface CategoriaService {
	
	ResponseEntity<CategoriaResponseRest> recuperarCategorias();
	
	ResponseEntity<CategoriaResponseRest> buscarCategoriaPorId(Long id);
	
}

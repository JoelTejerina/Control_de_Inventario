package com.compania.inventario.service;

import org.springframework.http.ResponseEntity;

import com.compania.inventario.response.CategoriaResponseRest;

public interface CategoriaService {
	
	public ResponseEntity<CategoriaResponseRest> buscar();
	
}

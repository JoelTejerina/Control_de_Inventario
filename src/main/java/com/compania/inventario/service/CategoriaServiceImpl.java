package com.compania.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.repository.CategoriaRepository;
import com.compania.inventario.response.CategoriaResponseRest;

@Service
@Transactional(readOnly = true)
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Override
	public ResponseEntity<CategoriaResponseRest> buscar() {
		CategoriaResponseRest responseRest = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> categorias = categoriaRepository.findAll();
			
			responseRest.getCategoriaResponse().setCategorias(categorias);
			responseRest.setMetadata("Respuesta exitosa", "200", "Respuesta ok");
			
		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al consultar");
			e.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.OK);
	}

}

package com.compania.inventario.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.repository.CategoriaRepository;
import com.compania.inventario.response.CategoriaResponseRest;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Override
	public ResponseEntity<CategoriaResponseRest> recuperarCategorias() {
		CategoriaResponseRest responseRest = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> categorias = categoriaRepository.findAll();
			
			responseRest.getCategoriaResponse().setCategorias(categorias);
			responseRest.setMetadata("Respuesta exitosa", "200", "Se obtuvo las categorias");
			
		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al consultar");
			e.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoriaResponseRest> buscarCategoriaPorId(Long id) {
		
		CategoriaResponseRest responseRest = new CategoriaResponseRest();
		List<Categoria> categorias = new ArrayList<>();
				
		try {
			Optional<Categoria> optional = categoriaRepository.findById(id);
			
			if (optional.isPresent()) {
				categorias.add(optional.get());
				responseRest.getCategoriaResponse().setCategorias(categorias);
				responseRest.setMetadata("Respuesta exitosa", "200", "Categoria encontrada");

			} else {
				responseRest.setMetadata("Error", "-1", "Categoria no encontrada");
	
				return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al consultar por id");
			e.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoriaResponseRest> guardarCategoria(Categoria categoria) {
		CategoriaResponseRest responseRest = new CategoriaResponseRest();
		List<Categoria> categorias = new ArrayList<>();
				
		try {
			Categoria categoriaGuardar = categoriaRepository.save(categoria);
			
			if(categoriaGuardar != null) {
				categorias.add(categoriaGuardar);
				responseRest.getCategoriaResponse().setCategorias(categorias);
				
				responseRest.setMetadata("Respuesta exitosa", "200", "Categoria guardad");

			}
			else {
				responseRest.setMetadata("Error", "-1", "Categoria no guardada");
				
				return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al guardar categoria");
			e.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<CategoriaResponseRest> actualizarCategoria(Categoria categoria, Long id) {
		CategoriaResponseRest responseRest = new CategoriaResponseRest();
		List<Categoria> categorias = new ArrayList<>();
				
		try {
			Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
			if (categoriaOptional.isPresent()) {
				categoriaOptional.get().setNombre(categoria.getNombre());
				categoriaOptional.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizada = categoriaRepository.save(categoriaOptional.get());
				
				if (categoriaActualizada != null) {
					categorias.add(categoriaActualizada);
					responseRest.getCategoriaResponse().setCategorias(categorias);
					
					responseRest.setMetadata("Respuesta exitosa", "200", "Categoria actualizada");
				}
				else {
					responseRest.setMetadata("Error", "-1", "Categoria no actualizada");
					
					return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
				}
			}
			else {
				responseRest.setMetadata("Error", "-1", "Categoria no actualizada");
				
				return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al actualizar categoria");
			e.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(responseRest, HttpStatus.OK);
	}
}
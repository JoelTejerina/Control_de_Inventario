package com.compania.inventario.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.response.CategoriaResponseRest;
import com.compania.inventario.service.CategoriaService;
import com.compania.inventario.utility.CategoriaExcelExporter;

@CrossOrigin(origins = {"http://localhost:4200"})
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
	public ResponseEntity<CategoriaResponseRest> buscarCategoria(@PathVariable Long id){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.buscarCategoriaPorId(id);
		return responseEntity;
	}
	
	@GetMapping("/categorias/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=resultado_categoria";
		response.setHeader(headerKey, headerValue);
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.recuperarCategorias();
		
		CategoriaExcelExporter excelExporter = new CategoriaExcelExporter(
				responseEntity.getBody().getCategoriaResponse().getCategorias());
		
		excelExporter.export(response);
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> guardarCategoria(@RequestBody Categoria categoria){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.guardarCategoria(categoria);
		return responseEntity;
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> actualizarCategoria(@RequestBody Categoria categoria, @PathVariable Long id){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.actualizarCategoria(categoria, id);
		return responseEntity;
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> borrarCategoria(@PathVariable Long id){
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaService.borrarCategoria(id);
		return responseEntity;
	}
}

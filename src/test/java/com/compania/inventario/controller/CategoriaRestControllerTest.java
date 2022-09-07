package com.compania.inventario.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.controller.CategoriaRestController;
import com.compania.inventario.response.CategoriaResponseRest;
import com.compania.inventario.service.CategoriaService;

class CategoriaRestControllerTest {
	@InjectMocks
	CategoriaRestController categoriaRestController;
	
	@Mock
	private CategoriaService categoriaService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void guardarCategoriaTest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(Long.valueOf(1));
		categoria.setNombre("Lacteos");
		categoria.setDescripcion("Productos derivados de la leche");
		
		when(categoriaService.guardarCategoria(any(Categoria.class))).thenReturn(
				new ResponseEntity<CategoriaResponseRest>(HttpStatus.OK));
		
		ResponseEntity<CategoriaResponseRest> responseEntity = categoriaRestController.guardarCategoria(categoria);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
}

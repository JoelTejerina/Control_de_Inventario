package com.compania.inventario.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.compania.inventario.bo.Producto;
import com.compania.inventario.response.ProductoResponseRest;
import com.compania.inventario.service.ProductoService;
import com.compania.inventario.utility.ImagenUtility;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductoRestController {
	
	@Autowired
	ProductoService productoService;
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<ProductoResponseRest> obtenerProductosPorId(@PathVariable Long id){
		ResponseEntity<ProductoResponseRest> productoResponse = productoService.buscarProductoPorId(id);
		return productoResponse;
	}
	
	@GetMapping("/productos/filter/{nombre}")
	public ResponseEntity<ProductoResponseRest> searchByName(@PathVariable String nombre){
		ResponseEntity<ProductoResponseRest> response = productoService.buscarProductoPorNombre(nombre);
		return response;
	}
	
	@PostMapping("/productos")
	public ResponseEntity<ProductoResponseRest> guardarProducto(
			@RequestParam("imagen") MultipartFile imagen,
			@RequestParam("nombre") String nombre,
			@RequestParam("precio") Double precio,
			@RequestParam("cantidad") int cantidad,
			@RequestParam("idCategoria") Long idCategoria) throws IOException {
		
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setCantidad(cantidad);
		producto.setImagen(ImagenUtility.compressZLib(imagen.getBytes()));
		
		ResponseEntity<ProductoResponseRest> productoResponse = productoService.guardarProductoCategoria(producto, idCategoria);
		return productoResponse;
	}

}

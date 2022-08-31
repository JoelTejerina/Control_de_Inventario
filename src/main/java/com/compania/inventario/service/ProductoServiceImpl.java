package com.compania.inventario.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.bo.Producto;
import com.compania.inventario.repository.CategoriaRepository;
import com.compania.inventario.repository.ProductoRepository;
import com.compania.inventario.response.ProductoResponseRest;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductoServiceImpl implements ProductoService {
		
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public ResponseEntity<ProductoResponseRest> recuperarProductos() {
		return null;
		
	}

	@Override
	public ResponseEntity<ProductoResponseRest> buscarProductoPorId(Long id) {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public ResponseEntity<ProductoResponseRest> guardarProductoCategoria(Producto producto, Long idCategoria) {
		ProductoResponseRest responseRest = new ProductoResponseRest();
		List<Producto> productos = new ArrayList<>();
		
		try {
			Optional<Categoria> categoriaOptional = categoriaRepository.findById(idCategoria);
			
			if(categoriaOptional.isPresent()) {
				producto.setCategoria(categoriaOptional.get());

			}else {
				responseRest.setMetadata("Error", "-1", "Categoria no encontrada asociada al producto");
				new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}
			
			Producto productoGuardar = productoRepository.save(producto);
			
			if(productoGuardar != null) {
				productos.add(productoGuardar);
				responseRest.getProductoResponse().setProductos(productos);
	
				responseRest.setMetadata("Respuesta exitosa", "200", "Producto guardado");
				
			}else {				
				responseRest.setMetadata("Error", "200", "Producto no guardado");
				new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.getStackTrace();
			responseRest.setMetadata("Error", "200", "El producto no se pudo guardar");
			new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductoResponseRest> actualizarProducto(Producto producto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ProductoResponseRest> borrarProducto(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

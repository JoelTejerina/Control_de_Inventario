package com.compania.inventario.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compania.inventario.bo.Categoria;
import com.compania.inventario.bo.Producto;
import com.compania.inventario.repository.CategoriaRepository;
import com.compania.inventario.repository.ProductoRepository;
import com.compania.inventario.response.CategoriaResponseRest;
import com.compania.inventario.response.ProductoResponseRest;
import com.compania.inventario.utility.ImagenUtility;

@Service
public class ProductoServiceImpl implements ProductoService {
		
	private static Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public ResponseEntity<ProductoResponseRest> recuperarProductos() {
		ProductoResponseRest responseRest = new ProductoResponseRest();
		List<Producto> productos = new ArrayList<>();
		List<Producto> productoAux = new ArrayList<>();
		
		try {
			
			productoAux = productoRepository.findAll();
			
			if( productoAux.size() > 0) {
			
					productoAux.stream().forEach( (p) -> {
					byte[] imageDescomprimida = ImagenUtility.decompressZLib(p.getImagen());
					p.setImagen(imageDescomprimida);
					productos.add(p);
				});
			} 
			
			responseRest.getProductoResponse().setProductos(productos);
			responseRest.setMetadata("Respuesta exitosa", "200", "Se obtuvo los productos");
			
		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al consultar");
			e.getStackTrace();
			
			return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductoResponseRest> buscarProductoPorId(Long id) {
		ProductoResponseRest responseRest = new ProductoResponseRest();
		List<Producto> productos = new ArrayList<>();
				
		try {
			Optional<Producto> productoOptional = productoRepository.findById(id);
			
			if (productoOptional.isPresent()) {
				
				byte[] imagenDescomprimida = ImagenUtility.decompressZLib(productoOptional.get().getImagen());
				productoOptional.get().setImagen(imagenDescomprimida);
				productos.add(productoOptional.get());
				responseRest.getProductoResponse().setProductos(productos);
				responseRest.setMetadata("Respuesta exitosa", "200", "Producto encontrado");

			} else {
				responseRest.setMetadata("Error", "-1", "Producto no encontrado");
	
				return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al consultar por id");
			e.getStackTrace();
			
			return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ProductoResponseRest> buscarProductoPorNombre(String nombre) {
		ProductoResponseRest response = new ProductoResponseRest();
		List<Producto> productos = new ArrayList<>();
		List<Producto> productoAux = new ArrayList<>();
		
		try {
			
			productoAux = productoRepository.findByNameLike(nombre);
			
			
			if( productoAux.size() > 0) {
				
				productoAux.stream().forEach( (p) -> {
					byte[] imageDescompressed = ImagenUtility.decompressZLib(p.getImagen());
					p.setImagen(imageDescompressed);
					productos.add(p);
				});
				
				
				response.getProductoResponse().setProductos(productos);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
				
			} else {
				response.setMetadata("respuesta nok", "-1", "Productos no encontrados ");
				return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al buscar producto por nombre");
			return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);

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
		ProductoResponseRest responseRest = new ProductoResponseRest();
		
		try {
			
			productoRepository.deleteById(id);
			responseRest.setMetadata("Respuesta exitosa", "200", "Registro eliminado");
			
		} catch (Exception e) {
			responseRest.setMetadata("Error", "-1", "Error al eliminar");
			e.getStackTrace();
			
			return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductoResponseRest>(responseRest, HttpStatus.OK);
	}
}

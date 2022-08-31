package com.compania.inventario.response;

import java.util.List;

import com.compania.inventario.bo.Producto;

import lombok.Data;

@Data
public class ProductoResponse {
	
	List<Producto> productos;
}

package com.compania.inventario.response;

import java.util.*;

import com.compania.inventario.bo.Categoria;

import lombok.Data;

@Data
public class CategoriaResponse {
	
	private List<Categoria> categorias;
}

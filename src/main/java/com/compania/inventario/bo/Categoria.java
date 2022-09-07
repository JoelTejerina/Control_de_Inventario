package com.compania.inventario.bo;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="CATEGORIAS")
public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8259190182482428361L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCategoria;
	private String nombre;
	private String descripcion;
	
}

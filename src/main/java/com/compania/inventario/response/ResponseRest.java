package com.compania.inventario.response;

import java.util.*;

public class ResponseRest {
	private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();
	
	public ArrayList<HashMap<String, String>> getMetadata(){
		return metadata;
	}
	
	public void setMetadata(String tipo, String codigo, String dato) {
		HashMap<String,	String> map = new HashMap<String, String>();
		
		map.put("tipo", tipo);
		map.put("codigo", codigo);
		map.put("dato", dato);
		
		metadata.add(map);
	}
}

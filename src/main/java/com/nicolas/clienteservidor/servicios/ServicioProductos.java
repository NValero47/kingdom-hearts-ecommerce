package com.nicolas.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.nicolas.clienteservidor.model.Producto;


public interface ServicioProductos {
	
	void registrarProducto(Producto producto);
	
	List<Producto> obtenerProductos();
	
	void borrarProducto(long id);
	
	void actualizarProducto(Producto producto);

	Producto obtenerProductoPorId(long id);
	
	//Para la parte publica, servicios REST
	List< Map<String, Object> > obtenerProductosParaFormarJSON();
	
}
package com.nicolas.clienteservidor.RESTcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.servicios.ServicioProductos;

@RestController
@RequestMapping("productosREST/")
public class ProductosREST {
	
	@Autowired
	private ServicioProductos servicioProductos;
	
	@RequestMapping("obtener")
	public List<Map<String, Object>> obtenerProductos() {
		return servicioProductos.obtenerProductosParaFormarJSON();
	}
	
}
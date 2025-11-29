package com.nicolas.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.nicolas.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.nicolas.clienteservidor.model.Pedido;

public interface ServicioPedidos {

	// Gestion en administración
	List<Pedido> obtenerPedidos();
	
	List<Map<String, Object>> obtenerPedidosPorIdUsuario(int idUsuario);
	
	Pedido obtenerPedidoPorId(int idPedido);
	
	void actualizarPedido(int idPedido, String estado);
	
	// Operaciones para hacer AJAX
	void procesarPaso1(String nombre, String direccion, String provincia, String portal, String piso, String telefono, int idUsuario);

	ResumenPedido procesarPaso2(String tarjeta, String numero, String titular, int idUsuario);
	
	void procesarPaso3(String personalidad, String comentario, int idUsuario);

	Pedido obtenerPedidoIncompleto(int idUsuario);
	
	public ResumenPedido obtenerResumenDelPedido(int idUsuario, Pedido pedido);
	
	void confirmarPedido(int idUsuario);

}

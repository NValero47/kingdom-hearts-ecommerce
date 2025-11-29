package com.nicolas.clienteservidor.RESTcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicolas.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.nicolas.clienteservidor.model.Pedido;
import com.nicolas.clienteservidor.servicios.ServicioPedidos;
import com.nicolas.clienteservidor.serviciosJPAImpl.ServicioPedidosImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pedidosREST/")
public class PedidosREST {

	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("paso4")
	public String paso4(HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		servicioPedidos.confirmarPedido(idUsuario);
		return "ok";
	}
	
	@RequestMapping("paso3")
	public ResumenPedido paso3(String personalidad, String comentario, HttpServletRequest request) {
	    int idUsuario = (int) request.getSession().getAttribute("usuario_id");

	    // Guardamos los datos del paso 3
	    servicioPedidos.procesarPaso3(personalidad, comentario, idUsuario);

	    // Obtenemos el pedido actualizado
	    Pedido pedido = servicioPedidos.obtenerPedidoIncompleto(idUsuario);

	    // Devolvemos un ResumenPedido completo
	    return servicioPedidos.obtenerResumenDelPedido(idUsuario, pedido);
	}

	
	@RequestMapping("paso2")
	public ResumenPedido paso2(String tarjeta, String numero, String titular, HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		ResumenPedido resumen = servicioPedidos.procesarPaso2(tarjeta, numero, titular, idUsuario);
		return resumen;
	}
	
	@RequestMapping("paso1")
	public String paso1(String nombre, String direccion, String provincia, String portal, String piso, String telefono, HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		String respuesta = "ok";
		servicioPedidos.procesarPaso1(nombre, direccion, provincia, portal, piso, telefono, idUsuario);
		return respuesta;
	}
	
	@RequestMapping("obtener")
	public List<Map<String, Object>> obtener(HttpServletRequest request) {
		int idUsuario = (int) request.getSession().getAttribute("usuario_id");
		return servicioPedidos.obtenerPedidosPorIdUsuario(idUsuario);
	}
	
}
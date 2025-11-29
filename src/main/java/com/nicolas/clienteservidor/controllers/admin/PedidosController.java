package com.nicolas.clienteservidor.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.constantes.Estados;
import com.nicolas.clienteservidor.model.Pedido;
import com.nicolas.clienteservidor.servicios.ServicioPedidos;

@Controller
@RequestMapping("admin/")
public class PedidosController {

	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("obtenerPedidos")
	public String obtenerPedidos(Model model) {
		model.addAttribute("pedidos", servicioPedidos.obtenerPedidos());
		return "admin/pedidos";
	}
	
	@RequestMapping("verDetallesPedido")
	public String verDetallesPedido(@RequestParam("id") Integer id, Model model) {
		
		Pedido p = servicioPedidos.obtenerPedidoPorId(id);
		model.addAttribute("pedido", p);
		
		// Vamos a mandar a la vista los valores del desplegable estado del pedido
		Map<String, String> estados = new HashMap<String, String>();
		estados.put(Estados.INCOMPLETO.name(), "Pedido incompleto");
		estados.put(Estados.TERMINADO.name(), "Finalizado por el usuario");
		estados.put(Estados.LISTO_PARA_ENVIAR.name(), "Pedido listo para enviar");
		estados.put(Estados.ENVIADO.name(), "Pedido enviado");
		model.addAttribute("estados", estados);
		
	    Map<String, String> tiposTarjeta = new HashMap<String, String>();
	    tiposTarjeta.put("1", "VISA");
	    tiposTarjeta.put("2", "MasterCard");
	    model.addAttribute("tiposTarjeta", tiposTarjeta);
	    
	    Map<String, String> personalidades = new HashMap<String, String>();
	    personalidades.put("1", "Una persona tranquila y responsable");
	    personalidades.put("2", "Alguien alegre y con mucha energía");
	    personalidades.put("3", "Una persona creativa y original");
	    personalidades.put("4", "Un poco de todo, depende del día");
	    model.addAttribute("personalidades", personalidades);
	    
		return "admin/pedidos_detalle";
	}
}
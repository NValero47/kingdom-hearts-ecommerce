package com.nicolas.clienteservidor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.servicios.ServicioImagenesProducto;
import com.nicolas.clienteservidor.servicios.ServicioProductos;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DetallesProductoController {

    @Autowired
    private ServicioProductos servicioProductos;

    @Autowired
    private ServicioImagenesProducto servicioImagenes;
    
    @Autowired
	private MessageSource messageSource;

    @GetMapping("detallesProducto")
    public String detalles(@RequestParam("id") Long id, Model model, HttpServletRequest request ) {

		Object usuarioIdObj = request.getSession().getAttribute("usuario_id");
		Integer usuarioId = (usuarioIdObj != null) ? (Integer) usuarioIdObj : null;
		model.addAttribute("usuarioId", usuarioId);
    	
        model.addAttribute("producto", servicioProductos.obtenerProductoPorId(id));
        model.addAttribute("imagenes", servicioImagenes.obtenerImagenes(id));

        String idiomaActual = messageSource.getMessage("idioma", null, LocaleContextHolder.getLocale());
        
        return "detalles_producto_" + idiomaActual;
    }
}
package com.nicolas.clienteservidor.controllers.imagen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.servicios.ServicioProductos;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MostrarImagenProducto {
	
	@Autowired
	private ServicioProductos servicioProductos;
	
	@RequestMapping("mostrar_imagen")
	public void mostrarImagen( @RequestParam("id") Long id, HttpServletResponse response) throws IOException {
		byte[] info = servicioProductos.obtenerProductoPorId(id).getImagenPortada();
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}
	
}
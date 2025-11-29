package com.nicolas.clienteservidor.controllers.imagen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.servicios.ServicioUsuario;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MostrarImagenUsuario {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@RequestMapping("mostrar_imagen_usuario")
	public void mostrarImagenUsuario( @RequestParam("id") int id, HttpServletResponse response) throws IOException {
		byte[] info = servicioUsuario.obtenerUsuarioPorId(id).getImagenPortada();
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}
	
}
package com.nicolas.clienteservidor.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioUsuario;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/")
public class UsuariosController {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@RequestMapping("editarUsuario")
	public String editarUsuario(@RequestParam("id") int id, Model model) {
		Usuario usuario = servicioUsuario.obtenerUsuarioPorId(id);
		model.addAttribute("usuarioEditar", usuario);
		return "admin/usuarios_editar";
	}
	
	@RequestMapping("guardarCambiosUsuario")
	public String guardarCambiosUsuario(@ModelAttribute("usuarioEditar") @Valid Usuario usuarioEditar, BindingResult resultadoValidaciones, Model model) {
		if (resultadoValidaciones.hasErrors()) {
	        return "admin/usuarios_editar";
	    }
		
		servicioUsuario.actualizarUsuario(usuarioEditar);
		return obtenerUsuarios(model);
	}
	
	@RequestMapping("registrarUsuario")
	public String registrarUsuario(Model model) {
		Usuario u = new Usuario();
		model.addAttribute("nuevoUsuario", u);
		return "admin/usuarios_registro";
	}
	
	@RequestMapping("guardarUsuario")
	public String guardarUsuario(@ModelAttribute("nuevoUsuario") @Valid Usuario nuevoUsuario, BindingResult resultadoValidaciones, Model model) {
		if (resultadoValidaciones.hasErrors()) {
	        return "admin/usuarios_registro";
	    }
		
		servicioUsuario.registrarUsuario(nuevoUsuario);
		return obtenerUsuarios(model);
	}
	
	@RequestMapping("obtenerUsuarios")
	public String obtenerUsuarios(Model model) {		
		model.addAttribute("usuarios", servicioUsuario.obtenerUsuarios());
		return "admin/usuarios";	
	}
	
	@RequestMapping("borrarUsuario")
	public String borrarUsuario(@RequestParam("id") int id, Model model) {
		servicioUsuario.borrarUsuario(id);
		return obtenerUsuarios(model);
	}
	
}
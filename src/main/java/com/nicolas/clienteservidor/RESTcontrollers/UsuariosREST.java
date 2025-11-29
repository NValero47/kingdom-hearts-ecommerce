package com.nicolas.clienteservidor.RESTcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioUsuario;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("usuariosREST/")
public class UsuariosREST {
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	@RequestMapping("registrar")
	public String registrar(String nombre, String email, String pass, String ciudad, String codigoPostal){
		
		if(servicioUsuario.obtenerUsuarioPorEmail(email) != null) {
			return "Email ya registrado";
		}
		System.out.println("Voy a registrar este usuario: " + nombre + " " +  email + " " + pass + " " + ciudad + " " + codigoPostal);
		servicioUsuario.registrarUsuario(new Usuario(nombre, email, pass, ciudad, Integer.parseInt(codigoPostal)));
		return "Se ha registrado con exito, ya puedes iniciar sesion";
	}
	
	@RequestMapping("login")
	public String login(String email, String pass, HttpServletRequest request){
		Usuario u = servicioUsuario.obtenerUsuarioPorEmailYPass(email, pass);
		String respuesta = "";
		if(u != null) {
			request.getSession().setAttribute("usuario_id", u.getId());
			respuesta = "Ok," + u.getNombre();
		}else {
			respuesta = "No se ha podido iniciar sesión, email o contraseña incorrectos";
		}
		return respuesta;
	}
	
	@RequestMapping("logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout correcto");
    }
	
	@RequestMapping("getNombre")
	public String getNombre(Integer id) {
	    Usuario u = servicioUsuario.obtenerUsuarioPorId(id);
	    return (u != null) ? u.getNombre() : "";
	}
	
}
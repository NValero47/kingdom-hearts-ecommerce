package com.nicolas.clienteservidor.servicios;

import java.util.List;

import com.nicolas.clienteservidor.model.Usuario;


// En java empresarial hay muchos conceptos confusos en este caso se llama servicio a la interfaz que va a definir 
// las operaciones posibles con usuarios, casi igual que cuando hemos usado el paquete repository o el paquete daos

public interface ServicioUsuario {
	
	void registrarUsuario(Usuario usuario);
	
	List<Usuario> obtenerUsuarios();
	
	void borrarUsuario(int id);
	
	Usuario obtenerUsuarioPorId(int id);
	
	Usuario obtenerUsuarioPorEmail(String email);

	void actualizarUsuario(Usuario usuarioEditar);

	Usuario obtenerUsuarioPorEmailYPass(String email, String pass);
	
}
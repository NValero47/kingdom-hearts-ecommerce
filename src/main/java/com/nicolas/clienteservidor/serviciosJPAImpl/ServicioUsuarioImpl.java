package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioUsuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

	// Vamos a implementar esta clase con JPA
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarUsuario(Usuario usuario) {
	    try {
	        if (usuario.getImagen() != null && !usuario.getImagen().isEmpty()) {
	            usuario.setImagenPortada(usuario.getImagen().getBytes());
	        }
	    } catch (IOException e) {
	        System.out.println("Error al procesar la imagen del usuario");
	        e.printStackTrace();
	    }

	    entityManager.persist(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		// Ahora no hay criteria, porque no se puede usar en JPA
		// Para obtener datos y hacer consultas: 
		// SQL nativo
		// JPQL -> Pseudo SQL, muy sencillo, este devuelve objetos
		List<Usuario> usuarios = entityManager.createQuery("SELECT u FROM Usuario u ORDER BY u.id DESC").getResultList();
		return usuarios;
	}

	@Override
	public void borrarUsuario(int id) {
		Usuario usuarioBorrar = obtenerUsuarioPorId(id);
		if(usuarioBorrar != null) {
			entityManager.createNativeQuery("DELETE FROM PRODUCTOS_PEDIDO WHERE PEDIDO_ID IN (SELECT ID FROM PEDIDO WHERE USUARIO_ID = :id)").setParameter("id", id).executeUpdate();
			entityManager.createNativeQuery("DELETE FROM PEDIDO WHERE USUARIO_ID = :id").setParameter("id", id).executeUpdate();
			entityManager.createNativeQuery("DELETE FROM CARRITO WHERE USUARIO_ID = :id").setParameter("id", id).executeUpdate();
			entityManager.remove(usuarioBorrar);
		}
	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		return entityManager.find(Usuario.class, id);
	}

	@Override
	public void actualizarUsuario(Usuario usuarioEditar) {
		Usuario usuarioBaseDeDatos = entityManager.find(Usuario.class, usuarioEditar.getId());
		usuarioBaseDeDatos.setNombre(usuarioEditar.getNombre());
		usuarioBaseDeDatos.setEmail(usuarioEditar.getEmail());
		usuarioBaseDeDatos.setCiudad(usuarioEditar.getCiudad());
		usuarioBaseDeDatos.setCodigoPostal(usuarioEditar.getCodigoPostal());
		if (usuarioEditar.getPass() != null && !usuarioEditar.getPass().isBlank()) {
	        usuarioBaseDeDatos.setPass(usuarioEditar.getPass());
	    }
		if(usuarioEditar.getImagen() != null && usuarioEditar.getImagen().getSize() > 0) {
			try {
				usuarioBaseDeDatos.setImagenPortada(usuarioEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("No se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		entityManager.merge(usuarioBaseDeDatos);
		
	}

	@Override
	public Usuario obtenerUsuarioPorEmailYPass(String email, String pass) {
		try {
			Usuario usuario = (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.pass = :pass").setParameter("email", email).setParameter("pass", pass).getSingleResult();
			return usuario;
		} catch (Exception e) {
			System.out.println("No existe el usuario o se introdujo mal algun dato");
		}
		
		return null;
	}

	@Override
	public Usuario obtenerUsuarioPorEmail(String email) {
		try {
			Usuario usuario = (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email").setParameter("email", email).getSingleResult();
			return usuario;
		} catch (Exception e) {
			System.out.println("No existe un usuario con el email indicado");
		}
		return null;
	}
	
}
package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nicolas.clienteservidor.constantesSQL.ConstantesSQL;
import com.nicolas.clienteservidor.model.Carrito;
import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioCarrito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void agregarProducto(int idUsuario, long idProducto, int cantidad) {
		
		// Aunque seria mas eficiente lanzar una SQL
		Usuario usuario = (Usuario) entityManager.find(Usuario.class, idUsuario);
		List<Carrito> productosCarrito = usuario.getProductosCarrito();
		boolean productoEnCarrito = false;
		// Ver si el producto esta en el carrito y en tal caso incrementar su cantidad
		for(Carrito pCarrito : productosCarrito) {
			if(pCarrito.getProducto().getId() == idProducto) {
				productoEnCarrito = true;
				pCarrito.setCantidad(pCarrito.getCantidad() + cantidad);
				entityManager.merge(pCarrito);// Actualizamos el registro en carrito
			}
		}
		// Si el producto no esta en el carrito crea un registro nuevo
		if( ! productoEnCarrito ) {
			Carrito productoCarrito = new Carrito();
			productoCarrito.setUsuario(usuario);
			productoCarrito.setProducto(entityManager.find(Producto.class, idProducto));
			productoCarrito.setCantidad(cantidad);
			entityManager.persist(productoCarrito);
		}
	}// End agregarProducto

	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO, Tuple.class);
		query.setParameter("usuario_id", idUsuario);
		List<Tuple> tuples = query.getResultList();

	       List<Map<String, Object>> resultado = new ArrayList<>();
	       for (Tuple tuple : tuples) {
	        Map<String, Object> fila = new HashMap<>();
	        for (TupleElement<?> element : tuple.getElements()) {
	        fila.put(element.getAlias(), tuple.get(element));
	        }
	        resultado.add(fila);
	       }
	       return resultado;
	}

	@Override
	public void quitarProductoCarrito(int idUsuario, Long idProducto) {
		entityManager.createNativeQuery(ConstantesSQL.SQL_ELIMINAR_PRODUCTO_CARRITO).setParameter("usuario_id", idUsuario).setParameter("producto_id", idProducto).executeUpdate();
	}

}
package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.clienteservidor.constantesSQL.ConstantesSQL;
import com.nicolas.clienteservidor.model.Categoria;
import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioProductos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

@Service
@Transactional
public class ServicioProductosImpl implements ServicioProductos{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarProducto(Producto producto) {
		try {
			producto.setImagenPortada(producto.getImagen().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Tenemos que asociar la categoria a nivel de base de datos porque idCategoria tiene puesto @Transient
		Categoria categoria = entityManager.find(Categoria.class, producto.getIdCategoria());
		producto.setCategoria(categoria);
		entityManager.persist(producto);
	}

	@Override
	public List<Producto> obtenerProductos() {
		List<Producto> productos = entityManager.createQuery("SELECT p FROM Producto p ORDER BY p.id desc").getResultList();
		return productos;
	}

	@Override
	public void borrarProducto(long id) {
		entityManager.createNativeQuery("DELETE FROM PRODUCTO_IMAGENES WHERE PRODUCTO_ID = :id").setParameter("id", id).executeUpdate();
		
		// Antes de borrar el producto debemos eliminar todas las referencias al mismo en el carrito y pedidos
		entityManager.createNativeQuery("DELETE FROM CARRITO WHERE PRODUCTO_ID = :id").setParameter("id", id).executeUpdate();
		entityManager.createNativeQuery("DELETE FROM PRODUCTOS_PEDIDO WHERE PRODUCTO_ID = :id").setParameter("id", id).executeUpdate();
		
		// createNativeQuery lanza sl, no confundir con createQuery que lanza JPQL
		entityManager.createNativeQuery("DELETE FROM productos WHERE id = :id").setParameter("id", id).executeUpdate();
		
	}

	@Override
	public void actualizarProducto(Producto productoEditar) {
		// Lo siguiente es un tanto bestia, porque hay campos sensibles como por ejemplo el byte[]
		// entityManager.merge(producto);
		Producto productoBaseDeDatos = entityManager.find(Producto.class, productoEditar.getId());
		productoBaseDeDatos.setNombre(productoEditar.getNombre());
		productoBaseDeDatos.setDescripcion(productoEditar.getDescripcion());
		productoBaseDeDatos.setPrecio(productoEditar.getPrecio());
		productoBaseDeDatos.setEstado(productoEditar.getEstado());
		productoBaseDeDatos.setTipo(productoEditar.getTipo());
		productoBaseDeDatos.setStock(productoEditar.getStock());
		if(productoEditar.getImagen() != null && productoEditar.getImagen().getSize() > 0) {
			try {
				productoBaseDeDatos.setImagenPortada(productoEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("No se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		productoBaseDeDatos.setCategoria(entityManager.find(Categoria.class, productoEditar.getIdCategoria()));
		entityManager.merge(productoBaseDeDatos);
	}

	@Override
	public Producto obtenerProductoPorId(long id) {
		Producto producto = entityManager.find(Producto.class, id);
		return producto;
	}

	@Override
	   public List<Map<String, Object>> obtenerProductosParaFormarJSON() {
	       Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRODUCTOS_PARA_JSON, Tuple.class);
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

}

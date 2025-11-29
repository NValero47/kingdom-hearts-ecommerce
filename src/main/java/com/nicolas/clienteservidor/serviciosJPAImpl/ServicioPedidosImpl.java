package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.nicolas.clienteservidor.constantes.Estados;
import com.nicolas.clienteservidor.constantesSQL.ConstantesSQL;
import com.nicolas.clienteservidor.model.Carrito;
import com.nicolas.clienteservidor.model.Pedido;
import com.nicolas.clienteservidor.model.ProductosPedido;
import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioCarrito;
import com.nicolas.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioPedidosImpl implements ServicioPedidos{

	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery("SELECT p FROM Pedido p ORDER BY p.id DESC", Pedido.class).getResultList();
	}

	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return (Pedido) entityManager.find(Pedido.class, idPedido);
	}

	@Override
	public void actualizarPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);
		entityManager.merge(p);
		
	}
	
	@Override
	public void procesarPaso1(String nombre, String direccion, String provincia, String portal, String piso, String telefono, int idUsuario) {
		Pedido pedido = obtenerPedidoIncompletoActual(idUsuario);
		pedido.setNombreCompleto(nombre);
		pedido.setDireccion(direccion);
		pedido.setProvincia(provincia);
		pedido.setPortal(portal);
		pedido.setPiso(piso);
		pedido.setTelefono(telefono);
		entityManager.merge(pedido);
	}
	
	// Este metodo devuelve el pedido incompleto actual del usuario, si no existe, lo creamos
	private Pedido obtenerPedidoIncompletoActual(int idUsauario) {
		Usuario usuario = entityManager.find(Usuario.class, idUsauario);
		Object pedidoIncompleto = null;
		List<Pedido> pedidos = entityManager.createQuery("SELECT p FROM Pedido p WHERE p.estado = :estado AND p.usuario.id = :usuario_id").setParameter("estado", Estados.INCOMPLETO.name()).setParameter("usuario_id", idUsauario).getResultList();
		if(pedidos.size() ==  1) {
			pedidoIncompleto = pedidos.get(0);
		}
		Pedido pedido = null;
		if(pedidoIncompleto != null) {
			pedido = (Pedido) pedidoIncompleto;
		}else {
			pedido = new Pedido();
			pedido.setEstado(Estados.INCOMPLETO.name());
			pedido.setUsuario(usuario);
		}
		return pedido;
	}

	@Override
	public ResumenPedido procesarPaso2(String tarjeta, String numero, String titular, int idUsuario) {
		Pedido pedido = obtenerPedidoIncompletoActual(idUsuario);
		pedido.setTipoTarjeta(tarjeta);
		pedido.setNumeroTarjeta(numero);
		pedido.setTitularTarjeta(titular);
		entityManager.merge(pedido);
		// Preparamos el ResumenPedido a devolver
		
		return obtenerResumenDelPedido(idUsuario, pedido);
	}
	
	@Override
	public ResumenPedido obtenerResumenDelPedido(int idUsuario, Pedido pedido) {
		ResumenPedido resumen = new ResumenPedido();
		resumen.setNombreCompleto(pedido.getNombreCompleto());
		resumen.setDireccion(pedido.getDireccion());
		resumen.setProvincia(pedido.getProvincia());
		resumen.setPortal(pedido.getPortal());
	    resumen.setPiso(pedido.getPiso());
	    resumen.setTelefono(pedido.getTelefono());
		resumen.setTipoTarjeta(pedido.getTipoTarjeta());
		resumen.setTitularTarjeta(pedido.getTitularTarjeta());
		String ultimos4 = pedido.getNumeroTarjeta().substring(pedido.getNumeroTarjeta().length() - 4);
		resumen.setNumeroTarjeta("**** **** **** " + ultimos4);
		resumen.setPersonalidad(pedido.getPersonalidad());
	    resumen.setComentario(pedido.getComentario());
		resumen.setProductos(servicioCarrito.obtenerProductosCarrito(idUsuario));
		Map<String, String> tiposTarjeta = Map.of(
		        "1", "VISA",
		        "2", "MasterCard"
		    );
		String tipoTarjeta = pedido.getTipoTarjeta();
		if(tipoTarjeta == null) tipoTarjeta = "0"; // o algún valor por defecto que tengas en el Map
		resumen.setTipoTarjetaTexto(tiposTarjeta.getOrDefault(tipoTarjeta, "Desconocido"));
		Map<String, String> personalidades = Map.of(
				"1", "Una persona tranquila y responsable",
				"2", "Alguien alegre y con mucha energía",
				"3", "Una persona creativa y original",
				"4", "Un poco de todo, depende del día"
			);
		String personalidad = pedido.getPersonalidad();
		if(personalidad == null) personalidad = "0"; // valor por defecto
		resumen.setPersonalidadTexto(personalidades.getOrDefault(personalidad, "No especificado"));
		
		return resumen;
	}

	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		List<Carrito> c = entityManager.createQuery("SELECT c FROM Carrito c WHERE c.usuario.id = :usuario_id").setParameter("usuario_id", idUsuario).getResultList();
		System.out.println("Productos en el carrito a procesar: " + c.size());
		for(Carrito productoCarrito : c) {
			ProductosPedido pp = new ProductosPedido();
			pp.setCantidad(productoCarrito.getCantidad());
			pp.setProducto(productoCarrito.getProducto());
			pp.setPedido(p);
			entityManager.persist(pp);
		}
		p.setEstado(Estados.TERMINADO.name());
		entityManager.merge(p);
		
		// Eliminar los productos del carrito
		entityManager.createNativeQuery(ConstantesSQL.SQL_VACIAR_CARRITO).setParameter("usuario_id", idUsuario).executeUpdate();
	}
	
	@Override
	public void procesarPaso3(String personalidad, String comentario, int idUsuario) {
		Pedido pedido = obtenerPedidoIncompletoActual(idUsuario);
	    pedido.setPersonalidad(personalidad);
	    pedido.setComentario(comentario);
	    entityManager.merge(pedido);
	}
	
	@Override
	public Pedido obtenerPedidoIncompleto(int idUsuario) {
	    return obtenerPedidoIncompletoActual(idUsuario);
	}

	@Override
	public List<Map<String, Object>> obtenerPedidosPorIdUsuario(int idUsuario) {

	    Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PEDIDOS_USUARIO, Tuple.class);
	    query.setParameter("usuario_id", idUsuario);

	    List<Tuple> tuples = query.getResultList();
	    List<Map<String, Object>> resultado = new ArrayList<>();

	    Map<String, String> tiposTarjeta = Map.of(
	        "1", "VISA",
	        "2", "MasterCard"
	    );

	    Map<String, String> personalidades = Map.of(
	        "1", "Una persona tranquila y responsable",
	        "2", "Alguien alegre y con mucha energía",
	        "3", "Una persona creativa y original",
	        "4", "Un poco de todo, depende del día"
	    );

	    for (Tuple tuple : tuples) {

	        Map<String, Object> fila = new HashMap<>();

	        // Copiar todos los campos tal cual vienen del SQL
	        for (TupleElement<?> element : tuple.getElements()) {
	            fila.put(element.getAlias(), tuple.get(element));
	        }

	        // --- Añadir TIPO_TARJETA_TEXTO ---
	        Object tipoTarjetaObj = fila.get("TIPO_TARJETA");
	        String tipoTarjeta = (tipoTarjetaObj != null) ? tipoTarjetaObj.toString() : "0";
	        String tipoTarjetaTexto = tiposTarjeta.getOrDefault(tipoTarjeta, "Desconocido");
	        fila.put("TIPO_TARJETA_TEXTO", tipoTarjetaTexto);

	        // --- Enmascarar número de tarjeta ---
	        Object numTarjetaObj = fila.get("NUMERO_TARJETA");
	        if (numTarjetaObj != null) {
	            String num = numTarjetaObj.toString();
	            if (num.length() >= 4) {
	                String ultimos4 = num.substring(num.length() - 4);
	                fila.put("NUMERO_TARJETA_MASKED", "**** **** **** " + ultimos4);
	            } else {
	                fila.put("NUMERO_TARJETA_MASKED", "****");
	            }
	        } else {
	            fila.put("NUMERO_TARJETA_MASKED", "****");
	        }

	        // --- Personalidad texto ---
	        Object personalidadObj = fila.get("PERSONALIDAD");
	        String personalidad = (personalidadObj != null) ? personalidadObj.toString() : "0";
	        String personalidadTexto = personalidades.getOrDefault(personalidad, "No especificado");
	        fila.put("PERSONALIDAD_TEXTO", personalidadTexto);

	        // --- Obtener productos del pedido ---
	        // Necesitamos el ID del pedido para recuperar los productos
	        Object pedidoIdObj = fila.get("ID");
	        List<Map<String, Object>> productosList = new ArrayList<>();
	        if (pedidoIdObj != null) {
	            Long pedidoId = ((Number) pedidoIdObj).longValue();

	            // Cargar la entidad Pedido desde JPA para acceder a productosPedido
	            Pedido pedido = entityManager.find(Pedido.class, pedidoId);
	            if (pedido != null && pedido.getProductosPedido() != null) {
	                for (ProductosPedido pp : pedido.getProductosPedido()) {
	                    Map<String, Object> prod = new HashMap<>();
	                    prod.put("PRODUCTO_ID", pp.getProducto().getId());
	                    prod.put("NOMBRE", pp.getProducto().getNombre());
	                    prod.put("PRECIO", pp.getProducto().getPrecio());
	                    prod.put("CANTIDAD", pp.getCantidad());
	                    productosList.add(prod);
	                }
	            }
	        }

	        fila.put("PRODUCTOS", productosList);

	        resultado.add(fila);
	    }

	    return resultado;
	}
	
}
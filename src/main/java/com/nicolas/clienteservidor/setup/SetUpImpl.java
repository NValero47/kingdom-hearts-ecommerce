package com.nicolas.clienteservidor.setup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.clienteservidor.model.Carrito;
import com.nicolas.clienteservidor.model.Categoria;
import com.nicolas.clienteservidor.model.Pedido;
import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.model.Usuario;
import com.nicolas.clienteservidor.servicios.ServicioImagenesProducto;
import com.nicolas.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class SetUpImpl implements SetUp {

	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@Autowired
	private ServicioImagenesProducto servicioImagenesProducto;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void prepararRegistros() {
		
		TablaSetUp registroSetUp = null;
		// Si no hay un registro en la tablaSetUp, preparamos los registros iniciales
		try {
			registroSetUp = (TablaSetUp) entityManager.createQuery("SELECT r FROM TablaSetUp r").getSingleResult();
		} catch (Exception e) {
			System.out.println("No se ha encontrado ningun registro en la tabla setup, comenzamos a realizar los registros iniciales...");
		}
		if(registroSetUp != null && registroSetUp.isCompletado()) {
			return;
		}
		// Preparar las categorias iniciales:
		Categoria cBasicos = new Categoria("Basicos", "Lo esencial");
		entityManager.persist(cBasicos);
		Categoria cIntermedios = new Categoria("Intermedios", "Para los que saben algo o ya han jugado lo principal");
		entityManager.persist(cIntermedios);
		Categoria cAvanzados = new Categoria("Avanzados", "Para los que buscan todo sobre la saga");
		entityManager.persist(cAvanzados);
		
		// Preparamos los registros iniciales
		Producto producto1 = new Producto("Kingdom Hearts", 59.99, "El juego original, para la plataforma PS2. Primera versión de Kingdom Hearts.", "Nuevo", "Videojuego", 19);
		producto1.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH.jpg"));
		producto1.setCategoria(cBasicos);
		entityManager.persist(producto1);
		servicioImagenesProducto.guardarImagenExtra(producto1.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH_extra1.jpg"));

		Producto producto2 = new Producto("Kingdom Hearts: Chain of Memories", 49.99, "Secuela de Kingdom Hearts para Game Boy Advance, incluye nuevas mecánicas de cartas.", "Nuevo", "Videojuego", 15);
		producto2.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHCOM.jpg"));
		producto2.setCategoria(cAvanzados);
		entityManager.persist(producto2);
		servicioImagenesProducto.guardarImagenExtra(producto2.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHCOM_extra1.jpg"));

		Producto producto3 = new Producto("Kingdom Hearts II", 69.99, "Segunda entrega de la saga para PS2, con gráficos mejorados y nuevas aventuras.", "Nuevo", "Videojuego", 20);
		producto3.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2.jpg"));
		producto3.setCategoria(cBasicos);
		entityManager.persist(producto3);
		servicioImagenesProducto.guardarImagenExtra(producto3.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2_extra1.jpg"));
		servicioImagenesProducto.guardarImagenExtra(producto3.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2_extra2.jpg"));

		Producto producto4 = new Producto("Kingdom Hearts 358/2 Days", 39.99, "Historia centrada en Roxas, juego para Nintendo DS con gráficos estilo cel-shading.", "Nuevo", "Videojuego", 12);
		producto4.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH358.jpg"));
		producto4.setCategoria(cAvanzados);
		entityManager.persist(producto4);
		servicioImagenesProducto.guardarImagenExtra(producto4.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH358_extra1.jpg"));

		
		Producto producto5 = new Producto("Kingdom Hearts: Birth by Sleep", 59.99, "Precuela para PSP que narra la historia de Terra, Aqua y Ventus.", "Nuevo", "Videojuego", 18);
		producto5.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHBBS.jpg"));
		producto5.setCategoria(cAvanzados);
		entityManager.persist(producto5);
		servicioImagenesProducto.guardarImagenExtra(producto5.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHBBS_extra1.jpg"));
		
		Producto producto6 = new Producto("Kingdom Hearts: Re:Chain of Memories", 29.99, "Remake de Chain of Memories para PS2 con gráficos mejorados y nuevas cinemáticas.", "Nuevo", "Videojuego", 14);
		producto6.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHRECOM.jpg"));
		producto6.setCategoria(cIntermedios);
		entityManager.persist(producto6);
		servicioImagenesProducto.guardarImagenExtra(producto6.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHRECOM_extra1.jpg"));
		servicioImagenesProducto.guardarImagenExtra(producto6.getId(),obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHRECOM_extra2.jpg"));
		
		Producto producto7 = new Producto("Kingdom Hearts HD 1.5 Remix", 79.99, "Compilación remasterizada para PS3 de Kingdom Hearts y Kingdom Hearts Re:Chain of Memories.", "Nuevo", "Videojuego", 25);
		producto7.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH1.5REMIX.jpg"));
		producto7.setCategoria(cIntermedios);
		entityManager.persist(producto7);
		servicioImagenesProducto.guardarImagenExtra(producto7.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH1.5REMIX_extra1.jpg"));
		
		Producto producto8 = new Producto("Kingdom Hearts HD 2.5 Remix", 79.99, "Compilación remasterizada para PS3 que incluye Kingdom Hearts II Final Mix y juegos de PSP.", "Nuevo", "Videojuego", 25);
		producto8.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2.5REMIX.jpg"));
		producto8.setCategoria(cIntermedios);
		entityManager.persist(producto8);
		servicioImagenesProducto.guardarImagenExtra(producto8.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2.5REMIX_extra1.jpg"));
		servicioImagenesProducto.guardarImagenExtra(producto8.getId(),obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH2.5REMIX_extra2.jpg"));
		
		Producto producto9 = new Producto("Kingdom Hearts III", 69.99, "La tercera entrega numerada de la saga, disponible para PS4 y Xbox One.", "Nuevo", "Videojuego", 22);
		producto9.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH3.jpg"));
		producto9.setCategoria(cBasicos);
		entityManager.persist(producto9);
		servicioImagenesProducto.guardarImagenExtra(producto9.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH3_extra1.jpg"));
		servicioImagenesProducto.guardarImagenExtra(producto9.getId(),obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KH3_extra2.jpg"));
		
		Producto producto10 = new Producto("Kingdom Hearts: Melody of Memory", 49.99, "Juego de ritmo musical para PS4 y Nintendo Switch basado en las canciones de la saga.", "Nuevo", "Videojuego", 17);
		producto10.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHMOM.jpg"));
		producto10.setCategoria(cIntermedios);
		entityManager.persist(producto10);
		servicioImagenesProducto.guardarImagenExtra(producto10.getId(), obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHMOM_extra1.jpg"));
		servicioImagenesProducto.guardarImagenExtra(producto10.getId(),obtenerInfoImagen("http://localhost:8082/imagenes_base/productos/KHMOM_extra2.jpg"));
		entityManager.flush(); // NECESARIO
		

		Usuario usuario1 = new Usuario("Sora", "sora@gmail.com", "1234", "Islas del destino", 28040);
		usuario1.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/usuarios/Usuario1.jpg"));
		entityManager.persist(usuario1);
		Usuario usuario2 = new Usuario("Sonic", "sonic@gmail.com", "1234", "Isla navidad", 11001);
		usuario2.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/usuarios/Usuario2.jpg"));
		entityManager.persist(usuario2);
		Usuario usuario3 = new Usuario("Sasuke", "sasuke@gmail.com", "1234", "Konoha", 46001);
		usuario3.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/usuarios/Usuario3.jpg"));
		entityManager.persist(usuario3);
		Usuario usuario4 = new Usuario("Link", "link@gmail.com", "1234", "Bosque Kokiri", 41001);
		usuario4.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/usuarios/Usuario4.jpg"));
		entityManager.persist(usuario4);
		Usuario usuario5 = new Usuario("Sans", "sans@gmail.com", "1234", "Snowdin", 48001);
		usuario5.setImagenPortada(obtenerInfoImagen("http://localhost:8082/imagenes_base/usuarios/Usuario5.jpg"));
		entityManager.persist(usuario5);
		
		// Vamos a meter unos productos en el carrito de varios usuarios
		Carrito registroCarrito1 = new Carrito();
		registroCarrito1.setUsuario(usuario1);
		registroCarrito1.setProducto(producto2);
		registroCarrito1.setCantidad(2);
		entityManager.persist(registroCarrito1);
		Carrito registroCarrito2 = new Carrito();
		registroCarrito2.setUsuario(usuario2);
		registroCarrito2.setProducto(producto5);
		registroCarrito2.setCantidad(10);
		entityManager.persist(registroCarrito2);
		
		// Registrar un pedido para el usuario 1
		servicioPedidos.procesarPaso1("Sora", "Calle gumi", "Ciudad de paso", "12", "1ºA", "+34 666 66 66 66", usuario1.getId());
		servicioPedidos.procesarPaso2("1" ,"1234 1234 1234 1234" ,"Sora" ,usuario1.getId());
		servicioPedidos.procesarPaso3("1", "Portador de la llave espada", usuario1.getId());
		servicioPedidos.confirmarPedido(usuario1.getId());
		
		// Una vez preparados los registros iniciales, marcamos el setup de la siguiente forma
		TablaSetUp registro = new TablaSetUp();
		registro.setCompletado(true);
		entityManager.persist(registro);
	}// End prepararRegistros
	
	// Metodo que nos va a permitir obtener un byte[] de cada archivo de imagenes_base
	private byte[] obtenerInfoImagen(String ruta_origen) {
		byte[] info = null;
		try {
			URL url = new URL(ruta_origen);
			info = IOUtils.toByteArray(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo procesar: " + ruta_origen);
			e.printStackTrace();
		}
		
		return info;
	}
	
}
package com.nicolas.clienteservidor.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.servicios.ServicioCategorias;
import com.nicolas.clienteservidor.servicios.ServicioProductos;

import jakarta.validation.Valid;


@Controller
@RequestMapping("admin/")
public class ProductosController {
	
	@Autowired
	private ServicioProductos servicioProductos;
	
	@Autowired
	private ServicioCategorias servicioCategorias;
	
	@RequestMapping("editarProducto")
	public String editarProducto(@RequestParam("id") Long id, Model model) {
		Producto producto = servicioProductos.obtenerProductoPorId(id);
		producto.setIdCategoria(producto.getCategoria().getId());
		model.addAttribute("productoEditar", producto);
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		return "admin/productos_editar";
	}
	
	@RequestMapping("guardarCambiosProducto")
	public String guardarCambiosProducto(@ModelAttribute("productoEditar") @Valid Producto productoEditar, BindingResult resultadoValidaciones, Model model) {
		if (resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
	        return "admin/productos_editar";
	    }
		
		servicioProductos.actualizarProducto(productoEditar);
		return obtenerProductos(model);
	}
	
	@RequestMapping("registrarProducto")
	public String registrarProducto(Model model) {
		
		Producto p = new Producto();
		p.setPrecio(1);
		model.addAttribute("nuevoProducto", p);
		// Vamos a añadir las categorias al model para que le lleguen a  la vista
		model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
		
		return "admin/productos_registro";
	}
	
	@RequestMapping("guardarProducto")
	public String guardarProducto(@ModelAttribute("nuevoProducto") @Valid Producto nuevoProducto, BindingResult resultadoValidaciones, Model model) {
		if(resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategorias());
			return "admin/productos_registro";
		}
		servicioProductos.registrarProducto(nuevoProducto);
		return obtenerProductos(model);
	}
	
	@RequestMapping("obtenerProductos")
	public String obtenerProductos(Model model) {		
		model.addAttribute("productos", servicioProductos.obtenerProductos());
		return "admin/productos";	
	}
	
	@RequestMapping("borrarProducto")
	public String borrarProducto(@RequestParam("id") Long id, Model model) {
			servicioProductos.borrarProducto(id);
		return obtenerProductos(model);
	}
	
}
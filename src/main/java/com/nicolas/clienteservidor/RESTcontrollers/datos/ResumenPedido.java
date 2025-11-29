package com.nicolas.clienteservidor.RESTcontrollers.datos;

import java.util.List;
import java.util.Map;

public class ResumenPedido {

	// Productos que hay en el carrito
	private List<Map<String, Object>> productos;
	
	// Datos del paso 1
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	private String portal;
	private String piso;
	private String telefono;
	
	// Datos del paso 2
	private String tipoTarjeta;
	private String numeroTarjeta;
	private String titularTarjeta;
	
	// Campos paso 3
	private String personalidad;
	private String comentario;
	
	// Datos para no mostrarle al usuario numeros sino texto
	private String tipoTarjetaTexto;
	private String personalidadTexto;

	public List<Map<String, Object>> getProductos() {
		return productos;
	}
	public void setProductos(List<Map<String, Object>> productos) {
		this.productos = productos;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTitularTarjeta() {
		return titularTarjeta;
	}
	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
	public String getPortal() {
		return portal;
	}
	public void setPortal(String portal) {
		this.portal = portal;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPersonalidad() {
		return personalidad;
	}
	public void setPersonalidad(String personalidad) {
		this.personalidad = personalidad;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTipoTarjetaTexto() {
		return tipoTarjetaTexto;
	}
	public void setTipoTarjetaTexto(String tipoTarjetaTexto) {
		this.tipoTarjetaTexto = tipoTarjetaTexto;
	}
	public String getPersonalidadTexto() {
		return personalidadTexto;
	}
	public void setPersonalidadTexto(String personalidadTexto) {
		this.personalidadTexto = personalidadTexto;
	}
	
}
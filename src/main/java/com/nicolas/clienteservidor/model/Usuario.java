package com.nicolas.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Si no ponemos el @Table(name = "usuarios") el nombre sera el mismo que el de la clase, empezando con minuscula
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
	@Pattern(regexp = "^(?!.* {2})[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ .,:()\\-_\\/]+$", message = "El nombre contiene caracteres no permitidos o espacios múltiples")
	private String nombre;
	
	@NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "Email no válido")
	@Column(unique = true)
	private String email;
	
	@NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres")
	private String pass;
	
	@NotEmpty(message = "La ciudad no puede estar vacía")
    @Size(min = 3, max = 40, message = "La ciudad debe tener entre 3 y 40 caracteres")
	private String ciudad;
	
	@NotNull(message = "Debes indicar el código postal")
	@Min(1000)
	@Max(99999)
	private int codigoPostal;
	
	@OneToMany(mappedBy = "usuario")
	private List<Carrito> productosCarrito = new ArrayList<Carrito>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// Campo para la imagen del producto:
	@Lob
	@Column(name = "imagen_portada", columnDefinition = "longblob")
	private byte[] imagenPortada;
		
	@Transient
	private MultipartFile imagen;

	public Usuario() {
		
	}
	
	public Usuario(String nombre, String email, String pass, String ciudad, int codigoPostal, int id, MultipartFile imagen) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.pass = pass;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.id = id;
		this.imagen = imagen;
	}
	
	public Usuario(String nombre, String email, String pass, String ciudad, int codigoPostal) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.pass = pass;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
	}



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public List<Carrito> getProductosCarrito() {
		return productosCarrito;
	}

	public void setProductosCarrito(List<Carrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}
	
	
}
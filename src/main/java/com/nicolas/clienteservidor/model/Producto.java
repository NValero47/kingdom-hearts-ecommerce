package com.nicolas.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Con @Entity indicamos la tabla a la que va a guardar asociada a esta clase
// Una entidad es una clase asociada a una tabla
// Para que una clase pueda ser entidad debe tener un campo marcado como @Id

@Entity
@Table(name = "productos")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un nomre")
	@Pattern(regexp = "^(?!.* {2})[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ .,:()\\-_\\/]+$", message = "El nombre contiene caracteres no permitidos o espacios múltiples")
	private String nombre;
	@NotNull(message = "Debes insertar un precio")
	@Min(value = 1, message = "El precio minimo es de 1 euro")
	@Max(value = 999, message = "El precio maximo es de 999 euros")
	private double precio;
	private String descripcion;
	@Pattern(regexp = "Nuevo|Usado|Reacondicionado", message = "Estado no válido")
	private String estado;
	@Pattern(regexp = "Videojuego|Accesorio|Consola", message = "Tipo no válido")
	private String tipo;
	@NotNull(message = "Debes insertar un stock")
	@Min(value = 1, message = "El stock minimo es 1")
	@Max(value = 999, message = "El stock maximo es 999")
	private int stock;
	
	@OneToMany(mappedBy = "producto")
	private List<Carrito> carritos = new ArrayList<Carrito>();
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	// Campo para la imagen del producto:
	@Lob
	@Column(name = "imagen_portada", columnDefinition = "longblob")
	private byte[] imagenPortada;
	
	//Campo para imagenes extra
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private List<ProductoImagen> imagenes = new ArrayList<>();
	
	@Transient
	private MultipartFile imagen;
	
	@Transient
	private long idCategoria;
	
	public Producto() {
		
	}

	public Producto(String nombre, double precio, String descripcion, String estado, String tipo, int stock) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tipo = tipo;
		this.stock = stock;
	}
	
	public Producto(long id, String nombre, double precio, String descripcion, String estado, String tipo, int stock) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tipo = tipo;
		this.stock = stock;
	}
	
	public Producto(long id, String nombre, double precio, String descripcion, String estado, String tipo, int stock,
			MultipartFile imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tipo = tipo;
		this.stock = stock;
		this.imagen = imagen;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}
	
	public List<Carrito> getCarritos() {
		return carritos;
	}

	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}	
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion
				+ ", estado=" + estado + ", tipo=" + tipo + ", stock=" + stock + "]";
	}
	
}
package com.nicolas.clienteservidor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_imagenes")
public class ProductoImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public ProductoImagen() {}

    public ProductoImagen(byte[] imagen, Producto producto) {
        this.imagen = imagen;
        this.producto = producto;
    }

    public Long getId() {
    	
    	return id;
    	
    	}
    public byte[] getImagen() {
    	
    	return imagen;
    	
    }
    public void setImagen(byte[] imagen) {
    	
    	this.imagen = imagen; 
    	
    }

    public Producto getProducto() {
    	
    	return producto; 
    	
    }
    public void setProducto(Producto producto) {
    	
    	this.producto = producto; 
    	
    }
}
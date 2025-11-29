package com.nicolas.clienteservidor.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicolas.clienteservidor.model.ProductoImagen;

public interface RepoProductoImagenes extends JpaRepository<ProductoImagen, Long> {
    
	List<ProductoImagen> findByProductoId(Long productoId);
	
}
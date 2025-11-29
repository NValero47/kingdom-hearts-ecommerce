package com.nicolas.clienteservidor.servicios;

import java.util.List;

import com.nicolas.clienteservidor.model.ProductoImagen;

public interface ServicioImagenesProducto {
	
    void guardarImagenExtra(Long idProducto, byte[] datos);
    
    List<ProductoImagen> obtenerImagenes(Long idProducto);
    
    byte[] obtenerImagenPorId(Long idImagen);
    
}
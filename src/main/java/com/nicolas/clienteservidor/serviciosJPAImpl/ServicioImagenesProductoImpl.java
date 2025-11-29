package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.clienteservidor.model.Producto;
import com.nicolas.clienteservidor.model.ProductoImagen;
import com.nicolas.clienteservidor.repositorios.RepoProductoImagenes;
import com.nicolas.clienteservidor.servicios.ServicioImagenesProducto;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioImagenesProductoImpl implements ServicioImagenesProducto {

    @Autowired
    private RepoProductoImagenes repo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void guardarImagenExtra(Long idProducto, byte[] datos) {
        Producto p = entityManager.find(Producto.class, idProducto);

        ProductoImagen img = new ProductoImagen(datos, p);
        repo.save(img);
    }

    @Override
    public List<ProductoImagen> obtenerImagenes(Long idProducto) {
        return repo.findByProductoId(idProducto);
    }

    @Override
    public byte[] obtenerImagenPorId(Long idImagen) {
        return repo.findById(idImagen)
                   .map(ProductoImagen::getImagen)
                   .orElse(null);
    }
}
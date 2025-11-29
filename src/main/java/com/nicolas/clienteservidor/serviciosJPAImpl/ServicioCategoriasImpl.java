package com.nicolas.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nicolas.clienteservidor.model.Categoria;
import com.nicolas.clienteservidor.servicios.ServicioCategorias;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCategoriasImpl implements ServicioCategorias {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Categoria> obtenerCategorias() {
		
		return entityManager.createQuery("SELECT c FROM Categoria c").getResultList();
	}
	
}
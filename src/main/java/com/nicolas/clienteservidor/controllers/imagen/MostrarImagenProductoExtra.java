package com.nicolas.clienteservidor.controllers.imagen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicolas.clienteservidor.servicios.ServicioImagenesProducto;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MostrarImagenProductoExtra {

    @Autowired
    private ServicioImagenesProducto servicio;

    @RequestMapping("mostrar_imagen_extra")
    public void mostrar(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {

        byte[] info = servicio.obtenerImagenPorId(id);
        if (info == null) return;

        response.setContentType("image/jpeg, image/png, image/jpg");
        response.getOutputStream().write(info);
        response.getOutputStream().close();
    }
}

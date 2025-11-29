package com.nicolas.clienteservidor.interceptores;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Este interceptor se va a ejecutar antes de atender cualquier peticion sobre admin/

@Component
public class InterceptorAdmin implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Interceptar solo las rutas hacia admin/
		if(request.getRequestURI().contains("/admin/")) {
			System.out.println("Se ejecuta el interceptor de admin");
			System.out.println("Aqui vamos a comprobar si en sesión esta identificado el admin");
			
			//Comprobar si nos llega el parametro pass-admin e identificar al usuario como admin si es correcto
			if(request.getParameter("pass-admin") != null) {
				String passAdmin = request.getParameter("pass-admin");
				if(passAdmin.equals("123")) {
					request.getSession().setAttribute("admin", "ok");
				}
			}
			
			// Redirigir al usuario si no esta identificado como admin
			if(request.getSession().getAttribute("admin") == null || !request.getSession().getAttribute("admin").equals("ok")) {
				// Revisa si han intentado acceder sin haber puesto el pass de admin
				response.sendRedirect("../loginAdmin");
				return false;
			}// End comprobar /admin/
		}
		return true;
	}
	
	
}
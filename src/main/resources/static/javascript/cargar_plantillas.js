$.get("plantillas_mustache" + idioma + "/registro.html", function(r){
	plantilla_registro = r
})
$.get("plantillas_mustache" + idioma + "/mis_pedidos.html", function(r){
	plantilla_mis_pedidos = r
})

$.get("plantillas_mustache" + idioma + "/login.html", function(r){
	// Codigo que se ejecuta cuando el navegador ha descargado en r(variable de la funcion) el contenido del archivo html indicado
	plantilla_login = r
})
$.get("plantillas_mustache" + idioma + "/productos.html", function(r){
	plantilla_productos = r
})
$.get("plantillas_mustache" + idioma + "/carrito.html", function(r){
	plantilla_carrito = r
})
$.get("plantillas_mustache" + idioma + "/checkout.html", function(r){
	plantilla_checkout_1 = r
})
$.get("plantillas_mustache" + idioma + "/checkout_2.html", function(r){
	plantilla_checkout_2 = r
})
$.get("plantillas_mustache" + idioma + "/checkout_3.html", function(r){
	plantilla_checkout_3 = r
})
$.get("plantillas_mustache" + idioma + "/checkout_4.html", function(r){
	plantilla_checkout_4 = r
})
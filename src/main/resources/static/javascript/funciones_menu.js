// Funciones modernas de alert y confirm
function mostrarAlerta(mensaje, tipo = 'info') {
    Swal.fire({
        icon: tipo,
        text: mensaje,
        confirmButtonText: 'Aceptar'
    });
}

function mostrarConfirmacion(mensaje, callbackAceptar) {
    Swal.fire({
        icon: 'question',
        text: mensaje,
        showCancelButton: true,
        confirmButtonText: 'Sí',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            callbackAceptar();
        }
    });
}

// Checkout pasos
function checkout_paso_4(json){
    let html = Mustache.render(plantilla_checkout_4, json)
    $("#contenedor").html(html)
    $("#boton_confirmar_pedido").click(function(){
        $.post("pedidosREST/paso4").done(function(res){
            if(res == "ok"){
                obtenerProductos()
            }
        })
    })
}

function checkout_paso_3(){
    $("#contenedor").html(plantilla_checkout_3)
    $("#aceptar_paso_3").submit(function(e){
		e.preventDefault()
        let personalidad = $("#campo_personalidad").val()
        let comentario = $("#campo_comentario").val()
        if(personalidad == "0"){
            mostrarAlerta("Selecciona cómo te defines", "warning")
            return
        }
        $.post("pedidosREST/paso3", {
            personalidad: personalidad,
            comentario: comentario
        }).done(function(res){
			checkout_paso_4(res)
        })
    })
}

function checkout_paso_2(){
    $("#contenedor").html(plantilla_checkout_2)
    $("#aceptar_paso_2").submit(function(e){
		e.preventDefault()
        let tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val()
        if(tipo_tarjeta == "0"){
            mostrarAlerta("Selecciona un tipo de tarjeta", "warning")
            return
        }
		let numero = $("#numero_tarjeta").val().replace(/\s+/g, '');
		if(!/^[0-9]{16}$/.test(numero)){
		    mostrarAlerta("Número de tarjeta inválido. Debe tener 16 dígitos.", "warning");
		    return;
		}
        let titular = $("#titular_tarjeta").val()

        $.post("pedidosREST/paso2", {
            tarjeta : tipo_tarjeta,
            numero : numero,
            titular : titular
        }).done(function(res){
			    checkout_paso_3(res)
        })
    })
}

function checkout_paso_1(){
    let nombre = $("#campo_nombre").val()
    let direccion = $("#campo_direccion").val()
    let provincia = $("#campo_provincia").val()
    let portal = $("#campo_portal").val()
    let piso = $("#campo_piso").val()
    let telefono = $("#campo_telefono").val()
    $.post("pedidosREST/paso1", {
        nombre : nombre,
        direccion : direccion,
        portal : portal,
        piso : piso,
        telefono : telefono,
        provincia : provincia
    }).done(function(res){
        if (res == "ok"){
			checkout_paso_2()
        }
    })
}

function checkout_paso_0(){
    $("#contenedor").html(plantilla_checkout_1)
    $("#aceptar_paso_1").submit(
		function(e){
			e.preventDefault()
			checkout_paso_1()
		})
	}


// Mostrar carrito
function mostrarCarrito(){
    if(nombre_login == ""){
        mostrarAlerta("Inicie sesión para acceder al carrito.", "info")
    } else {
        $.get("carritoREST/obtener", function(r){
            if(r.length == 0){
                mostrarAlerta("Aun no has agregado nada al carrito", "info")
				obtenerProductos()
            } else {
                $("#login_usuario").html("Carrito de " + nombre_login)
                let html = Mustache.render(plantilla_carrito,r)
                $("#contenedor").html(html)
                $("#realizar_pedido").click(checkout_paso_0)
                
                $(".enlace-borrar-producto-carrito").click(function(){
                    let idProducto = $(this).attr("id-producto")
                    mostrarConfirmacion("¿Estás seguro de borrar este producto?", function(){
                        $.post("carritoREST/eliminar", { id : idProducto })
                        .done(function(res){
                            if(res == "Eliminado con exito"){
                                mostrarCarrito()
                            }
                        });
                    });
                });
            }
        });
    }
}

// Mostrar pedidos
function mostrarPedidos(){
    if(nombre_login == ""){
        mostrarAlerta("Inicie sesión para acceder a los pedidos.", "info")
    } else {
        $.get("pedidosREST/obtener", function(r){
            if(r.length == 0){
                mostrarAlerta("Aun no has realizado ningun pedido", "info")
				obtenerProductos()
            } else {
                $("#login_usuario").html("Pedidos de " + nombre_login)
                let html = Mustache.render(plantilla_mis_pedidos,r)
                $("#contenedor").html(html)
				
            }
        });
    }
}

// Comprar producto
function comprar_producto(){
    if(nombre_login == ""){
        mostrarAlerta("Inicie sesión para comprar productos.", "info")
    } else {
        let id_producto = $(this).attr("id-producto")
        mostrarAlerta("Añadir producto al carrito de " + nombre_login + " IdProducto: " + id_producto, "success")
        $.post("carritoREST/agregarProducto", {
            id : id_producto,
            cantidad : 1
        }).done(function(res){
            mostrarAlerta(res, "success")
        })
    }
}

// Obtener productos
function obtenerProductos(){
    if(nombre_login != ""){
        $("#login_usuario").html("Hola " + nombre_login)
    }
    $("#contenedor").html("Cargando...")
    $.get("productosREST/obtener", function(r){
        let productos = r
        console.log(productos)
        let info = Mustache.render(plantilla_productos, {array_productos: productos})
        $("#contenedor").html(info);
    })
}

// Login
function mostrarLogin(){
    if(nombre_login != ""){
        mostrarConfirmacion("¿Está seguro de que desea cerrar sesión?", function(){
            $.get("usuariosREST/logout").done(function(res){
                nombre_login = "";
                $("#login_usuario").html("Usuario no identificado");
                $("#enlace_login").html("Iniciar sesión");
                obtenerProductos();
            });
        });
    } else {
        $("#contenedor").html(plantilla_login)
        $("#form_login").submit(function(e){
            e.preventDefault()
            let email = $("#email").val()
            let pass = $("#pass").val()
            $.post("usuariosREST/login", { email: email, pass: pass })
            .done(function(res){
                let partes = res.split(",")
                if(partes[0] == "Ok"){
                    nombre_login = partes[1]
                    $("#login_usuario").html("Hola " + partes[1])
					let textoCerrar = "Cerrar sesión";
					if (idioma === "_en") {
					    textoCerrar = "Log out";
					} else if (idioma === "_jap") {
					    textoCerrar = "ログアウト";
					}
                    $("#enlace_login").html(textoCerrar);
                    obtenerProductos()
                } else {
					mostrarAlerta("Email o contraseña incorrectos", "error")
				}
            })
        })
    }
}

// Registro
function mostrarRegistro() {
    if(nombre_login != ""){
        $("#login_usuario").html("Hola " + nombre_login)
    }
    $("#contenedor").html(plantilla_registro)
    $("#form_registro").submit(function(e){
        e.preventDefault()
        let nombre = $("#nombre").val()
        let email = $("#email").val()
        let pass = $("#pass").val()
        let ciudad = $("#ciudad").val()
        let codigoPostal = $("#codigoPostal").val()
        $.post("usuariosREST/registrar", {
            nombre, email, pass, ciudad, codigoPostal
        }).done(function(res){
            mostrarAlerta(res, "success")
        })
    })
}

// Eventos principales
$("#enlace_pedidos").click(mostrarPedidos)
$("#enlace_productos").click(obtenerProductos)
$("#enlace_login").click(mostrarLogin)
$("#enlace_registro").click(mostrarRegistro)
$("#enlace_carrito").click(mostrarCarrito)
$(document).on("click", ".enlace_comprar_producto", comprar_producto)
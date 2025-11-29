package com.nicolas.clienteservidor.constantesSQL;

public class ConstantesSQL {
	
	public static final String SQL_OBTENER_PRODUCTOS_PARA_JSON = "SELECT p.id, p.nombre, p.precio, p.descripcion, p.estado, p.tipo, p.stock, c.nombre AS nombre_categoria FROM productos AS p, categoria AS c WHERE p.categoria_id = c.id  ORDER BY p.id DESC";
	
	public static final String SQL_OBTENER_PRODUCTOS_CARRITO = "SELECT C.USUARIO_ID, P.NOMBRE, P.ID AS PRODUCTO_ID, P.PRECIO, P.ESTADO, C.CANTIDAD FROM CARRITO AS C, PRODUCTOS AS P WHERE USUARIO_ID = :usuario_id AND P.ID = C.PRODUCTO_ID";

	public static final String SQL_ELIMINAR_PRODUCTO_CARRITO = "DELETE FROM CARRITO WHERE PRODUCTO_ID = :producto_id AND USUARIO_ID = :usuario_id";

	public static final String SQL_VACIAR_CARRITO = "DELETE FROM CARRITO WHERE USUARIO_ID = :usuario_id";
	
	public static final String SQL_OBTENER_PEDIDOS_USUARIO = "SELECT * FROM PEDIDO WHERE USUARIO_ID = :usuario_id";
}
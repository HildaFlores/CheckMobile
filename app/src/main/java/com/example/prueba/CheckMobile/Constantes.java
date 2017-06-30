package com.example.prueba.CheckMobile;

/**
 * Created by Prueba on 19-jun-17.
 */

public class Constantes {

    public static String USUARIO_DB = "";
    public static String CLAVE_DB = "";
    public static String IP_SERVIDOR = "";
    public static String PUERTO_DB = "";
    public static String SERVICIO_DB = "";
    public static String URL_DB = "jdbc:oracle:thin:@%s:%s:%s";
    public static final String USUARIO_PROPIEDAD = "user";
    public static final String CLAVE_PROPIEDAD = "password";
    public static final String ID_EMPRESA = "3";

    // tipos de respuestas
    public static final String JSON = "application/getJsonTransformer";

    // codigos de respuestas
    public static final String RESPONSE_CODE_OK = "200";
    public static final String RESPONSE_CODE_ERROR = "500";

    // formato de fecha
    public static final String DATE_SHORT_FORMAT = "dd/MM/rrrr";
    public static final String DATE_LONG_FORMAT = "dd/MM/rrrrr HH24:mi:ss";
    public static final int SHORT_DATE_FORMAT = 1;
    public static final int LONG_DATE_FORMAT = 2;

    // claves Json - parametros
    public static final String JSON_KEY_INSPECCION = "inspecciones";
    public static final String JSON_KEY_CLIENT = "id_cliente";
    public static final String JSON_KEY_VEHICULO = "referencia";
    public static final String JSON_KEY_MARCA = "id_marca";
    public static final String JSON_KEY_MODELO = "id_modelo";
    public static final String JSON_KEY_ESTILO = "id_estilo";
    public static final String JSON_KEY_MECANICO = "id_rep_ven";
    public static final String JSON_KEY_PRODUCTO_ID = "id_producto";
    public static final String JSON_KEY_PEDIDO_ID = "id_documento";
    public static final String JSON_KEY_TIPO_TRANS = "id_tipo_trans";
    public static final String JSON_KEY_ASESOR = "id_asesor";
    public static final String JSON_KEY_CLASIFICACION = "id_clasificacion";
    public static final String JSON_KEY_VALOR = "valor";
    public static final String JSON_KEY_LISTA = "id_lista";
    public static final String JSON_KEY_RESPONSE_CODE = "responseCode";
    public static final String JSON_USARIO_ADMIN = "usuario";
    public static final String JSON_CLAVE_USUARIO_ADMIN = "clave";
    public static final String JSON_KEY_DOC_IDENTIDAD = "rnc_cedula";
    public static final String JSON_KEY_TYPE = "type";
    public static final String JSON_KEY_API_KEY = "api_key";

    public static final int ROWS_PER_RESPONSE = 500;

}

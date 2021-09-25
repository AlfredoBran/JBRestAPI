package com.josebran.jbrestapi;


import android.util.Base64;

import java.nio.charset.StandardCharsets;

/***
 * Clase que provee de los metodos GET, POST, PUT y DELETE, haciendo que estos sean reutilizables.
 */
public class JBRestAPI {
    /***
     * Metodo que devuelve las credenciales de un usuario en una cadena en Base64
     * @param usuario El nombre del usuario
     *<p>
     * @param contraseña lA contraseña del usuario
     *<p>
     * @return Un String con las credenciales del usuario en Base64
     */
    public String getCredenciales(String usuario, String contraseña){
        String result="";
        String temp=usuario+":"+contraseña;
        byte[] bytes= temp.getBytes(StandardCharsets.UTF_8);
        result = Base64.encodeToString(bytes, Base64.DEFAULT);
        return result;
    }

    /***
     * Metodo GET que consume un REST API, de contenido JSON
     * @param url La URL del servicio a consumir
     *<p>
     * @param data La data a Enviar al Servidor en formato JSON
     *<p>
     * @param credenciales Las credenciales del usuario que consumira el Servicio
     *<p>
     * @return Un string con la respuesta del servidor a la solicitud del cliente
     */

    public String get(String url, String data, String credenciales){
        Get object = new Get();
        String result = object.get(url, data, credenciales);
        return result;
    }

    /***
     * Metodo PUT que consume un REST API, de contenido JSON
     * @param url La URL del servicio a consumir
     *<p>
     * @param data La data a Enviar al Servidor en formato JSON
     *<p>
     * @param credenciales Las credenciales del usuario que consumira el Servicio
     *<p>
     * @return Un string con la respuesta del servidor a la solicitud del cliente
     */
    public String put(String url, String data, String credenciales){
        Put object = new Put();
        String result = object.put(url, data, credenciales);
        return result;
    }

    /***
     * Metodo POST que consume un REST API, de contenido JSON
     * @param url La URL del servicio a consumir
     *<p>
     * @param data La data a Enviar al Servidor en formato JSON
     *<p>
     * @param credenciales Las credenciales del usuario que consumira el Servicio
     *<p>
     * @return Un string con la respuesta del servidor a la solicitud del cliente
     */
    public String post(String url, String data, String credenciales){
        Post object = new Post();
        String result = object.post(url, data, credenciales);
        return result;
    }

    /***
     * Metodo DELETE que consume un REST API, de contenido JSON
     * @param url La URL del servicio a consumir
     *<p>
     * @param data La data a Enviar al Servidor en formato JSON
     *<p>
     * @param credenciales Las credenciales del usuario que consumira el Servicio
     * <p>
     * @return Un string con la respuesta del servidor a la solicitud del cliente
     */
    public String delete(String url, String data, String credenciales){
        Delete object = new Delete();
        String result = object.delete(url, data, credenciales);
        return result;
    }
}



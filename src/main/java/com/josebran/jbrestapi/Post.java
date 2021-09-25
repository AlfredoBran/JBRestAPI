package com.josebran.jbrestapi;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

class Post extends Thread {

    public Post(){

    }

    public String getCredenciales(String usuario, String contraseña){
        String result="";
        String temp=usuario+":"+contraseña;
        byte[] bytes= temp.getBytes(StandardCharsets.UTF_8);
        result = Base64.encodeToString(bytes, Base64.DEFAULT);
        return result;
    }

    public String post(String url, String data, String credenciales){
        this.setCredenciales(credenciales);
        this.setData(data);
        this.setUrl(url);
        this.start();
        while(this.getState() !=Thread.State.TERMINATED){
        }
        return getRespuesta();
    }
    private String url;
    private String credenciales;
    private String data;
    private String respuesta;

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, java.nio.charset.Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Override
    public void run() {
        try {
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Hilo creado");
            Log.d("Inicia la creacion del hilo de consulta Post: ", getUrl());
            Log.d("Inicia la creacion del hilo de consulta Post: ", getData());
            Log.d("Inicia la creacion del hilo de consulta Post: ", getCredenciales());
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Se crea el objeto url");
            URL endPoint = new URL(getUrl());
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Se crea la Conecxion");
            HttpsURLConnection conexion = (HttpsURLConnection) endPoint.openConnection();
            conexion.setRequestMethod("POST");
            // Activar método POST
            conexion.setDoOutput(true);
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Setea el metodo");
            conexion.setRequestProperty("Authorization", "Basic "+getCredenciales());
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Setea el encabezado");
            conexion.setRequestProperty("Content-Type", "application/json");
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Setea el contenido");
            conexion.setRequestProperty("Accept", "application/json");
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Setea lo que acepta el rest api");
            OutputStream out = conexion.getOutputStream();
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Crea el OutPut Stream");
            // Usas tu método ingeniado para convertir el archivo a bytes
            out.write(getData().getBytes());
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Escribe los Bytes");
            out.flush();
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Reaiza el Flush");
            out.close();
            Log.d("Inicia la creacion del hilo de consulta Post: ", "Cierra la escritura");

            if(conexion.getResponseCode()==200){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "Se obtuvo una respuesta positiva del RestAPI");
                InputStream responseBody = conexion.getInputStream();
                String temporal = readFromStream(responseBody);
                Log.d("Inicia la creacion del hilo de consulta Post, resultado del metodo: ", temporal);
                setRespuesta(temporal);
            }
            if(conexion.getResponseCode()==201){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "Se obtuvo una respuesta positiva del CREATING RestAPI");
                InputStream responseBody = conexion.getInputStream();
                String temporal = readFromStream(responseBody);
                Log.d("Inicia la creacion del hilo de consulta Post, resultado del metodo: ", temporal);
                //setRespuesta(temporal);
            }

            if(conexion.getResponseCode()==204){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "Se ejecuto la accion, pero no hay respuesta por parte del servidor");
            }
            if(conexion.getResponseCode()==403){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "No tiene los permisos necesarios del RestAPI");
            }
            if(conexion.getResponseCode()==406){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "El formato de Devolucion de la informacion no es el del servidor");
            }


            if(conexion.getResponseCode()==404){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "No encontro el recurso del RestAPI");
            }
            if(conexion.getResponseCode()==401){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "No esta autorizado para consumir el recurso del RestAPI");
            }
            if(conexion.getResponseCode()==500){
                Log.d("Inicia la creacion del hilo de consulta Post: ", "Error Interno del servidor del RestAPI");
            }
            Log.d("Inicia la creacion del hilo de consulta Post, mensaje del resultado: ", conexion.getResponseMessage());


            Log.d("Inicia la creacion del hilo de consulta Post: ", "Finalizo la consulta al RestAPI");
        }catch (Exception e) {
            e.printStackTrace();
            Log.d("Exepcion disparada en el hilo de consulta Post: ", e.toString());
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCredenciales() {
        return credenciales;
    }

    public void setCredenciales(String credenciales) {
        this.credenciales = credenciales;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}

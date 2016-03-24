package com.bbva.hitss.directorio.Http.Handlers;

import android.os.AsyncTask;

import com.bbva.hitss.directorio.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hitss on 10/03/2016.
 */
public class GoogleSpreadsheetResponse extends AsyncTask<String, Void, String> {

    final String TIMEOUT = "No fue posible descargar el contenido de la pagina.";
    AsyncResult callback;

    public GoogleSpreadsheetResponse(AsyncResult callback) {

        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urls) {

        /*
        Los parametros que vienen dentro de la llamada del execute son la url asi que se toma el primer valor urls[0]
        y se realiza la descarga
         */
        try {
            return descargarContenido(urls[0]);
        } catch (IOException e) {
            Utils.log("CONSOLE LOG: ", TIMEOUT);
            Utils.log("IOEXCEPTION: ", "Se presento un IOException");
            return TIMEOUT;
        }
    }

    //Muestra el resuptado de el AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Utils.log("RESULT ON POST EXECUTE: ", result);
        if (result.equalsIgnoreCase(TIMEOUT)){
            try {
                JSONObject timeout=new JSONObject("{}");
                callback.onResult(timeout);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            // Se retiran las partes innecesarioas de la respuesta para construir un objeto JSON
            int start = result.indexOf("{", result.indexOf("{") + 1);
            int end = result.lastIndexOf("}");
            String jsonResponse = result.substring(start, end);
            Utils.log("JSON RESPONSE: ", jsonResponse);
            try {
                JSONObject table = new JSONObject(jsonResponse);
                callback.onResult(table);
            } catch (JSONException e) {
                e.printStackTrace(

                );
            }
        }
    }

    private String descargarContenido(String urlString) throws IOException {
        InputStream is = null;

        try {

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.addRequestProperty("Referer", "google.com");
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Se comienza a realizar la Query
            conn.connect();
            // Se verifica si la respuesta
            Utils.log("GOOGLE DRIVE: ", "Descargando contendio de Drive");
            Utils.log("RESPUESTA GOOGLE DRIVE: ", "Descargando contendio de Drive");
            is = conn.getInputStream();
            Utils.log("GOOGLE DRIVE INPUT STREAM: ", is.toString());
            String contentAsString = convertStreamToString(is);
            Utils.log("GOOGLE DRIVE INPUT STREAM: ", contentAsString);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

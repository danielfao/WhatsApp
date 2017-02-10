package com.example.danieloliveira.whatsapp.helper;

import android.util.Base64;

/**
 * Created by Daniel Oliveira on 10/02/2017.
 */

public class Base64Custom {

    public static String converterBase64(String texto) {
        String textoConvertido = Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
        return textoConvertido.trim();
    }

    public static String decodificarBase64(String textoCodificado) {
        byte[] byteDecodificado = Base64.decode(textoCodificado, Base64.DEFAULT);
        return new String(byteDecodificado);
    }
}

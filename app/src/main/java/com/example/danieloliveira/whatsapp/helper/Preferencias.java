package com.example.danieloliveira.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Daniel Oliveira on 08/02/2017.
 */

public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "whatsapp.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuario";

    public Preferencias(Context contextoParametro) {
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE); //ou Context.MODE_PRIVATE ou 0
        editor = preferences.edit();
    }

    public void salvarDados(String identificador) {
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }
}
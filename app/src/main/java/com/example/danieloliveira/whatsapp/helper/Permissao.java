package com.example.danieloliveira.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliveira on 08/02/2017.
 */

public class Permissao {
    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<String>();

            //Percorrer as permissoes e checar se estão liberadas
            for (String permissao : permissoes) {
                Boolean validapermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validapermissao) listaPermissoes.add(permissao);
            }

            //Caso a lista esteja vazia, não é necessário solicitar permissão
            if (listaPermissoes.isEmpty()) return true;

            //casting para array de string
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita permissões
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }
        return true;
    }
}

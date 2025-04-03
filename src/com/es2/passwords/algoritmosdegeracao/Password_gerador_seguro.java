package com.es2.passwords.algoritmosdegeracao;
import com.es2.passwords.PassGerador;
import java.security.SecureRandom;

import com.es2.passwords.ObjectPool_SecureRamdom;

public class Password_gerador_seguro implements PassGerador {
    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int tamanho_pass = 12;

    //atualização pora usar o secure da pool
    private final ObjectPool_SecureRamdom pool = new ObjectPool_SecureRamdom();

    @Override
    public String PalavraPasse_gerada() {

        SecureRandom aleatorio=pool.obtem_secure();//vai buscar o secure da pool

        StringBuilder password = new StringBuilder(tamanho_pass);
        for (int i = 0; i < tamanho_pass; i++) {
            password.append(caracteres.charAt(aleatorio.nextInt(caracteres.length())));
        }

        //no final devolve de volta o SecureRaddom da pool
        pool.devolve_secure(aleatorio);


        return password.toString();
    }
}

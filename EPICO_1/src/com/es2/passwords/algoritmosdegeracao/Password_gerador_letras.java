package com.es2.passwords.algoritmosdegeracao;

import com.es2.passwords.PassGerador;

import java.security.SecureRandom;//usa criptografia
import com.es2.passwords.ObjectPool_SecureRamdom;

public class Password_gerador_letras implements PassGerador {
    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int tamanho_pass = 12;
    //atualização pora usar o secure da pool
    private final ObjectPool_SecureRamdom pool = new ObjectPool_SecureRamdom();

    //defenimos o tamanho da palavra passe e os caracteres que podem vir a ser escolhidos pelo random

    @Override
    public String PalavraPasse_gerada() {
        SecureRandom aleatorio=pool.obtem_secure();//vai buscar o secure da pool

        StringBuilder password = new StringBuilder(tamanho_pass);
        for (int i = 0; i < tamanho_pass; i++) {
            password.append(caracteres.charAt(aleatorio.nextInt(caracteres.length())));//de froma aleatoria
        }

        //no final devolve de volta o SecureRaddom da pool
        pool.devolve_secure(aleatorio);

        return password.toString();
    }
}
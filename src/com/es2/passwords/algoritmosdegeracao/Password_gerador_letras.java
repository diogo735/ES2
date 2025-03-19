package com.es2.passwords.algoritmosdegeracao;

import com.es2.passwords.PassGerador;

import java.security.SecureRandom;//usa criptografia
import java.util.Random;

public class Password_gerador_letras implements PassGerador {
    private static final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int tamanho_pass = 12;
    private final Random random = new SecureRandom();

    //defenimos o tamanho da palavra passe e os caracteres que podem vir a ser escolhidos pelo random

    @Override
    public String PalavraPasse_gerada() {
        StringBuilder password = new StringBuilder(tamanho_pass);
        for (int i = 0; i < tamanho_pass; i++) {
            password.append(caracteres.charAt(random.nextInt(caracteres.length())));//de froma aleatoria
        }
        return password.toString();
    }
}
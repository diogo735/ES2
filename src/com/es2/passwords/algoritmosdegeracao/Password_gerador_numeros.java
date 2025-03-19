package com.es2.passwords.algoritmosdegeracao;

import com.es2.passwords.PassGerador;

import java.security.SecureRandom;
import java.util.Random;

public class Password_gerador_numeros implements PassGerador {
    private static final String numeros = "0123456789";
    private static final int tamanho_pass = 8; // Senha terá 8 dígitos numéricos
    private final Random random = new SecureRandom();

    @Override
    public String PalavraPasse_gerada() {
        StringBuilder password = new StringBuilder(tamanho_pass);
        for (int i = 0; i < tamanho_pass; i++) {
            password.append(numeros.charAt(random.nextInt(numeros.length())));
        }
        return password.toString();
    }
}

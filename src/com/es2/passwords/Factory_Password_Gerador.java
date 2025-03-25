package com.es2.passwords;

import com.es2.passwords.algoritmosdegeracao.Password_gerador_letras;
import com.es2.passwords.algoritmosdegeracao.Password_gerador_numeros;
import com.es2.passwords.algoritmosdegeracao.Password_gerador_seguro;

public class Factory_Password_Gerador{
    public static PassGerador createGenerator(String tipo) {
        switch (tipo.toLowerCase()) {
            case "letras":
                return new Password_gerador_letras();
            case "numeros":
                return new Password_gerador_numeros();
            case "seguro":
                return new Password_gerador_seguro();
            default:
                throw new IllegalArgumentException("Tipo de gerador inválido: " + tipo);
        }
    }
}
//com base num tipo fornecido ,ele cria a instancia de geraçao correta
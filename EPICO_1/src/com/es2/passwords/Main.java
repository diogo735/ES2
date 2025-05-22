package com.es2.passwords;

import com.es2.passwords.autenticacao_metodos.Escolher_metodo;

public class Main {
    public static void main(String[] args) {
        AutenticadorFunc app = new Escolher_metodo(new App_Inicio());
        app.executar();
    }
}


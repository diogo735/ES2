package com.es2.passwords;

public class App_Inicio implements AutenticadorFunc{

    @Override
    public void executar() {
        Main_Principal.run();
    }
}
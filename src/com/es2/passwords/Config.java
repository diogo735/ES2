package com.es2.passwords;

public class Config {
    private static Config instance;
    private String databaseURL;
    private int maxpassguardadas;
    private String user_nome;
    private String user_pass;
    private String email;

    private Config() {
        this.databaseURL = "jdbc:mysql://localhost:3306/app_passwords";
        this.maxpassguardadas= 10;
        this.user_nome = "Diogo Ferreira";
        this.user_pass= "admin123";
        this.email = "user@example.com";
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    public void setUserName(String userName) {
        this.user_nome = userName;
    }

    public void setMasterPass(String masterPass) {
        this.user_pass = masterPass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassuser(String password) {
        return this.user_pass.equals(password);
    }

    public void showConfig() {
        System.out.println("\n Configurações da App:");
        System.out.println("  -User: " + user_nome);
        System.out.println(" -Email: " + email);
        System.out.println(" -Base de Dados: " + databaseURL);
        System.out.println(" -Máximo de Passa Guardadas: " + maxpassguardadas);
    }
}

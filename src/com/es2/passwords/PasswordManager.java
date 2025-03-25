package com.es2.passwords;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PasswordManager {
    private static PasswordManager instance;
    private Map<String, String> sitePasswords;
    private Tipo_Armazenamento armazenamento;


    // configs da app
    private String databaseURL;
    private int maxpassguardadas;
    private String user_nome;
    private String user_pass;
    private String email;

    public PasswordManager(Tipo_Armazenamento armazenamento) {
        this.armazenamento = armazenamento;
        this.sitePasswords = armazenamento.mostrar_tudo();


        // configuração inicial da app
        this.databaseURL = "jdbc:sqlite:passwords.db";
        this.maxpassguardadas = 10;
        this.user_nome = "Diogo Ferreira";
        this.user_pass = "admin123";
        this.email = "user@example.com";
    }
    public static PasswordManager getInstance(Tipo_Armazenamento armazenamento) {
        if (instance == null) {
            instance = new PasswordManager(armazenamento);
        } else if (instance.armazenamento == null) {
            instance.armazenamento = armazenamento;
            instance.sitePasswords = armazenamento.mostrar_tudo(); // carregar novamente
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }


    public void addSite(String site, String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("⚠ Password vazia! Não pode guardar.");
            return;
        }

        if (isValidPassword(password)) {
            sitePasswords.put(site, password);
            armazenamento.guardar(site, password);
            System.out.println("Password guardada para o site: " + site);
        } else {
            System.out.println("⚠ Pass inválida!.");
        }
    }

    public void removeSite(String site) {
        if (sitePasswords.containsKey(site)) {
            sitePasswords.remove(site);
            armazenamento.remover(site);
            System.out.println( site + "REMOVIDO!");
        } else {
            System.out.println("Site não encontrado!");
        }
    }


    public void showSites() {
        System.out.println("\n Passwords guardadas:");
        Map<String, String> dados = armazenamento.mostrar_tudo(); // carrega dados reais

        if (dados.isEmpty()) {
            System.out.println("(nenhuma password guardada)");
            return;
        }

        for (Map.Entry<String, String> entry : dados.entrySet()) {
            System.out.println("Site: " + entry.getKey() + " | Pass: " + entry.getValue());
        }
    }


    public void showConfig() {
        System.out.println("\n Configurações da App:");
        System.out.println("  - User: " + user_nome);
        System.out.println("  - Email: " + email);
        System.out.println("  - Base de Dados: " + databaseURL);
        System.out.println("  - Máximo de Passwords Guardadas: " + maxpassguardadas);
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 4;
    }

    public boolean checkPassuser(String password) {
        return this.user_pass.equals(password);
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
}
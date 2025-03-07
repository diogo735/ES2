package com.es2.passwords;

import java.util.HashMap;
import java.util.Map;

public class PasswordManager {
    private Map<String, String> sitePasswords;

    public PasswordManager() {
        sitePasswords = new HashMap<>();
    }


    public void addSite(String site, String password) {
        if (isValidPassword(password)) {
            sitePasswords.put(site, password);
            System.out.println("Password guardada para o site : " + site);
        } else {
            System.out.println("⚠ Senha invalida!!!.");
        }
    }


    public void removeSite(String site) {
        if (sitePasswords.containsKey(site)) {
            sitePasswords.remove(site);
            System.out.println( site + "REMOVIDO!");
        } else {
            System.out.println("Site não encontrado!");
        }
    }


    public void showSites() {
        System.out.println("\n Passwords guardadas:");
        for (Map.Entry<String, String> entry : sitePasswords.entrySet()) {
            System.out.println("Site: " + entry.getKey() + " | Pass: " + entry.getValue());
        }
    }


    private boolean isValidPassword(String password) {
        return password.length() >= 4;
    }
}

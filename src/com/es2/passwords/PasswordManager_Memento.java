package com.es2.passwords;
import java.util.HashMap;
import java.util.Map;

public class PasswordManager_Memento {
    private final String site;
    private final String password;
    private final String categoria;

    public PasswordManager_Memento(String site, String password, String categoria) {
        this.site = site;
        this.password = password;
        this.categoria = categoria;
    }

    public String getSite() {
        return site;
    }

    public String getPassword() {
        return password;
    }

    public String getCategoria() {
        return categoria;
    }
}

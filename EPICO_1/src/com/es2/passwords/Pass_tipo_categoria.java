package com.es2.passwords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pass_tipo_categoria implements Categoria {
    private final String site;
    private final String password;

    public Pass_tipo_categoria(String site, String password) {
        this.site = site;
        this.password = password;
    }

    @Override
    public void exibir(String prefixo) {
        System.out.println(prefixo + " -> SITE: " + site + ", SENHA: " + password);
    }

    @Override
    public void exibir(String prefixo, Map<String, List<String>> senhaInfo) {
        // A implementação mais simples pode apenas chamar o método exibir original
        this.exibir(prefixo);
    }
    public String getSite() {
        return site;
    }
}

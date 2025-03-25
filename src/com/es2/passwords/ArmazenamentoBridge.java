package com.es2.passwords;

import java.util.Map;

public abstract class ArmazenamentoBridge {
    public abstract void guardar(String categoria, String site, String senha);
    public abstract void remover(String site);
    public abstract Map<String, String[]> mostrar_tudo();
}

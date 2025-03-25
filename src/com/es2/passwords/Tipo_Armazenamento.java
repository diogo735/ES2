package com.es2.passwords;

import java.util.Map;

public interface Tipo_Armazenamento {
    void guardar(String site, String password);
    void remover(String site);
    Map<String, String> mostrar_tudo();
}

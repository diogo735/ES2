package com.es2.passwords;

import java.util.List;
import java.util.Map;

public interface Categoria {
    void exibir(String categoria);
    void exibir(String prefixo, Map<String, List<String>> senhaInfo);
}

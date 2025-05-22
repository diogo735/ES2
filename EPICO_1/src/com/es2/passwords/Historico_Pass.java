package com.es2.passwords;
import java.util.Stack;

public class Historico_Pass {
    private final Stack<PasswordManager_Memento> historico = new Stack<>();

    public void guardar_estado(PasswordManager_Memento memento) {
        historico.push(memento);
    }

    public PasswordManager_Memento restaurar_ultimo_estado() {
        if (!historico.isEmpty()) {
            return historico.pop();
        }
        return null;
    }
}

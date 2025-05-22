package com.es2.passwords.autenticacao_metodos;
import com.es2.passwords.AutenticadorFunc;
import java.util.Scanner;

public class Escolher_metodo implements AutenticadorFunc {
    private final AutenticadorFunc autenticacao;

    public Escolher_metodo(AutenticadorFunc autenticacao) {
        this.autenticacao = autenticacao;
    }

    @Override
    public void executar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🔐 Escolha o método de autenticação:");
        System.out.println("1 - Palavra-passe");
        System.out.println("2 - PIN");
        System.out.print("Opção: ");
        String opcao = scanner.nextLine();

        AutenticadorFunc autenticaoComMetodo;

        switch (opcao) {
            case "2":
                autenticaoComMetodo = new Pin_Decorator(autenticacao);
                break;
            case "1":
            default:
                autenticaoComMetodo = new Password_Decorator(autenticacao);
        }

        autenticaoComMetodo.executar();
    }
}

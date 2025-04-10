package com.es2.passwords.autenticacao_metodos;
import com.es2.passwords.AutenticadorFunc;

import java.util.Scanner;
public class Password_Decorator implements AutenticadorFunc {
    private final AutenticadorFunc autenticao;

    public Password_Decorator(AutenticadorFunc autenticao) {
        this.autenticao = autenticao;

    }
    public void limparEcra() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    @Override
    public void executar() {
        Scanner scanner = new Scanner(System.in);
        int tentativas = 0;

        while (tentativas < 2) {
            System.out.print("Digite a palavra-passe: ");
            String pass = scanner.nextLine();

            if ("admin123".equals(pass)) {
                limparEcra();
                System.out.println("✔ Palavra-passe correta.");
                autenticao.executar();
                return;
            } else {
                tentativas++;
                System.out.println("Palavra-passe incorreta. Tentativa " + tentativas + "/2");
            }
        }

        System.out.println("Número máximo de tentativas excedido. Encerrando aplicação.");
    }
}

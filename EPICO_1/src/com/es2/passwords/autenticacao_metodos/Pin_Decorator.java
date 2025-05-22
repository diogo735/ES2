package com.es2.passwords.autenticacao_metodos;
import com.es2.passwords.AutenticadorFunc;

import java.util.Scanner;

public class Pin_Decorator implements AutenticadorFunc {
    private final AutenticadorFunc funcionalidade;

    public Pin_Decorator(AutenticadorFunc funcionalidade) {
        this.funcionalidade = funcionalidade;
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
            System.out.print("Introduza o PIN de 4 dígitos: ");
            String pin = scanner.nextLine();

            if ("1234".equals(pin)) {
                limparEcra();
                System.out.println("✔ PIN correto.");

                funcionalidade.executar();
                return;
            } else {
                tentativas++;
                System.out.println(" PIN incorreto. Tentativa " + tentativas + "/2");
            }
        }

        System.out.println(" Número máximo de tentativas excedido. Encerrando aplicação.");
    }
}

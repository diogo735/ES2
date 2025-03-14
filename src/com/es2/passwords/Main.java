package com.es2.passwords;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PasswordManager passwordManager = new PasswordManager();
        Config configuracao = Config.getInstance();

        while (true) {
            System.out.println("\n---App de passwords ---");
            System.out.println("1 Adicionar Site e Pass");
            System.out.println("2 Remover Site");
            System.out.println("3 Exibir Pass guardadas");
            System.out.println("4 Mostrar Defenições da APP");
            System.out.println("5 Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (choice) {
                case 1:
                    System.out.print("- Nome do Site: ");
                    String site = scanner.nextLine();
                    System.out.print("* Pass: ");
                    String pass = scanner.nextLine();
                    passwordManager.addSite(site, pass);
                    break;
                case 2:
                    System.out.print("- Site para eliminar: ");
                    String removeSite = scanner.nextLine();
                    passwordManager.removeSite(removeSite);
                    break;
                case 3:
                    passwordManager.showSites();
                    break;
                case 4:
                    configuracao.showConfig();
                    break;
                case 5:
                    System.out.println("exit");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}

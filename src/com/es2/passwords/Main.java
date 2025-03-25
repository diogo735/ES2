package com.es2.passwords;

import com.es2.passwords.algoritmosdearmazenamento.Armazenamento_em_BD;
import com.es2.passwords.algoritmosdearmazenamento.Armazenamento_em_Ficheiro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o tipo de armazenamento:");
        System.out.println("1 - Ficheiro");
        System.out.println("2 - Base de Dados");
        System.out.print("Opção: ");
        int tipoArmazenamento = scanner.nextInt();
        scanner.nextLine();

        ArmazenamentoBridge armazenamento;

        switch (tipoArmazenamento) {
            case 2:
                armazenamento = new Armazenamento_em_BD();
                break;
            case 1:
            default:
                armazenamento = new Armazenamento_em_Ficheiro();
                break;
        }
        PasswordManager.resetInstance();
        PasswordManager passwordManager = PasswordManager.getInstance(armazenamento);

        while (true) {
            System.out.println("\n---App de passwords ---");
            System.out.println("1 Adicionar Site e Pass (NOVA FUNÇÃO GERAR) ");
            System.out.println("2 Remover Site");
            System.out.println("3 Exibir Pass guardadas");
            System.out.println("4 Mostrar Defenições da APP");
            System.out.println("6 Exibir categorias e passwords");
            System.out.println("7 Criar nova categoria");
            System.out.println("8 Criar Subcategoria");
            System.out.println("5 Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    passwordManager.mostrarCategorias();
                    System.out.print("Nome da categoria existente: ");
                    String categoriaAlvo = scanner.nextLine();
                    System.out.print("- Nome do Site: ");
                    String site = scanner.nextLine();
                    System.out.print("* Pass (se deixar vazia gera automaticamente): ");
                    String pass = scanner.nextLine();
                    if (pass.trim().isEmpty()) {
                        System.out.println("Escolha o tipo de gerador de password:");
                        System.out.println("1 - Letras");
                        System.out.println("2 - Números");
                        System.out.println("3 - Seguro");
                        System.out.print("Opção: ");
                        int opcao = scanner.nextInt();
                        scanner.nextLine();

                        String tipo;
                        switch (opcao) {
                            case 1: tipo = "letras"; break;
                            case 2: tipo = "numeros"; break;
                            case 3: tipo = "seguro"; break;
                            default:
                                System.out.println("Opção inválida! Usando 'letras'");
                                tipo = "letras";
                        }

                        pass = Factory_Password_Gerador.createGenerator(tipo).PalavraPasse_gerada();// retoirna uma intancia com o gerador correspondente FACTORY
                        System.out.println("-> Password gerada: " + pass);
                    }
                    passwordManager.addSite(categoriaAlvo,site, pass);//APENAS Guarda
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
                    passwordManager.showConfig();
                    break;
                case 6:
                    passwordManager.mostrarCategorias();
                    break;
                case 7:
                    System.out.print("Digite o nome da nova categoria: ");
                    String nomeCategoria = scanner.nextLine();
                    passwordManager.criarCategoria(nomeCategoria);
                    break;
                case 8:
                    passwordManager.mostrarCategorias();
                    System.out.print("Nome da categoria pai: ");
                    String categoriaPai = scanner.nextLine();
                    System.out.print("Nome da subcategoria: ");
                    String nomeSubcategoria = scanner.nextLine();
                    passwordManager.criarSubcategoria(categoriaPai, nomeSubcategoria);
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
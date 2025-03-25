package com.es2.passwords;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PasswordManager {
    private static PasswordManager instance;
    private Map<String, String> sitePasswords;
    private PassGerador vai_gerar_pass; //objeto do tipo da pass e podia escolher qualquer tipo de password

    // configs da app
    private String databaseURL;
    private int maxpassguardadas;
    private String user_nome;
    private String user_pass;
    private String email;

    public PasswordManager() {
        sitePasswords = new HashMap<>();
        this.vai_gerar_pass= null;

        // configuração inicial da app
        this.databaseURL = "jdbc:mysql://localhost:3306/app_passwords";
        this.maxpassguardadas = 10;
        this.user_nome = "Diogo Ferreira";
        this.user_pass = "admin123";
        this.email = "user@example.com";
    }

    public static PasswordManager getInstance() {
        if (instance == null) {
            instance = new PasswordManager();
        }
        return instance;
    }//padrao singleton-patern

    public void addSite(String site, String password) {
        if (password == null || password.isEmpty()) {
            if (vai_gerar_pass == null) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Escolha o tipo de Pass que deseja gerar:");
                System.out.println("2 - Pass Apenas Numérica");
                System.out.println("1 - Pass Apenas Letras");
                System.out.println("3 - Pass Segura (Letras, Números e Símbolos)");
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                String tipoGerador;
                switch (opcao) {
                    case 2:
                        tipoGerador = "letras";
                        break;
                    case 1:
                        tipoGerador = "numeros";
                        break;
                    case 3:
                        tipoGerador = "seguro";
                        break;
                    default:
                        System.out.println("Opção inválida! Optando pelo gerador por letras.");
                        tipoGerador = "letras";
                }

                vai_gerar_pass = Factory_Password_Gerador.createGenerator(tipoGerador);//cria a instancia do gerador com o Factory patern
                password = vai_gerar_pass.PalavraPasse_gerada();//gera a pass usando o polimorfismo do factory
                System.out.println("-> Pass gerada automaticamente: " + password);

            }

            if (isValidPassword(password)) {
                sitePasswords.put(site, password);
                System.out.println("Password guardada para o site: " + site);
            } else {
                System.out.println("⚠ Pass inválida!.");
            }
        }
    }

    public void removeSite(String site) {
        if (sitePasswords.containsKey(site)) {
            sitePasswords.remove(site);
            System.out.println( site + "REMOVIDO!");
        } else {
            System.out.println("Site não encontrado!");
        }
    }


    public void showSites() {
        System.out.println("\n Passwords guardadas:");
        for (Map.Entry<String, String> entry : sitePasswords.entrySet()) {
            System.out.println("Site: " + entry.getKey() + " | Pass: " + entry.getValue());
        }
    }

    public void showConfig() {
        System.out.println("\n Configurações da App:");
        System.out.println("  - User: " + user_nome);
        System.out.println("  - Email: " + email);
        System.out.println("  - Base de Dados: " + databaseURL);
        System.out.println("  - Máximo de Passwords Guardadas: " + maxpassguardadas);
    }
    private boolean isValidPassword(String password) {
        return password.length() >= 4;
    }

    public boolean checkPassuser(String password) {
        return this.user_pass.equals(password);
    }

    public void setUserName(String userName) {
        this.user_nome = userName;
    }

    public void setMasterPass(String masterPass) {
        this.user_pass = masterPass;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

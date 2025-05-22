package com.es2.passwords;


import com.es2.passwords.Historico_Pass;
import com.es2.passwords.PasswordManager_Memento;
import java.util.*;

public class PasswordManager {
    private static PasswordManager instance;
    private Map<String, String> sitePasswords;
    private ArmazenamentoBridge armazenamento;
    private  Historico_Pass historio = new Historico_Pass();


    // configs da app
    private String databaseURL;
    private int maxpassguardadas;
    private String user_nome;
    private String user_pass;
    private String email;
    private CategoriaComposite raizCategorias = new CategoriaComposite("Root");

    public PasswordManager(ArmazenamentoBridge armazenamento) {
        this.armazenamento = armazenamento;
        this.sitePasswords = new HashMap<>();

        // carregar senhas simples (sem categoria) na memória
        Map<String, String[]> dados = armazenamento.mostrar_tudo();
        for (Map.Entry<String, String[]> entry : dados.entrySet()) {
            String site = entry.getKey();
            String password = entry.getValue()[1]; // [categoria, password]
            this.sitePasswords.put(site, password);
        }


        // configuração inicial da app
        this.databaseURL = "jdbc:sqlite:passwords.db";
        this.maxpassguardadas = 10;
        this.user_nome = "Diogo Ferreira";
        this.user_pass = "admin123";
        this.email = "user@example.com";
    }
    public static PasswordManager getInstance(ArmazenamentoBridge armazenamento) {
        if (instance == null) {
            instance = new PasswordManager(armazenamento);
        } else if (instance.armazenamento == null) {
            instance.armazenamento = armazenamento;
            instance.sitePasswords = new HashMap<>();

            Map<String, String[]> dados = armazenamento.mostrar_tudo();
            for (Map.Entry<String, String[]> entry : dados.entrySet()) {
                String site = entry.getKey();
                String password = entry.getValue()[1]; // [categoria, password]
                instance.sitePasswords.put(site, password);
            }
        }
        return instance;
    }


    public static void resetInstance() {
        instance = null;
    }


    public void addSite(String nomeCategoria, String site, String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("⚠ Password vazia! Não pode guardar.");
            return;
        }

        // Verifique se a categoria realmente existe
        CategoriaComposite categoria = buscarCategoria(nomeCategoria);
        if (categoria == null) {
            System.out.println("⚠ Categoria não encontrada! Não pode guardar.");
            return;
        }

        if (isValidPassword(password)) {
            armazenamento.guardar(nomeCategoria, site, password);
            System.out.println("✔ Password guardada no armazenamento com sucesso.");
        } else {
            System.out.println("⚠ Pass inválida!.");
        }
    }


    public void mostrarCategorias() {
        System.out.println("\nCategorias Existentes:");
        if (raizCategorias != null && !raizCategorias.getFilhos().isEmpty()) {
            Map<String, List<String>> senhaInfo = new HashMap<>();
            // Construa o mapa de informações de senha baseado nos dados do armazenamento
            for (Map.Entry<String, String[]> entry : armazenamento.mostrar_tudo().entrySet()) {
                String site = entry.getKey();
                String categoria = entry.getValue()[0];
                String password = entry.getValue()[1];
                senhaInfo.putIfAbsent(categoria, new ArrayList<>());
                senhaInfo.get(categoria).add(site + ": " + password);
            }
            raizCategorias.exibir("", senhaInfo);
        } else {
            System.out.println("Nenhuma categoria existente.");
        }
    }
    public PasswordManager_Memento salvar_no_historico(String site) {
        Map<String, String[]> dados = armazenamento.mostrar_tudo();
        if (dados.containsKey(site)) {
            String categoria = dados.get(site)[0];
            String password = dados.get(site)[1];
            return new PasswordManager_Memento(site, password, categoria);
        }
        return null;
    }


    public void retaurar_do_historico(PasswordManager_Memento memento) {
        sitePasswords.put(memento.getSite(), memento.getPassword());
        armazenamento.guardar(memento.getCategoria(), memento.getSite(), memento.getPassword());
        System.out.println("✔ Site restaurado: " + memento.getSite());
    }


    public void removeSite(String site) {
        if (armazenamento.mostrar_tudo().containsKey(site)) {
            PasswordManager_Memento memento = salvar_no_historico(site);
            if (memento != null) {
                historio.guardar_estado(memento);
            }

            sitePasswords.remove(site);           // RAM
            armazenamento.remover(site);         // armazenamento
            System.out.println(site + " REMOVIDO do armazenamento!");
        } else {
            System.out.println("Site não encontrado no armazenamento!");
        }
    }

    public void desfazer_remover() {
        PasswordManager_Memento memento = historio.restaurar_ultimo_estado();
        if (memento != null) {
            sitePasswords.put(memento.getSite(), memento.getPassword()); // RAM
            armazenamento.guardar(memento.getCategoria(), memento.getSite(), memento.getPassword()); // armazenamento
            System.out.println("✔ Site restaurado: " + memento.getSite());
        } else {
            System.out.println("⚠ Nada para desfazer.");
        }
    }






    public void showSites() {
        System.out.println("\nPasswords guardadas:");
        Map<String, String[]> dados = armazenamento.mostrar_tudo(); // agora retorna [categoria, password]

        if (dados.isEmpty()) {
            System.out.println("(nenhuma password guardada)");
            return;
        }

        for (Map.Entry<String, String[]> entry : dados.entrySet()) {
            String site = entry.getKey();
            String categoria = entry.getValue()[0];
            String password = entry.getValue()[1];

            System.out.println("Categoria: " + categoria + " | Site: " + site + " | Pass: " + password);
        }
    }


    public void criarCategoria(String nomeCategoria) {
        CategoriaComposite categoria = new CategoriaComposite(nomeCategoria);
        raizCategorias.adicionar(categoria);
        System.out.println("✔ Categoria criada: " + nomeCategoria);
    }

    public void criarSubcategoria(String categoriaPai, String nomeSubcategoria) {
        CategoriaComposite categoria = buscarCategoria(categoriaPai);
        if (categoria != null) {
            categoria.encontrarOuCriarSubcategoria(nomeSubcategoria);
            System.out.println("Subcategoria '" + nomeSubcategoria + "' criada em '" + categoriaPai + "'");
        } else {
            System.out.println("Categoria pai não encontrada!");
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

    public CategoriaComposite buscarCategoria(String nome) {
        return buscarCategoriaRecursivo(raizCategorias, nome);
    }

    private CategoriaComposite buscarCategoriaRecursivo(CategoriaComposite atual, String nome) {
        if (atual.getNome().equalsIgnoreCase(nome)) {
            return atual;
        }
        for (Categoria filho : atual.getFilhos()) {
            if (filho instanceof CategoriaComposite) {
                CategoriaComposite achada = buscarCategoriaRecursivo((CategoriaComposite) filho, nome);
                if (achada != null) return achada;
            }
        }
        return null;
    }
    public CategoriaComposite buscarCategoriaPorCaminho(String caminho) {
        String[] partes = caminho.split(" → ");
        CategoriaComposite atual = raizCategorias;
        for (String parte : partes) {
            atual = atual.encontrarOuCriarSubcategoria(parte);
            if (atual == null) {
                return null; // Não encontrou a categoria ou subcategoria
            }
        }
        return atual;
    }


}
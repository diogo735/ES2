package com.es2.passwords.algoritmosdearmazenamento;

import com.es2.passwords.ArmazenamentoBridge;

import java.io.*;
import java.util.*;

public class Armazenamento_em_Ficheiro extends ArmazenamentoBridge {
    private final String filePath = "passwords_guardadas.txt";

    @Override
    public void guardar(String categoria, String site, String password) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(categoria + ";" + site + ";" + password + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao guardar no ficheiro.");
        }
    }

    @Override
    public void remover(String site) {
        Map<String, String[]> all = mostrar_tudo();
        all.remove(site);
        try (PrintWriter pw = new PrintWriter(filePath)) {
            for (Map.Entry<String, String[]> entry : all.entrySet()) {
                pw.println(entry.getValue()[0] + ";" + entry.getKey() + ";" + entry.getValue()[1]);
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover do ficheiro.");
        }
    }


    @Override
    public Map<String, String[]> mostrar_tudo() {
        Map<String, String[]> data = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) return data;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 3) {
                    data.put(parts[1], new String[] {parts[0], parts[2]});
                    // site → [categoria, password]
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler do ficheiro.");
        }

        return data;
    }

}

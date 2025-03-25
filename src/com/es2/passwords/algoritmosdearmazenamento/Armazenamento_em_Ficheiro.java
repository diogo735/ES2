package com.es2.passwords.algoritmosdearmazenamento;
import com.es2.passwords.Tipo_Armazenamento;

import java.io.*;
import java.util.*;

public class Armazenamento_em_Ficheiro implements Tipo_Armazenamento {
    private final String filePath = "passwords_guardadas.txt";

    @Override
    public void guardar(String site, String password) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(site + ";" + password + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao guardar no ficheiro.");
        }
    }

    @Override
    public void remover(String site) {
        Map<String, String> all = mostrar_tudo();
        all.remove(site);
        try (PrintWriter pw = new PrintWriter(filePath)) {
            for (Map.Entry<String, String> entry : all.entrySet()) {
                pw.println(entry.getKey() + ";" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Erro ao remover do ficheiro.");
        }
    }

    @Override
    public Map<String, String> mostrar_tudo() {
        Map<String, String> data = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) return data;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler do ficheiro.");
        }

        return data;
    }
}
